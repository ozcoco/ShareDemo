package com.xdynamics.share.platform;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share.platform
 * @ClassName: FaceBook
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/14 18:52
 * @UpdateUser:
 * @UpdateDate: 2020/5/14 18:52
 * @UpdateRemark:
 * @Version: 1.0
 */
public class FaceBook implements InitAble {


    @Override
    public void init(Application app) {

        FacebookSdk.sdkInitialize(app);

        AppEventsLogger.activateApp(app);

    }

}
