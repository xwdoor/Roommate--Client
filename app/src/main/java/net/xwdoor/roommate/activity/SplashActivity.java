package net.xwdoor.roommate.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import net.xwdoor.roommate.entity.AppInfo;
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
                Global.init(SplashActivity.this);
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

    }

    /**
     * 检测更新
     */
    private void checkUpdate() {
        //检测新版本
        RemoteService.getInstance().invoke(RemoteService.API_KEY_UPDATE, this, null, new ARequestCallback() {
            @Override
            public void onSuccess(String content) {
                AppInfo appInfo = gson.fromJson(content, AppInfo.class);
                int currentCode = getVersionCode();
                if (currentCode < appInfo.versionCode) {
                    //提示更新新版本
                    showUpdateDialog(appInfo);
                } else {
                    startLogin();
                }

            }

            @Override
            public void onFailure(String errorMessage) {
                startLogin();
            }
        });

    }

    /**
     * 开始登录
     */
    private void startLogin() {
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

    private int getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
        return packInfo.versionCode;
    }

    /**
     * 显示升级对话框
     */
    private void showUpdateDialog(final AppInfo appInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本");//设置标题：发现新版本
        builder.setMessage(appInfo.description);//设置内容
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //开始下载apk
                Uri uri = Uri.parse(appInfo.downloadUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                finish();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startLogin();
            }
        });

        //点击返回键的监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startLogin();
            }
        });
        builder.show();
    }
}
