package com.app.kekoo.common.glideloader.imagei;

import android.graphics.Bitmap;

/**
 * Created by mms on 2017/7/2.
 */

public interface ImageDownLoadCallBack {

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
