package com.xdynamics.share.services;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationCollectorService extends NotificationListenerService {

    ShareNotificationHandle handle;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        if (handle == null)
            handle = TwitterShareNotificationHandle.create(getApplication());

        if (handle.getPkg().equals(sbn.getPackageName()))
            handle.onHandle();

        Log.i("---->", "open" + "-----" + sbn.getPackageName());
        Log.i("---->", "open" + "------" + sbn.getNotification().tickerText);
        Log.i("---->", "open" + "-----" + sbn.getNotification().extras.get("android.title"));
        Log.i("---->", "open" + "-----" + sbn.getNotification().extras.get("android.text"));
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

        Log.i("---->", "remove" + "-----" + sbn.getPackageName());
    }


    public static class Permission {

        public static boolean notificationListenerEnable(Context context) {
            boolean enable = false;
            String packageName = context.getPackageName();
            String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
            if (flat != null) {
                enable = flat.contains(packageName);
            }
            return enable;
        }


        public static boolean gotoNotificationAccessSetting(Context context) {
            try {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                try {
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                    intent.setComponent(cn);
                    intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                    context.startActivity(intent);
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }


        }

    }

}




