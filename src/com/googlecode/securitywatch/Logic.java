package com.googlecode.securitywatch;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Business Logic
 *
 * @author Konstantin Pavlov
 * @since 31.07.2010
 */
public abstract class Logic {

    private static final Object LOCK = new Object();

    /** Maps permission to package names */
    private static IndexedMultiValueMap<String, String> applications;

    public static boolean hasPermission(ApplicationInfo app, String permission, final PackageManager pkgmanager) {
        return PackageManager.PERMISSION_GRANTED == pkgmanager.checkPermission(permission, app.packageName);
    }

    public static IndexedMultiValueMap<String, String> listApplications(Activity ctx, boolean internetOnly, boolean excludeSystem) {
        IndexedMultiValueMap<String, String> result = new IndexedMultiValueMap<String, String>();
        final PackageManager pkgmanager = ctx.getPackageManager();
        final List<ApplicationInfo> installed = pkgmanager.getInstalledApplications(PackageManager.GET_PERMISSIONS);
        for (final ApplicationInfo app : installed) {
            for (RequestedPermission requestedPermission : RequestedPermission.values()) {
                if (excludeSystem && ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                    continue;
                }

                if (internetOnly && !hasPermission(app, Manifest.permission.INTERNET, pkgmanager)) {
                    continue;
                }

                if (hasPermission(app, requestedPermission.getPermission(), pkgmanager)) {
                    result.add(requestedPermission.getPermission(), pkgmanager.getApplicationLabel(app).toString());
                }
            }
        }
        result.removeEmpty();
        return result;
    }

    public static IndexedMultiValueMap<String, String> getApplications(Activity ctx) {
        if (applications == null) {
            // fetch
            synchronized (LOCK) {
                applications = listApplications(ctx, true, true);
            }
        }
        return applications;
    }

    public static void clearCache() {
        synchronized (LOCK) {
            applications = null;
        }
    }

    public static void refresh(Activity ctx) {
        synchronized (LOCK) {
            applications = listApplications(ctx, true, true);
        }
    }
}
