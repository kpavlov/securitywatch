package com.googlecode.securitywatch;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter which maintains expandable permission list with applications which have these permissions.
 *
 * @author Konstantin Pavlov
 * @since 1.0.1
 */
class PermissionListAdapter extends BaseExpandableListAdapter {

    private final Context ctx;

    private final LayoutInflater mInflater;

    private IndexedMultiValueMap<String, PackageItemInfo> data;

    public PermissionListAdapter(Context ctx) {
        this(ctx, new IndexedMultiValueMap<String, PackageItemInfo>());
    }

    public PermissionListAdapter(Context ctx, IndexedMultiValueMap<String, PackageItemInfo> data) {
        this.ctx = ctx;
        this.data = data;
        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    /**
     * Returns view for application row
     */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final PackageItemInfo packageItemInfo = getChild(groupPosition, childPosition);

        final PackageManager pm = ctx.getPackageManager();
        final View appView = mInflater.inflate(R.layout.app_list_item, null);
        final ImageView icon = (ImageView) appView.findViewById(R.id.app_list_item_icon);
        final TextView label = (TextView) appView.findViewById(R.id.app_list_item_label);

        if (packageItemInfo instanceof ApplicationInfo) {
            icon.setImageDrawable(pm.getApplicationIcon((ApplicationInfo) packageItemInfo));
            label.setText(pm.getApplicationLabel((ApplicationInfo) packageItemInfo));
        } else {
            icon.setImageResource(packageItemInfo.icon);
            label.setText(packageItemInfo.packageName);
        }
        return appView;
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
        TextView textView = (TextView) mInflater.inflate(R.layout.permission_list_item, null);
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
