package com.googlecode.securitywatch;

import android.Manifest;

/**
 * RequestedPermission
 * <p/>
 * Revision: 1
 *
 * @author maestro
 * @since 31.07.2010
 */
public enum RequestedPermission {

    READ_CONTACTS(Manifest.permission.READ_CONTACTS, "Read Contacts"),
    READ_OWNER_DATA(Manifest.permission.READ_OWNER_DATA, "Read Owner Data"),
    READ_BROWSE_HISTORY(Manifest.permission.READ_HISTORY_BOOKMARKS, "Read History Bookmarks"),
    READ_LOGS(Manifest.permission.READ_LOGS, "Read Logs");

    private String permission;
    private String title;

    private RequestedPermission(String permission, String title) {
        this.permission = permission;
        this.title = title;
    }

    public String getPermission() {
        return permission;
    }

    public String getTitle() {
        return title;
    }
}
