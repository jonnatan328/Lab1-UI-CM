package co.edu.udea.compumovil.gr04_20171.lab3.configuration;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


import co.edu.udea.compumovil.gr04_20171.lab3.R;


public class ConfigurationFragment extends PreferenceFragmentCompat {




    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.configuration_preferences);
    }
}
