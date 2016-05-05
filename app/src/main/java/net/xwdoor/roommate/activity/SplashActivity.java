package net.xwdoor.roommate.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;

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
                checkUpdate();
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
        //检查后进入登录界面
        SharedPreferences sp = getSharedPreferences("roommate", Context.MODE_PRIVATE);
        String loginName = sp.getString("loginName", "");
        String password = sp.getString("password", "");
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(password)) {
            LoginActivity.startAct(SplashActivity.this);
            finish();
        } else {
            autoLogin(loginName, password);
        }
    }

    /**
     * 自动登录
     */
    private void autoLogin(String loginName, String password) {
        ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter("loginName", loginName));
        params.add(new RequestParameter("pwd", password));
        RemoteService.getInstance().invoke(RemoteService.API_KEY_LOGIN, SplashActivity.this,
                params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showLog(getString(R.string.login_success));
//                        showJson(content);
//                        Global.me = gson.fromJson(content, User.class);
                        Global.me = JSON.parseObject(content, User.class);
                        MainActivity.startAct(SplashActivity.this);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        showLog("自动登录失败：%s", errorMessage);
                        LoginActivity.startAct(SplashActivity.this);
                        finish();
                    }
                });
    }
}
