package com.googlecode.securitywatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Manager
 * <p/>
 * Revision: 1
 *
 * @author maestro
 * @since 31.07.2010
 */
public class Manager {

    private static final Object LOCK = new Object();

    private static MultiValueMap<RequestedPermission, String> applications;

    public static boolean hasPermission(ApplicationInfo app, String permission, final PackageManager pkgmanager) {
        return PackageManager.PERMISSION_GRANTED == pkgmanager.checkPermission(permission, app.packageName);
    }

    public static MultiValueMap<RequestedPermission, String> listApplications(Context ctx, boolean internetOnly, boolean excludeSystem) {
        MultiValueMap<RequestedPermission, String> result = new MultiValueMap<RequestedPermission, String>();
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
                    result.add(requestedPermission, pkgmanager.getApplicationLabel(app).toString());
                }
            }
        }
        return result;

    }

    public static MultiValueMap<RequestedPermission, String> getApplications(Context ctx) {
        if (applications == null) {
            // fetch
            synchronized (LOCK) {
                applications = listApplications(ctx, true, true);
            }
        }
        return applications;
    }
}
