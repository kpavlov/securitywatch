/*
 * Copyright (c) 2010.
 */

package com.googlecode.securitywatch.scanner;

import android.content.pm.PackageManager;

public interface Risk {

//    CharSequence getName();
//
//    CharSequence getDescription();

    int getWeight();

    boolean realized(String packageName, PackageManager packageManager);
}
