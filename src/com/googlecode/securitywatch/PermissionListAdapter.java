package com.googlecode.securitywatch;

import android.app.Activity;
import android.content.pm.PackageItemInfo;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.googlecode.securitywatch.view.AppView;

/**
 * Adapter which maintains expandable permission list with applications which have these permissions.
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
class PermissionListAdapter extends BaseExpandableListAdapter {

    private final Activity ctx;

    private IndexedMultiValueMap<String, PackageItemInfo> data;

    public PermissionListAdapter(Activity ctx) {
        this(ctx, new IndexedMultiValueMap<String, PackageItemInfo>());
    }

    public PermissionListAdapter(Activity ctx, IndexedMultiValueMap<String, PackageItemInfo> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public PackageItemInfo getChild(int groupPosition, int childPosition) {
        return data.getList(
                data.keyList().get(groupPosition)
        ).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return data.getList(
                data.keyList().get(groupPosition)
        ).size();
    }

    private IndexedMultiValueMap<String, PackageItemInfo> getData() {
        return data;
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

    /**
     * Returns view for application row
     */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final PackageItemInfo packageItemInfo = getChild(groupPosition, childPosition);
        final AppView appView = new AppView(ctx, packageItemInfo);
        return appView;
//        TextView textView = getGenericView();
//        textView.setText(getChild(groupPosition, childPosition).toString());
//        return textView;
    }

    public Object getGroup(int groupPosition) {
        if (data == null) return null;
        return Utils.getPermissionLabel(
                data.keyList().get(groupPosition),
                ctx.getPackageManager());
    }

    public int getGroupCount() {
        return data == null ? 0 : data.size();
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

    /**
     * Sets model and calls {@link #notifyDataSetInvalidated()}
     *
     * @param data
     */
    public void setData(IndexedMultiValueMap<String, PackageItemInfo> data) {
        this.data = data;
        notifyDataSetInvalidated();
    }
}
