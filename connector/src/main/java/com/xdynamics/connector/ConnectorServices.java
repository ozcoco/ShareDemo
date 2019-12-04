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
}
