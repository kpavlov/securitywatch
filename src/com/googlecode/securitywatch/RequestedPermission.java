package com.googlecode.securitywatch;

import android.Manifest;

/**
 * RequestedPermission enumerates system permissions requested by applications.
 *
 * @author Konstantin Pavlov
 * @since 31.07.2010
 */
public enum RequestedPermission {

    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    
    READ_CALENDAR(Manifest.permission.READ_CALENDAR),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR), 

    READ_OWNER_DATA(Manifest.permission.READ_OWNER_DATA),
    WRITE_OWNER_DATA(Manifest.permission.WRITE_OWNER_DATA),

    CALL_PHONE(Manifest.permission.CALL_PHONE),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS),
    
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),

    SEND_SMS(Manifest.permission.SEND_SMS),
    READ_SMS(Manifest.permission.READ_SMS),
    WRITE_SMS(Manifest.permission.WRITE_SMS),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
    BROADCAST_SMS(Manifest.permission.BROADCAST_SMS),

    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
    BROADCAST_WAP_PUSH(Manifest.permission.BROADCAST_WAP_PUSH),

    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),

    READ_BROWSE_HISTORY(Manifest.permission.READ_HISTORY_BOOKMARKS),
    WRITE_BROWSE_HISTORY(Manifest.permission.WRITE_HISTORY_BOOKMARKS),

    ACCESS_MOCK_LOCATION(Manifest.permission.ACCESS_MOCK_LOCATION),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),

    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),
    AUTHENTICATE_ACCOUNTS(Manifest.permission.AUTHENTICATE_ACCOUNTS),
    MANAGE_ACCOUNTS(Manifest.permission.MANAGE_ACCOUNTS),
    USE_CREDENTIALS(Manifest.permission.USE_CREDENTIALS),
    ACCOUNT_MANAGER(Manifest.permission.ACCOUNT_MANAGER),

    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE),

    SET_PREFERRED_APPLICATIONS(Manifest.permission.SET_PREFERRED_APPLICATIONS),

    READ_LOGS(Manifest.permission.READ_LOGS);

    private String permission;

    private RequestedPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
