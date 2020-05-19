package com.xdynamics.share;

import android.webkit.WebView;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share
 * @ClassName: ShareConfig
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/19 11:09
 * @UpdateUser:
 * @UpdateDate: 2020/5/19 11:09
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface ShareConfig {

    interface Facebook {

        String URL_HOME = "https://m.facebook.com/home.php";


        static void InjectViewPortGone(WebView view) {

            view.loadUrl("javascript:document.getElementById('header').style.display='none';document.getElementById('u_0_y').click();");

            view.loadUrl("javascript:(function(){})();");


        }


    }


}
