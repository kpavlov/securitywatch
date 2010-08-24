/*
 * Copyright (c) 2010.
 */

package com.googlecode.securitywatch.scanner;

import android.content.pm.PackageManager;

import static android.Manifest.permission.*;
import static com.googlecode.securitywatch.Utils.hasPermission;

public class UseAccountsRisk implements Risk {
    @Override
    public int getWeight() {
        return 100;
    }

    @Override
    public boolean realized(String packageName, PackageManager packageManager) {
        return hasPermission(packageName, INTERNET, packageManager)
                &&
                (hasPermission(packageName, USE_CREDENTIALS, packageManager)
                        || hasPermission(packageName, AUTHENTICATE_ACCOUNTS, packageManager));
    }
}
