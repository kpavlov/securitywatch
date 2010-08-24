package com.googlecode.securitywatch;

import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.Window;

/**
 * Presenter
 * <p/>
 * Revision: 1
 * <p/>
 * Date: 08.08.2010
 * <p/>
 * Copyright by Konstantin Pavlov
 *
 * @author maestro
 */
public class Presenter {

    static PermissionsActivity permissionsActivity;
    public static AppPreferences preferencesActivity;

    public static void refreshPermissionsView(MenuItem item) {
        final UpdateAppPermissionsTask task = new UpdateAppPermissionsTask(item);
        task.execute();
    }

    public static void onLowMemory() {
        Dao.clearCache();
    }

    private static class UpdateAppPermissionsTask extends AsyncTask<Void, Boolean, IndexedMultiValueMap<String, String>> {
        private final MenuItem item;

        public UpdateAppPermissionsTask(MenuItem item) {
            this.item = item;
        }

        @Override
        protected IndexedMultiValueMap<String, String> doInBackground(Void... params) {
            if (item != null) {
                item.setEnabled(false);
            }
            publishProgress(true);
            Dao.clearCache();
            return Dao.listApplications(permissionsActivity);
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            if (values[0]) {
                permissionsActivity.getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS,
                        Window.PROGRESS_VISIBILITY_ON);
            } else {
                permissionsActivity.getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS,
                        Window.PROGRESS_VISIBILITY_OFF);
            }
        }

        @Override
        protected void onPostExecute(IndexedMultiValueMap<String, String> stringStringIndexedMultiValueMap) {
            permissionsActivity.onContentChanged();
            publishProgress(false);
            if (item != null) {
                item.setEnabled(true);
            }
        }
    }
}
