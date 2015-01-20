package bernie.tcpclient.Preference;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import bernie.tcpclient.R;


public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();
    }
}
