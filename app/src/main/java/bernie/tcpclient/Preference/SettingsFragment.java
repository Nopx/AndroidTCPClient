package bernie.tcpclient.Preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import bernie.tcpclient.R;


public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

}
