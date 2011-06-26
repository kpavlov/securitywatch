package com.googlecode.securitywatch;

import android.app.ProgressDialog;
import android.content.pm.PackageItemInfo;
import android.os.AsyncTask;
import android.view.MenuItem;

/**
 * Presenter
 *
 * @author Konstantin Pavlov
 */
public class Presenter {

    static PermissionsActivity permissionsActivity;
    public static AppPreferences preferencesActivity;

    public static void refreshPermissionsView(MenuItem item) {
        final UpdateAppPermissionsTask task = new UpdateAppPermissionsTask(item);
        task.execute();
    }

    /**
     * Background task which updates expandable list
     */
    private static class UpdateAppPermissionsTask extends AsyncTask<Void, Boolean, IndexedMultiValueMap<String, PackageItemInfo>> {
        private final MenuItem item;

        private ProgressDialog dialog;

        public UpdateAppPermissionsTask(MenuItem item) {
            this.item = item;
        }

        @Override
        protected IndexedMultiValueMap<String, PackageItemInfo> doInBackground(Void... params) {
            if (item != null) {
                item.setEnabled(false);
            }
            publishProgress(true);
            return PermissionDAO.listApplications(permissionsActivity);
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            if (values[0]) {
                dialog = ProgressDialog.show(permissionsActivity, "",
                        "Loading. Please wait...", true);

//                permissionsActivity.getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS,
//                        Window.PROGRESS_VISIBILITY_ON);
            } else if (dialog != null) {
                dialog.hide();
                dialog = null;
//                permissionsActivity.getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS,
//                        Window.PROGRESS_VISIBILITY_OFF);
            }
        }

        @Override
        protected void onPostExecute(IndexedMultiValueMap<String, PackageItemInfo> result) {
            permissionsActivity.onContentChanged(result);
            permissionsActivity.onContentChanged();
            publishProgress(false);
            if (item != null) {
                item.setEnabled(true);
            }
        }
    }
}
