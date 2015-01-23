package bernie.tcpclient.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import bernie.tcpclient.R;
import bernie.tcpclient.TCPSenderActivity;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        update();
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        update();
    }
    public void update() {
        Context context = getActivity();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        for(int i = 0; i<10; i++) {
            String btnlbl = sharedPref.getString("pref_btn" + (i + 1) + "lbl", "");
            Preference btnPref = (Preference) findPreference("pref_btn" + (i + 1) + "lbl");
            btnPref.setTitle("Button " + (i + 1) + ": \"" + btnlbl + "\"");
        }

    }
}
