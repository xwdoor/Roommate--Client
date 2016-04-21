package net.xwdoor.roommate;

import android.app.Application;

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
    }
}
