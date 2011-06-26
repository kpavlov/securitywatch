/*
 * Copyright (c) 2011.
 */

package com.googlecode.securitywatch.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * View to display application in expandable list
 */
public class AppView extends LinearLayout {

    private final ImageView icon;
    private final TextView label;

    public AppView(Context context) {
        super(context);
        addView(icon = new ImageView(context));
        addView(label = new TextView(context));
    }

    public AppView(Context context, PackageItemInfo packageItemInfo) {
        this(context);
        setModel(packageItemInfo);
    }

    public void setModel(PackageItemInfo packageItemInfo) {
        final PackageManager pm = getContext().getPackageManager();
        if (packageItemInfo instanceof ApplicationInfo) {
            icon.setImageDrawable(pm.getApplicationIcon((ApplicationInfo) packageItemInfo));
            label.setText(pm.getApplicationLabel((ApplicationInfo) packageItemInfo));
        } else {
            icon.setImageResource(packageItemInfo.icon);
            label.setText(packageItemInfo.packageName);
        }

    }


}
