package com.xdynamics.share;

import android.app.Application;
import android.os.StrictMode;

import com.twitter.sdk.android.core.Twitter;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());


        Twitter.initialize(this);

    }
}
