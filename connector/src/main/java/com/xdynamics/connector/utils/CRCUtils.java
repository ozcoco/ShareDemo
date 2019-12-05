package com.xdynamics.connector.utils;

public class CRCUtils {

    public native static void test();

    public native static char crc16(String str);

    static {
        System.loadLibrary("crc_utils");
    }
}
