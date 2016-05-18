package net.xwdoor.roommate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.engine.User;
import net.xwdoor.roommate.net.RequestParameter;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/5/4 21:57.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddUserActivity extends BaseActivity {

    private boolean mIsUpdate = false;
    private User mUser;

    public static void startAct(Context context, User userInfo) {
        Intent intent = new Intent(context, AddUserActivity.class);
        if (userInfo != null) {
            intent.putExtra("user", userInfo);
        }
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {
        mUser = (User) getIntent().getSerializableExtra("user");
        if (mUser != null) {
            mIsUpdate = true;
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_user);
        final EditText etName = (EditText) findViewById(R.id.et_name);
        final EditText etPhone = (EditText) findViewById(R.id.et_phone);
        final EditText etMail = (EditText) findViewById(R.id.et_mail);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        final TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        Button btnAddUser = (Button) findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etPhone.getText())
                        || TextUtils.isEmpty(etMail.getText()) || TextUtils.isEmpty(etPassword.getText())) {
                    showToast("信息不能为空");
                } else if (mIsUpdate) {
                    //更新用户信息
                    mUser.setRealName(etName.getText().toString());
                    mUser.setMail(etMail.getText().toString());
                    mUser.setPhone(etPhone.getText().toString());
                    mUser.setPassword(etPassword.getText().toString());
                    updateUser(mUser);
                } else {
                    addUser(etName.getText().toString(), etPhone.getText().toString(),
                            etMail.getText().toString(), etPassword.getText().toString());
                }
            }
        });

        if (mIsUpdate) {
            etName.setText(mUser.getRealName());
            etMail.setText(mUser.getMail());
            etPhone.setText(mUser.getPhone());
            btnAddUser.setText("提交");
            tvTitle.setText("修改个人信息");
        }
    }

    private void updateUser(final User user) {
        ArrayList<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("_id", user.getId() + ""));
        params.add(new RequestParameter("realName", user.getRealName()));
        params.add(new RequestParameter("phone", user.getPhone()));
        params.add(new RequestParameter("mail", user.getMail()));
        params.add(new RequestParameter("password", user.getPassword()));
        RemoteService.getInstance().invoke(RemoteService.API_KEY_UPDATE_USER, this, params, new ARequestCallback() {
            @Override
            public void onSuccess(String content) {
                Global.me = user;
                Global.me.setPassword("");
                showToast(content);
                finish();
            }
        });
    }

    private void addUser(String name, String phone, String mail, String pwd) {
        ArrayList<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("realName", name));
        params.add(new RequestParameter("phone", phone));
        params.add(new RequestParameter("mail", mail));
        params.add(new RequestParameter("password", pwd));

        RemoteService.getInstance().invoke(RemoteService.API_KEY_ADD_USER, this, params, new ARequestCallback() {
            @Override
            public void onSuccess(String content) {
//                Global.init(AddUserActivity.this);
                showToast("添加成功");
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }
}