package com.xdynamics.connector.utils;

public class CRCUtils {

    public native static void test();


    static {

        System.loadLibrary("crc_utils");

    }
}
