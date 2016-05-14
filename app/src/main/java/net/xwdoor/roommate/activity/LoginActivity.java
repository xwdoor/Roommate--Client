package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.engine.User;
import net.xwdoor.roommate.net.RequestParameter;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class LoginActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startActForResult(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        final EditText etLoginName = (EditText) findViewById(R.id.et_login_name);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.init(LoginActivity.this);
                String loginName = etLoginName.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(password)) {
                    showToast("请输入帐号密码");
                }else {
                    //保存登录信息
                    SharedPreferences sp = getSharedPreferences("roommate", Context.MODE_PRIVATE);
                    sp.edit().putString("loginName",loginName).apply();
                    sp.edit().putString("password",password).apply();
                    autoLogin(loginName,password);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void autoLogin(String loginName, String password) {
        ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter("loginName", loginName));
        params.add(new RequestParameter("pwd", password));
        RemoteService.getInstance().invoke(RemoteService.API_KEY_LOGIN, LoginActivity.this,
                params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showLog(getString(R.string.login_success));
                        showToast("登录成功");
                        Global.me = JSON.parseObject(content, User.class);
                        MainActivity.startAct(LoginActivity.this);
                        finish();
                    }
                });
    }
}