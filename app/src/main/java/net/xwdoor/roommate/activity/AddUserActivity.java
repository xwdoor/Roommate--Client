package net.xwdoor.roommate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.net.RequestParameter;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/5/4 21:57.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddUserActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AddUserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_user);
        final EditText etName = (EditText) findViewById(R.id.et_name);
        final EditText etPhone = (EditText) findViewById(R.id.et_phone);
        final EditText etMail = (EditText) findViewById(R.id.et_mail);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        Button btnAddUser = (Button) findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etPhone.getText())
                        || TextUtils.isEmpty(etMail.getText()) || TextUtils.isEmpty(etPassword.getText())) {
                    showToast("信息不能为空");
                }else {
                    addUser(etName.getText().toString(),etPhone.getText().toString(),
                            etMail.getText().toString(),etPassword.getText().toString());
                }
            }
        });
    }

    private void addUser(String name,String phone,String mail,String pwd) {
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