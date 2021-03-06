package com.example.myximalaya.base;

import android.app.Application;
import android.os.Handler;

import com.example.myximalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;

public class BaseApplication extends Application {

    private static Handler sHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
//        在Application中初始化CommonRequest ,请按照如下代码进行设置

        CommonRequest mXimalaya = CommonRequest.getInstanse();
        if(DTransferConstants.isRelease) {
            String mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af";
            mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8");
            mXimalaya.setPackid("com.app.test.android");
            mXimalaya.init(this ,mAppSecret);
        } else {
            String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
            mXimalaya.setPackid("com.ximalaya.qunfeng");
            mXimalaya.init(this ,mAppSecret);
        }

//        初始化LogUtil
        LogUtil.init(this.getPackageName(), false);

        sHandler = new Handler();
    }

    public static Handler getsHandler() {
        return sHandler;
    }
}
