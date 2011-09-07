package com.googlecode.securitywatch.scanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Permissions holds safe permissions and contains helper static methods for obtainig permissions.
 * <p/>
 * Copyright &copy; 2011 by Konstantin Pavlov
 *
 * @author Konstantin Pavlov
 */
public final class Permissions {

    private static final String TAG = "Permissions";

    private Permissions() {
    }

    public static final HashSet<String> SAFE_PERMISSIONS = new HashSet<String>(Arrays.asList(
            Manifest.permission.BATTERY_STATS,
            Manifest.permission.ACCESS_MOCK_LOCATION,
            Manifest.permission.BROADCAST_PACKAGE_REMOVED,
            Manifest.permission.EXPAND_STATUS_BAR,
            Manifest.permission.FLASHLIGHT,
            Manifest.permission.GET_PACKAGE_SIZE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.SET_ORIENTATION,
            Manifest.permission.VIBRATE
    ));

    public static synchronized List<String> getAllPermissions(PackageManager pm, boolean showSafePermissions) {
        List<String> ALL_PERMISSIONS = new ArrayList<String>();
        final List<PermissionGroupInfo> permissionGroups = pm.getAllPermissionGroups(0);
        for (PermissionGroupInfo permissionGroup : permissionGroups) {
            try {
                final List<PermissionInfo> permissionInfos = pm.queryPermissionsByGroup(permissionGroup.name, 0);
                for (PermissionInfo permission : permissionInfos) {
                    if (!showSafePermissions && SAFE_PERMISSIONS.contains(permission.name)) {
                        Log.v(TAG, "Safe permission excluded: " + permission.name);
                        continue;
                    }
                    ALL_PERMISSIONS.add(permission.name);
                    Log.v(TAG, "Permission included: " + permission.name);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Unknown permissionGroup " + permissionGroup.name, e);
            }
        }
        return ALL_PERMISSIONS;
    }
}
