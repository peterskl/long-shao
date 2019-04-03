package com.app.kekoo.common.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.app.kekoo.common.glideloader.loader.ImageLoader;


/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        ImageLoader.init(getApplicationContext());
//        MultiDex.install(this);
    }


    public static Context getAppContext() {

        if (baseApplication == null){
            baseApplication = new BaseApplication();
        }
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

//    /**
//     * 分包
//     *
//     * @param base
//     */
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }


}
