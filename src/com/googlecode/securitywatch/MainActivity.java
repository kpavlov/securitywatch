package com.googlecode.securitywatch;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * MainActivity
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
public class MainActivity extends ExpandableListActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up our adapter
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
            Logic.refresh(this);
            super.onContentChanged();
            return true;
        /*
        case R.id.preferences:
            startActivity(new Intent(ApplicationPreferences.ACTION_EDIT_PREFERENCES));
            return true;
        case R.id.help:
            return true;
        */
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Logic.clearCache();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        Logic.clearCache();
        super.onLowMemory();
    }

    /** If the applications are cached, just show them, otherwise load and show * /
     private void showOrLoadApplications() {
     if (Api.applications == null) {
     // The applications are not cached.. so lets display the progress dialog
     progress = ProgressDialog.show(this, "Working...", "Reading installed applications", true);
     final Handler handler = new Handler() {
     public void handleMessage(Message msg) {
     if (progress != null) {
     progress.dismiss();
     }
     showApplications();
     }
     };
     new Thread() {
     public void run() {
     Logic.listApplications(MainActivity.this);
     handler.sendEmptyMessage(0);
     }
     }.start();
     } else {
     // the applications are cached, just show the list
     showApplications();
     }
     }
     */

    /** Show the list of applications * /
     private void showApplications() {
     final DroidApp[] apps = Api.getApps(this);
     // Sort applications - selected first, then alphabetically
     Arrays.sort(apps, new Comparator<DroidApp>() {
    @Override public int compare(DroidApp o1, DroidApp o2) {
    if ((o1.selected_wifi | o1.selected_3g) == (o2.selected_wifi | o2.selected_3g)) {
    return o1.names[0].compareTo(o2.names[0]);
    }
    if (o1.selected_wifi || o1.selected_3g) {
    return -1;
    }
    return 1;
    }
    });
     final LayoutInflater inflater = getLayoutInflater();
     final ListAdapter adapter = new ArrayAdapter<DroidApp>(this, R.layout.listitem, R.id.itemtext, apps) {
    @Override public View getView(int position, View convertView, ViewGroup parent) {
    ListEntry entry;
    if (convertView == null) {
    // Inflate a new view
    convertView = inflater.inflate(R.layout.listitem, parent, false);
    entry = new ListEntry();
    entry.box_wifi = (CheckBox) convertView.findViewById(R.id.itemcheck_wifi);
    entry.box_3g = (CheckBox) convertView.findViewById(R.id.itemcheck_3g);
    entry.text = (TextView) convertView.findViewById(R.id.itemtext);
    convertView.setTag(entry);
    entry.box_wifi.setOnCheckedChangeListener(MainActivity.this);
    entry.box_3g.setOnCheckedChangeListener(MainActivity.this);
    } else {
    // Convert an existing view
    entry = (ListEntry) convertView.getTag();
    }
    final DroidApp app = apps[position];
    entry.text.setText(app.toString());
    final CheckBox box_wifi = entry.box_wifi;
    box_wifi.setTag(app);
    box_wifi.setChecked(app.selected_wifi);
    final CheckBox box_3g = entry.box_3g;
    box_3g.setTag(app);
    box_3g.setChecked(app.selected_3g);
    return convertView;
    }
    };
     this.listview.setAdapter(adapter);
     }
     */

}
