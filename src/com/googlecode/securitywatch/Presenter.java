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
                dialog = ProgressDialog.show(permissionsActivity,
                        permissionsActivity.getResources().getString(R.string.dialog_loading_title),
                        permissionsActivity.getResources().getString(R.string.dialog_loading_description),
                        true);

            } else if (dialog != null) {
                dialog.hide();
                dialog = null;
            }
        }

        @Override
        protected void onPostExecute(IndexedMultiValueMap<String, PackageItemInfo> result) {
            permissionsActivity.onContentChanged(result);
            publishProgress(false);
            if (item != null) {
                item.setEnabled(true);
            }
        }
    }
}
