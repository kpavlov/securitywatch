/*
 * Copyright (c) 2010.
 */

package com.googlecode.securitywatch.scanner;

import android.content.pm.PackageManager;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static com.googlecode.securitywatch.Utils.hasPermission;

public class SendContactsRisk implements Risk {

    @Override
    public int getWeight() {
        return 10;
    }

    @Override
    public boolean realized(String packageName, PackageManager packageManager) {
        return hasPermission(packageName, INTERNET, packageManager)
                && hasPermission(packageName, READ_CONTACTS, packageManager);
    }
}
