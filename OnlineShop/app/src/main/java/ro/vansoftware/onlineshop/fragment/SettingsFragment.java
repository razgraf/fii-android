package ro.vansoftware.onlineshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import ro.vansoftware.onlineshop.MainActivity;
import ro.vansoftware.onlineshop.R;
import ro.vansoftware.onlineshop.model.Product;
import ro.vansoftware.onlineshop.storage.Internal;
import ro.vansoftware.onlineshop.storage.Shared;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        getPreferenceManager().setSharedPreferencesName(Shared.SETTINGS);

        Preference preference = getPreferenceManager().findPreference("SETTING_CLEAR");
        if(preference != null)
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    try{

                        Internal.setProducts(getContext(), new ArrayList<Product>());

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finishAffinity();
                    }
                    catch (Exception e){Log.e("SettingsFragment",e.getMessage());}
                    return true;
                }
            });

    }

}
