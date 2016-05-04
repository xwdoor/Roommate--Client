package net.xwdoor.roommate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;

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
        setContentView(R.layout.layout_add_user);
    }

    @Override
    protected void loadData() {

    }
}