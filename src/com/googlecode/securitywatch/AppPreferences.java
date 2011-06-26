package com.googlecode.securitywatch;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * AppPreferences can be used to edit SecurityWatch preferences.
 * <p/>
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
public class AppPreferences extends PreferenceActivity {

    /**
     * Shows only applications with granted internet access
     */
    public static final String KEY_INTERNET_ONLY = "internetOnly";
    /**
     * Include system applications
     */
    public static final String KEY_INCLUDE_SYSTEM = "includeSystem";

    /**
     * Include packages (libraries
     */
    public static final String KEY_INCLUDE_PACKAGES = "includePackages";

    /**
     * This activity can be started by an intent with this action.
     */
    public final static String ACTION_EDIT_PREFERENCES = "com.googlecode.securitywatch.ACTION_EDIT_PREFERENCES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the XML preferences file
        addPreferencesFromResource(R.xml.preferences);

        Presenter.preferencesActivity = this;
    }
}
