package com.googlecode.securitywatch;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import com.googlecode.securitywatch.scanner.Permissions;

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
     * @param ctx application context
     * @return Permissions to package names map. Key is permission name,
     *         value is package names, having this permission
     */
    public static IndexedMultiValueMap<String, PackageItemInfo> listApplications(Context ctx) {
        /* Maps permission to package names. Key is permission name,
 value is package names, granted this permission */
        IndexedMultiValueMap<String, PackageItemInfo> result = new IndexedMultiValueMap<String, PackageItemInfo>();
        final PackageManager pm = ctx.getPackageManager();

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final boolean internetOnly = prefs.getBoolean(AppPreferences.KEY_INTERNET_ONLY, true);
        final boolean includeSystem = prefs.getBoolean(AppPreferences.KEY_INCLUDE_SYSTEM, false);
        final boolean showSafePermissions = prefs.getBoolean(AppPreferences.KEY_SHOW_SAFE_PERMISSIONS, false);
        //final boolean includePackages = prefs.getBoolean(AppPreferences.KEY_INCLUDE_PACKAGES, false);

        final List<String> allPermissions = Permissions.getAllPermissions(pm, showSafePermissions);

        final List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_PERMISSIONS);
        for (final ApplicationInfo app : apps) {
            if (!includeSystem && ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                continue;
            }

            for (String permission : allPermissions) {
                //noinspection StringEquality
                if (internetOnly && Manifest.permission.INTERNET == permission) {
                    // don't show apps having inet permission in separate group
                    continue;
                }

                if (internetOnly && !Utils.hasPermission(app.packageName, Manifest.permission.INTERNET, pm)) {
                    continue;
                }

                if (Utils.hasPermission(app.packageName, permission, pm)) {
                    result.add(permission, app);
                }
            }
        }
        return result;
    }


}
