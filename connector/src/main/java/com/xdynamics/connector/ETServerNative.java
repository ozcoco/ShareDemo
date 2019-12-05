package com.xdynamics.connector;

public class ETServerNative {

    public ETServerNative() {

        System.loadLibrary("connector");

    }

    public native void nativeInit();

    public native void nativeDestroy();

    public native boolean nativeIsRunning();

    public native void nativeTest();

}
