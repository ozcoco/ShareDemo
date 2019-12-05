package com.xdynamics.connector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ConnectorServices extends Service {
    public ConnectorServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private ETServerNative etServerNative;

    @Override
    public void onCreate() {
        super.onCreate();

        etServerNative = new ETServerNative();

    }


    /**
     * start sticky
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (etServerNative != null && !etServerNative.nativeIsRunning()) {

            etServerNative.nativeInit();

            etServerNative.nativeTest();

        }

        return START_STICKY_COMPATIBILITY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        etServerNative.nativeDestroy();

    }
}
