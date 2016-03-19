package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.RequestParameter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddBillActivity extends BaseActivity {

    private EditText etMoney;
    private EditText etPayer;
    private EditText etDesc;
    private int mBillType;

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
        setContentView(R.layout.activity_add_bill);

        Button btnAddBill = (Button) findViewById(R.id.btn_add_bill_again);
        Button btnSave = (Button) findViewById(R.id.btn_save);
        RadioGroup rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        etMoney = (EditText) findViewById(R.id.et_money);
        etPayer = (EditText) findViewById(R.id.et_payer);
        etDesc = (EditText) findViewById(R.id.et_desc);

        etMoney.setText("35.5");
        etPayer.setText("肖威");
        etDesc.setText("描述");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBill();
            }
        });

        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddBill();
            }
        });

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_market://市场
                        mBillType = 0;
                        break;
                    case R.id.rb_super_market://超市
                        mBillType = 1;
                        break;
                    case R.id.rb_water://水费
                        mBillType = 2;
                        break;
                    case R.id.rb_electricity://电费
                        mBillType = 3;
                        break;
                    case R.id.rb_gas://气费
                        mBillType = 4;
                        break;
                    case R.id.rb_other://其他
                        mBillType = 5;
                        break;
                }
            }
        });
        rgGroup.check(R.id.rb_market);
    }

    /**
     * 再记一笔
     */
    private void saveAddBill() {
        ArrayList<RequestParameter> params = getBillInfoParam();

    }

    /**
     * 保存账单
     */
    private void saveBill() {
        ArrayList<RequestParameter> params = getBillInfoParam();
        RemoteService.getInstance().invoke(this, RemoteService.API_KEY_SAVE_BILL,
                params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showToast("保存成功");
                        Global.getMockBillInfos().add(getBillInfo());
                        finish();
                    }
                });


    }

    private BillInfo getBillInfo() {
        BillInfo billInfo = new BillInfo();
        billInfo.money = Float.parseFloat(etMoney.getText().toString());
        billInfo.billType = mBillType;
        billInfo.date = new Date(System.currentTimeMillis());
        billInfo.desc = etDesc.getText().toString();
        billInfo.payerId = 0;
        return billInfo;
    }

    private ArrayList<RequestParameter> getBillInfoParam() {

        ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
        params.add(new RequestParameter("money", etMoney.getText().toString()));
        params.add(new RequestParameter("billType", mBillType + ""));
        params.add(new RequestParameter("date", new Date(System.currentTimeMillis()).toString()));
        params.add(new RequestParameter("desc", etDesc.getText().toString()));
        params.add(new RequestParameter("payerId", "0"));
        return params;
    }

    @Override
    protected void loadData() {

    }
}