package net.xwdoor.roommate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.adapter.superAdapter.IViewItemBindData;
import net.xwdoor.roommate.adapter.superAdapter.SuperAdapter;
import net.xwdoor.roommate.adapter.superAdapter.SuperViewHolder;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.RequestParameter;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/4/22 022 10:03.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class FinishBillActivity extends BaseActivity {

    private ListView mLvUnfinishBill;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, FinishBillActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_finish_bill);
        mLvUnfinishBill = (ListView) findViewById(R.id.lv_finish_bill);
        Button btnFinishBill = (Button) findViewById(R.id.btn_finish_bill);
        btnFinishBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BillDao.getInstance(FinishBillActivity.this).finishBills();
                ArrayList<RequestParameter> params = new ArrayList<>();
                params.add(new RequestParameter("password", "xwdoor"));
                RemoteService.getInstance().invoke(RemoteService.API_KEY_FINISH_BILL, FinishBillActivity.this, params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showToast("结算完成");
                        finish();
                    }
                });

            }
        });
    }

    @Override
    protected void loadData() {
        RemoteService.getInstance().invoke(RemoteService.API_KEY_GET_UNFINISHED_BILL, this, null, new ARequestCallback() {
            @Override
            public void onSuccess(String content) {
                Type listType = new TypeToken<ArrayList<BillInfo>>() {
                }.getType();

                ArrayList<BillInfo> bills = gson.fromJson(content, listType);
                initAdapter(bills);
            }
        });
    }

    private void initAdapter(ArrayList<BillInfo> bills) {
        if(bills!=null && bills.size()>0){
            SuperAdapter<BillInfo> adapter = new SuperAdapter<>(FinishBillActivity.this, bills, R.layout.item_fragment_bill);
            adapter.setItemBindListener(new IViewItemBindData<BillInfo>() {
                @Override
                public void onBindItem(SuperViewHolder holder, int viewType, int position, BillInfo billInfo) {
                    holder.setText(R.id.tv_type, "未结算");
                    holder.setText(R.id.tv_money, billInfo.money + "");
                    holder.setText(R.id.tv_payerName, "(" + Global.getPayerName(billInfo.payerId) + ")");
                }
            });
            mLvUnfinishBill.setAdapter(adapter);

            initFooterView(bills);
        }else {
            TextView tvView = new TextView(this);
            tvView.setText("无可结算账单");
            setContentView(tvView);
        }
    }

    private void initFooterView(final ArrayList<BillInfo> bills) {

        LinearLayout llContainer = new LinearLayout(this);
        llContainer.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvView = new TextView(this);
        tvView.setText("人数：");
        llContainer.addView(tvView);

        EditText etCount = new EditText(this);
        etCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        etCount.setMinimumWidth(100);
        etCount.setText(bills.size()+"");
        llContainer.addView(etCount);

        float avg = 0;
        for (BillInfo bill : bills) {
            avg += bill.money;
        }
        avg = avg / bills.size();

        final TextView tvAvg = new TextView(this);
        tvAvg.setText("人均：" + avg);
        llContainer.addView(tvAvg);

        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    float avg = 0;
                    for (BillInfo bill : bills) {
                        avg += bill.money;
                    }
                    avg = avg / Integer.parseInt(s.toString());

                    tvAvg.setText("人均：" + avg);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLvUnfinishBill.addFooterView(llContainer);
    }
}