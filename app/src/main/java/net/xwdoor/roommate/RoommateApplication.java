package net.xwdoor.roommate;

import android.app.Application;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by XWdoor on 2016/4/21 021 10:34.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class RoommateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);

        //初始化日志系统，由于是静态初始化，每初始化一次，就会多重复打印一次日志，所以慎用
        Logger.initialize(
                Settings.getInstance()
                        .isShowMethodLink(true)
                        .isShowThreadInfo(false)
                        .setMethodOffset(1)
                        .setLogPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
        );
    }
}
