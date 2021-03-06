package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.adapter.BillFragmentAdapter;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.RequestParameter;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/27.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillListActivity extends BaseActivity {

    private ListView lvList;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, BillListActivity.class);
        context.startActivity(intent);
    }

    public static void startActForResult(Context context) {
        Intent intent = new Intent(context, BillListActivity.class);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_bill_list);

        lvList = (ListView) findViewById(R.id.lv_list);
        findViewById(R.id.ddm_dropDownMenu).setVisibility(View.GONE);
        lvList.setVisibility(View.VISIBLE);
    }

    @Override
    protected void loadData() {
        ArrayList<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("payerId", Global.me.getId() + ""));
        RemoteService.getInstance().invoke(RemoteService.API_KEY_GET_USER_BILL,this,
                params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showJson(content);
                        Type listType = new TypeToken<ArrayList<BillInfo>>() {
                        }.getType();

                        ArrayList<BillInfo> bills = gson.fromJson(content, listType);
                        if(bills==null || bills.isEmpty()){
                            TextView tvView = new TextView(BillListActivity.this);
                            tvView.setText("无账单信息");
                            tvView.setGravity(Gravity.CENTER);
                            setContentView(tvView);
                        }else {
                            lvList.setAdapter(new BillFragmentAdapter(BillListActivity.this, bills));
                        }
                    }
                });
    }
}