package net.xwdoor.roommate.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

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
        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        ScaleAnimation anim = new ScaleAnimation(1, 1.3f, 1, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(2000);
        anim.setFillAfter(true);
        rlRoot.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
                params.add(new RequestParameter("userName", "xiaowei"));
                params.add(new RequestParameter("password", "xiaowei"));
                RemoteService.getInstance().invoke(SplashActivity.this, RemoteService.API_KEY_LOGIN, params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showLog("登录成功", content);
                        Global.me = gson.fromJson(content, User.class);
                        MainActivity.startAct(SplashActivity.this);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        super.onFailure(errorMessage);
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
