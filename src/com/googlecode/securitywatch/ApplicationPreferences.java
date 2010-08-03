package com.googlecode.securitywatch;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * ApplicationPreferences can be used to edit SecurityWatch preferences.
 * <p/>
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
public class ApplicationPreferences extends PreferenceActivity {

    /** This activity can be started by an intent with this action. */
    public final static String ACTION_EDIT_PREFERENCES = "com.googlecode.securitywatch.ACTION_EDIT_PREFERENCES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the XML preferences file
        addPreferencesFromResource(R.xml.preferences);
    }
}
