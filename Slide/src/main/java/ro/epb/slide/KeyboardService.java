package ro.epb.slide;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.view.View;

/**
 * Created by adi on 10/12/13.
 */
public class KeyboardService extends InputMethodService {
    @Override
    public View onCreateInputView() {
        KeyboardView ret = (KeyboardView)  getLayoutInflater().inflate(R.layout.keybord, null);
        ret.setKeyboard(new Keyboard(this,R.xml.symbols));
        return ret;
    }
}
