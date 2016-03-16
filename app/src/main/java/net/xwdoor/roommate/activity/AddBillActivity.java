package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddBillActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AddBillActivity.class);
        context.startActivity(intent);
    }

    public static void startActForResult(Context context) {
        Intent intent = new Intent(context, AddBillActivity.class);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill);
    }

    @Override
    protected void loadData() {

    }
}