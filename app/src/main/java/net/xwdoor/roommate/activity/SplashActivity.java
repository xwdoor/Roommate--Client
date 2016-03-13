package net.xwdoor.roommate.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        ScaleAnimation anim = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(2000);
        anim.setFillAfter(true);
        rlRoot.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MainActivity.startAct(SplashActivity.this);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void loadData() {
        checkUpdate();
    }

    private void checkUpdate() {

    }
}
