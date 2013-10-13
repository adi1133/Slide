package ro.epb.slide;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

/**
 * Created by adi on 10/12/13.
 */
public class KeyboardService extends InputMethodService {
    float posX;
    boolean eatOneKey;
    InputConnection mInputConnection;

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        InputConnection inp = getCurrentInputConnection();
        if(inp != null)
        {
            mInputConnection = inp;
        }
    }

    @Override
    public void onBindInput() {
        super.onBindInput();
        Log.i("asd","start bind");
    }

    @Override
    public View onCreateInputView() {
        KeyboardView ret = (KeyboardView)  getLayoutInflater().inflate(R.layout.keybord, null);
        ret.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        posX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dX = event.getX() - posX;
                        float trigger = 10;
                        if(Math.abs(dX) > trigger)
                        {
                            if(mInputConnection == null)
                            {
                                Log.i("null","is null");
                                return false;
                            }
                            eatOneKey = true;
                            Log.i("not null","asdasd");

                             ExtractedText et = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0);
                            int size = et.text.length();
                            int startC = et.selectionStart;
                            int endC = et.selectionEnd;
                            if(dX > 0 && endC < size)
                            {
                                mInputConnection.setSelection(startC + 1, endC + 1);
                            }
                            else if(dX < 0 && startC > 0)
                            {
                                mInputConnection.setSelection(startC - 1, endC - 1);
                            }

                            Log.i("cursor", startC + " " + endC + " " + size);
//                            int code = dX > 0 ? KeyEvent.KEYCODE_DPAD_RIGHT : KeyEvent.KEYCODE_DPAD_LEFT;
//
//                            mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, code));
//                            mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, code));
                            posX = event.getX();
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
//                //String raw = String.valueOf(event.getRawX());
//                //String vak = String.valueOf(event.getX());
//               // Log.i(raw,vak);
//                //Log.i("action",String.valueOf(event.getAction()));
                return false;
            }
        });
        ret.setKeyboard(new Keyboard(this,R.xml.symbols));

        ret.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {


            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                if(eatOneKey)
                {
                    eatOneKey = false;
                    return;
                }
                if(mInputConnection == null)
                {
                    Log.i("null2","is null2");
                    return ;
                }
                if(primaryCode == '\b')
                {
                    mInputConnection.sendKeyEvent(
                            new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    mInputConnection. sendKeyEvent(
                            new KeyEvent(KeyEvent.ACTION_UP,  KeyEvent.KEYCODE_DEL));
                }
                else
                {
                    mInputConnection.commitText(String.valueOf((char) primaryCode), 1);
                }
                //getCurrentInputConnection().sendKeyEvent(new KeyEvent(primaryCode));
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
        getLayoutInflater().inflate(R.layout.keybord, null);
        return ret;
    }
}
