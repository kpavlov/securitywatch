package com.googlecode.securitywatch;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * PermissionsActivity
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
public class PermissionsActivity extends ExpandableListActivity {

    /**
     * Holds reference to {@link PermissionListAdapter}
     */
    private PermissionListAdapter permissionListAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        Presenter.permissionsActivity = this;

        permissionListAdapter = new PermissionListAdapter(getApplicationContext());
        setListAdapter(permissionListAdapter);


    }

    @Override
    protected void onResume() {
        if (permissionListAdapter.getGroupCount() == 0) {
            // not initialized or reset
            Presenter.refreshPermissionsView(null);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * Handles options menu
     *
     * @param item selected item
     * @return true if action is handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                Presenter.refreshPermissionsView(item);
                return true;
            case R.id.preferences:
                // reset data before calling preferences
                permissionListAdapter.setData(null);
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
    public void onLowMemory() {
        permissionListAdapter.setData(null);
        super.onLowMemory();
    }

    /** Called by presenter when presenter has fiished fetching data from DAO */
    public void onContentChanged(IndexedMultiValueMap<String, PackageItemInfo> data) {
        permissionListAdapter.setData(data);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Handles click on child element - shows applicaiton permissions dialog
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        PackageItemInfo child = permissionListAdapter.getChild(groupPosition, childPosition);
        if (child != null) {
            Utils.showInstalledAppDetails(this, child.packageName);
            return true;
        }
        return super.onChildClick(parent, v, groupPosition, childPosition, id);
    }
}
