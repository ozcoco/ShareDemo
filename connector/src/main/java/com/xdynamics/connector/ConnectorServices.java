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

    private CMDServerNative mCMDServerNative;

    @Override
    public void onCreate() {
        super.onCreate();

        mCMDServerNative = new CMDServerNative();

    }


    /**
     * start sticky
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mCMDServerNative != null && !mCMDServerNative.nativeIsRunning()) {

            mCMDServerNative.nativeInit();

            mCMDServerNative.nativeTest();

        }

        return START_STICKY_COMPATIBILITY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mCMDServerNative.nativeDestroy();

    }
}
