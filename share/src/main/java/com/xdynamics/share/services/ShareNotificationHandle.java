package com.xdynamics.share.services;

import android.content.Context;

import java.io.Serializable;

public abstract class ShareNotificationHandle implements Serializable {

    private String pkg;

    private String tickerText;

    private String text;

    private transient Context context;


    public ShareNotificationHandle(Context context, String pkg) {
        if (context == null)
            throw new IllegalArgumentException("context != null");

        this.context = context;

        this.pkg = pkg;
    }

    public Context getContext() {
        return context;
    }

    public String getPkg() {
        return pkg;
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public abstract void onHandle();

}
