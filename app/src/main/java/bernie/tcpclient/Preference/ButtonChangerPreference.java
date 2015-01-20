package bernie.tcpclient.Preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import bernie.tcpclient.R;

/**
 * Created by Bernie on 20.01.2015.
 */
public class ButtonChangerPreference extends DialogPreference {
    public ButtonChangerPreference(Context context, AttributeSet attrs){
        super(context, attrs);

        setDialogLayoutResource(R.layout.buttonchanger_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    protected void onDialogClosed(boolean positiveResult){
        if(positiveResult){
        }
    }
}
