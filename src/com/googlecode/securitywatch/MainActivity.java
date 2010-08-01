package com.googlecode.securitywatch;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * MainActivity
 * <p/>
 * Revision: 1
 *
 * @author maestro
 * @since 15.07.2010
 */
public class MainActivity extends ExpandableListActivity {
    
    private MyExpandableListAdapter mAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
//        ExpandableListView epView = (ExpandableListView) findViewById(R.id.ExpandableListView01);
//        setContentView(R.layout.main);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
//        menu.add(0, 0, 0, R.string.expandable_list_sample_action);
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
     Manager.listApplications(MainActivity.this);
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

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids.
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = {"People Names", "Dog Names", "Cat Names", "Fish Names"};
        private String[][] children = {
                {"Arnold", "Barry", "Chuck", "David"},
                {"Ace", "Bandit", "Cha-Cha", "Deuce"},
                {"Fluffy", "Snuggles"},
                {"Goldy", "Bubbles"}
        };

        public Object getChild(int groupPosition, int childPosition) {
            return Manager.getApplications(MainActivity.this).getList(
                    RequestedPermission.values()[groupPosition]
            ).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return Manager.getApplications(MainActivity.this).getList(
                    RequestedPermission.values()[groupPosition]
            ).size();
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView textView = new TextView(MainActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//        textView.setTextColor(R.color.marcyred);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return RequestedPermission.values()[groupPosition].getTitle();
        }

        public int getGroupCount() {
            return RequestedPermission.values().length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}
