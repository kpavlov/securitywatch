/*
 * Copyright (c) 2010.
 */

package com.googlecode.securitywatch;

import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;

public abstract class Utils {

    public static boolean hasPermission(String packageName, String permission, final PackageManager pkgmanager) {
        return PackageManager.PERMISSION_GRANTED == pkgmanager.checkPermission(permission, packageName);
    }

    static CharSequence getPermissionLabel(String permission, PackageManager packageManager) {
        try {
            PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
            return permissionInfo.loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private CharSequence getPermissionDescription(String permission, PackageManager packageManager) {
        try {
            PermissionInfo permissionInfo = packageManager.getPermissionInfo(permission, 0);
            return permissionInfo.loadDescription(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

