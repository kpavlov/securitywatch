/*
 * Copyright (c) 2010.
 */

package com.googlecode.securitywatch.scanner;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    public List<Risk> scan(ApplicationInfo app, Context context) {
        ArrayList<Risk> risks = new ArrayList<Risk>();

        final SendContactsRisk sendContactsRisk = new SendContactsRisk();
        if (sendContactsRisk.realized(app.packageName, context.getPackageManager())) {
            risks.add(sendContactsRisk);
        }

        final UseAccountsRisk useAccountsRisk = new UseAccountsRisk();
        if (sendContactsRisk.realized(app.packageName, context.getPackageManager())) {
            risks.add(sendContactsRisk);
        }

        return risks;
    }
}
