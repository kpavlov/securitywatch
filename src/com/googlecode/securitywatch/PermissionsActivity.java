package com.googlecode.securitywatch;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

/**
 * PermissionsActivity
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
public class PermissionsActivity extends ExpandableListActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS,
                Window.PROGRESS_INDETERMINATE_ON);

        // Set up our adapter
        Presenter.permissionsActivity = this;

        setListAdapter(new PermissionListAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                Presenter.refreshPermissionsView(item);
                return true;
            case R.id.preferences:
                startActivity(new Intent(AppPreferences.ACTION_EDIT_PREFERENCES));
                return true;
            /*
            case R.id.help:
                return true;
            */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Dao.clearCache();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        Presenter.onLowMemory();
        super.onLowMemory();
    }
}
