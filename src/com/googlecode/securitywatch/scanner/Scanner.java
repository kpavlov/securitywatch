/*
 * Copyright (c) 2010.
 */

package com.googlecode.securitywatch.scanner;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    public List<Vulnerability> scan(ApplicationInfo app, Context context) {
        ArrayList<Vulnerability> vulnerabilities = new ArrayList<Vulnerability>();

        final SendContactsVulnerability sendContactsRisk = new SendContactsVulnerability();
        if (sendContactsRisk.realized(app.packageName, context.getPackageManager())) {
            vulnerabilities.add(sendContactsRisk);
        }

        final UseAccountsVulnerability useAccountsRisk = new UseAccountsVulnerability();
        if (sendContactsRisk.realized(app.packageName, context.getPackageManager())) {
            vulnerabilities.add(sendContactsRisk);
        }

        return vulnerabilities;
    }
}
