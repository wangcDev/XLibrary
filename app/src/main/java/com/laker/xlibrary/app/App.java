package com.laker.xlibrary.app;


import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.laker.xlibrary.loader.GlideImageLoader;
import com.laker.xlibs.XFrame;
import com.laker.xlibs.base.XApplication;

public class App extends XApplication {

    private static App baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        /**
         * 初始化全局图片加载框架
         * GlideImageLoader为你的图片加载框架实现类
         */
        XFrame.initXImageLoader(new GlideImageLoader(getApplicationContext()));
    }

    public static Context getAppContext() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
