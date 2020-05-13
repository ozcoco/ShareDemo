package com.xdynamics.share;

import android.app.Application;
import android.content.Intent;

import com.twitter.sdk.android.core.Twitter;
import com.xdynamics.connector.ConnectorServices;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Twitter.initialize(this);

//        startServer();
    }


    private void startServer() {

        Intent intent = new Intent(getApplicationContext(), ConnectorServices.class);



        startService(intent);

    }

}
