package com.googlecode.securitywatch.watch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receives notification when apckage is installed or replaced.
 *
 * @author Konstantin Pavlov
 * @version $LastChangedRevision$
 * @since 03.08.2010
 */
public class PackageInstallReceiver extends BroadcastReceiver {

    private final static String TAG = "PackageInstallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction()) || Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            // || Intent.ACTION_PACKAGE_INSTALL
            // Send a text notification to the screen.
            Log.d(TAG, "package installed or replaced. Need to scan");
//        NotificationManager nm = (NotificationManager)
//        context.getSystemService(Context.NOTIFICATION_SERVICE);
//        nm.notifyWithText(R.id.alarm,
//                          "Alarm!!!",
//                          NotificationManager.LENGTH_SHORT,
//                          null);
        }
    }
}
