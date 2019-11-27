package org.oz.utils;

import android.graphics.Color;

import com.blankj.utilcode.util.ToastUtils;

public class Ts {


    public static void i(String msg) {

        ToastUtils.showShort(msg);
    }


    public static void w(String msg) {

        ToastUtils.setBgColor(Color.YELLOW);
        ToastUtils.showShort(msg);
    }


    public static void e(String msg) {

        ToastUtils.setBgColor(Color.RED);
        ToastUtils.showShort(msg);
    }

}
