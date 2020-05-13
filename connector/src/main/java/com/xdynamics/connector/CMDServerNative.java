package com.xdynamics.connector;

public class CMDServerNative {

    public CMDServerNative() {

        System.loadLibrary("connect-lib");

    }

    public native void nativeInit();

    public native void nativeDestroy();

    public native boolean nativeIsRunning();

    public native void nativeTest();

}
