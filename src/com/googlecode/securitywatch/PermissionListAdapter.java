package com.googlecode.securitywatch;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Adapter which maintains expandable permission list with applications which have these permissions.
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
class PermissionListAdapter extends BaseExpandableListAdapter {

    private final Activity ctx;

    public PermissionListAdapter(Activity ctx) {
        this.ctx = ctx;
    }

    public Object getChild(int groupPosition, int childPosition) {
        final IndexedMultiValueMap<String, String> data = getData();
        return data.getList(
                data.keyList().get(groupPosition)
        ).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return getData().getList(
                getData().keyList().get(groupPosition)
        ).size();
    }

    private IndexedMultiValueMap<String, String> getData() {
        return Dao.getApplications(ctx);
    }

    private TextView getGenericView() {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 64);

        TextView textView = new TextView(ctx);
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
        return Utils.getPermissionLabel(
                getData().keyList().get(groupPosition),
                ctx.getPackageManager());
    }

    public int getGroupCount() {
        return getData().size();
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
