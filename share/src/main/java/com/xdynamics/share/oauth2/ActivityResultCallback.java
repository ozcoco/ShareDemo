package com.xdynamics.share.oauth2;

import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share.oauth2
 * @ClassName: ActivityResultable
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/13 9:12
 * @UpdateUser:
 * @UpdateDate: 2020/5/13 9:12
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface ActivityResultCallback {

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
