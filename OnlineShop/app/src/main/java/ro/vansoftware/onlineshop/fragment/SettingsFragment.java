package ro.vansoftware.onlineshop.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import ro.vansoftware.onlineshop.R;
import ro.vansoftware.onlineshop.storage.Shared;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        getPreferenceManager().setSharedPreferencesName(Shared.SETTINGS);


    }

}
