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

    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    READ_OWNER_DATA(Manifest.permission.READ_OWNER_DATA),
    READ_CALL_PHONE(Manifest.permission.CALL_PHONE),
    READ_SEND_SMS(Manifest.permission.SEND_SMS),
    READ_READ_SMS(Manifest.permission.READ_SMS),
    READ_BROWSE_HISTORY(Manifest.permission.READ_HISTORY_BOOKMARKS),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),
    READ_LOGS(Manifest.permission.READ_LOGS);

    private String permission;

    private RequestedPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
