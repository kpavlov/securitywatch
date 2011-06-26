package com.googlecode.securitywatch;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * DAO to get permissions.
 *
 * @author Konstantin Pavlov
 * @since 31.07.2010
 */
public abstract class PermissionDAO {

    /**
     * Processes system permissions and returns data structure for {@link PermissionListAdapter}
     *
     * @param ctx main activity
     * @return Permissions to package names map. Key is permission name,
     *         value is package names, having this permission
     */
    public static IndexedMultiValueMap<String, PackageItemInfo> listApplications(Activity ctx) {
        /* Maps permission to package names. Key is permission name,
 value is package names, granted this permission */
        IndexedMultiValueMap<String, PackageItemInfo> result = new IndexedMultiValueMap<String, PackageItemInfo>();
        final PackageManager pm = ctx.getPackageManager();

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final boolean internetOnly = prefs.getBoolean(AppPreferences.KEY_INTERNET_ONLY, true);
        final boolean includeSystem = prefs.getBoolean(AppPreferences.KEY_INCLUDE_SYSTEM, false);
        //final boolean includePackages = prefs.getBoolean(AppPreferences.KEY_INCLUDE_PACKAGES, false);

        final List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_PERMISSIONS);
        for (final ApplicationInfo app : apps) {
            for (RequestedPermission permission : RequestedPermission.values()) {
                //noinspection StringEquality
                if (internetOnly && Manifest.permission.INTERNET == permission.getPermission()) {
                    // don't show apps having inet permission in separate group
                    continue;
                }

                if (!includeSystem && ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                    continue;
                }
                if (internetOnly && !Utils.hasPermission(app.packageName, Manifest.permission.INTERNET, pm)) {
                    continue;
                }

                if (Utils.hasPermission(app.packageName, permission.getPermission(), pm)) {
                    result.add(permission.getPermission(), app);
                }
            }
        }
        return result;
    }
}
