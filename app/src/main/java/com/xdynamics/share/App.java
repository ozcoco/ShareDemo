package com.xdynamics.share;

import android.app.Application;
import android.content.Intent;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.twitter.sdk.android.core.Twitter;
import com.xdynamics.connector.ConnectorServices;
import com.xdynamics.share.platform.FaceBook;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());

        Twitter.initialize(this);

        new FaceBook().init(this);

//        startServer();
    }


    private void startServer() {

        Intent intent = new Intent(getApplicationContext(), ConnectorServices.class);

        startService(intent);

    }

}
