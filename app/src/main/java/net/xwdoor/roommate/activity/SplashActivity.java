package net.xwdoor.roommate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

import net.xwdoor.roommate.BuildConfig;
import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.engine.User;
import net.xwdoor.roommate.net.RequestParameter;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initVariables() {
        //初始化日志系统，由于是静态初始化，每初始化一次，就会多重复打印一次日志，所以慎用
        Logger.initialize(
                Settings.getInstance()
                        .isShowMethodLink(true)
                        .isShowThreadInfo(false)
                        .setMethodOffset(1)
                        .setLogPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
        );
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        final RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        final ScaleAnimation anim = new ScaleAnimation(1, 1.3f, 1, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(2000);
        anim.setFillAfter(true);
        rlRoot.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
                params.add(new RequestParameter("userName", "xiaowei"));
                params.add(new RequestParameter("password", "xiaowei"));
                RemoteService.getInstance(SplashActivity.this).invoke(RemoteService.API_KEY_LOGIN, params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showLog("登录成功");
//                        showJson(content);
                        Global.me = gson.fromJson(content, User.class);
                        MainActivity.startAct(SplashActivity.this);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        showLog("自动登录失败");
                        LoginActivity.startAct(SplashActivity.this);
                        finish();
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void loadData() {
        Global.init(this);
        checkUpdate();
    }

    /**
     * 检测更新
     */
    private void checkUpdate() {

    }
}
