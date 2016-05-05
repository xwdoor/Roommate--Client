package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.entity.BillType;
import net.xwdoor.roommate.net.RequestParameter;
import net.xwdoor.roommate.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddBillActivity extends BaseActivity {

    private EditText etMoney;
    private TextView tvPayer;
    private EditText etDesc;
    private int mBillType;
    private int mPayerId;
    private BillInfo mBillInfo;
    private boolean isUpdateBill;
    private RadioGroup rgGroup;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AddBillActivity.class);
        context.startActivity(intent);
    }

    public static void startActForResult(Context context, BillInfo billInfo) {
        Intent intent = new Intent(context, AddBillActivity.class);
        if (billInfo != null) {
            intent.putExtra("billInfo", billInfo);
            ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_CODE_UPDATE_BILL);
        } else {
            ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_CODE_ADD_BILL);
        }
    }

    @Override
    protected void initVariables() {
//        int mRadios = new int[]{};
        mBillInfo = (BillInfo) getIntent().getSerializableExtra("billInfo");
        if (mBillInfo != null) {
            isUpdateBill = true;
            mPayerId = mBillInfo.payerId;
            mBillType = mBillInfo.billType;
        } else {
            isUpdateBill = false;
            mPayerId = 1;
            mBillType = 1;
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_bill);

        Button btnAddBill = (Button) findViewById(R.id.btn_add_bill_again);
        Button btnSave = (Button) findViewById(R.id.btn_save);
        Button btnDelete = (Button) findViewById(R.id.btn_delete_bill);
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        etMoney = (EditText) findViewById(R.id.et_money);
        tvPayer = (TextView) findViewById(R.id.tv_payer);
        etDesc = (EditText) findViewById(R.id.et_desc);

        if (isUpdateBill) {
            etMoney.setText(mBillInfo.money + "");
            etDesc.setText(mBillInfo.desc);
            btnAddBill.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        //不管是添加还是编辑账单，都需要有付款人
        tvPayer.setText(Global.getPayerName(mPayerId));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMoney()) {
                    saveBill();
                }
            }
        });

        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMoney()) {
                    saveAddBill();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBill();
            }
        });

        tvPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mBillType = checkedId;
            }
        });
    }

    @Override
    protected void loadData() {
//        Global.getPayerName(mPayerId);
        initBillType(Global.sBillType);
    }

    /**
     * 检测金额是否为空
     */
    private boolean checkMoney() {
        if (TextUtils.isEmpty(etMoney.getText())) {
            showToast("请输入金额");
            return false;
        }
        return true;
    }

    /**
     * 删除账单
     */
    private void deleteBill() {
        final BillInfo billInfo = getBillInfo();
        ArrayList<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("_id", billInfo.id + ""));

//        BillDao.getInstance(this).deleteBill(billInfo.id);
        RemoteService.getInstance().invoke(RemoteService.API_KEY_DELETE_BILL, this,
                params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showToast("删除成功");
                        setResult(MainActivity.RESULT_CODE_DELETE, new Intent().putExtra("billInfo", billInfo));
                        finish();
                    }
                });
    }

    /**
     * 再记一笔
     */
    private void saveAddBill() {
        ArrayList<RequestParameter> params = getBillInfoParam();
        //添加账单
//        BillDao.getInstance(this).addBill(getBillInfo());

        RemoteService.getInstance().invoke(RemoteService.API_KEY_SAVE_BILL, this,
                params, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        showToast("保存成功");
                        etMoney.setText("");
                    }
                });
    }

    /**
     * 保存账单
     */
    private void saveBill() {
        ArrayList<RequestParameter> params = getBillInfoParam();
        if (isUpdateBill) {//更新账单
            getBillInfo();
            //更新数据库账单
//            BillDao.getInstance(this).updateBill(mBillInfo);

            //更新服务器账单
            RemoteService.getInstance().invoke(RemoteService.API_KEY_UPDATE_BILL, this,
                    params, new ARequestCallback() {
                        @Override
                        public void onSuccess(String content) {
                            showToast("保存成功");
                            showJson(content);
                            setResult(MainActivity.RESULT_CODE_SAVE, new Intent().putExtra("billInfo", mBillInfo));
                            finish();
                        }
                    });

        } else {
            //添加账单
//            BillDao.getInstance(this).addBill(getBillInfo());
            //添加服务器账单
            RemoteService.getInstance().invoke(RemoteService.API_KEY_SAVE_BILL, this,
                    params, new ARequestCallback() {
                        @Override
                        public void onSuccess(String content) {
                            showToast("保存成功");
                            setResult(MainActivity.RESULT_CODE_SAVE);
                            finish();
                        }
                    });
        }
    }

    /**
     * 获取账单对象
     * @return
     */
    private BillInfo getBillInfo() {
//        BillInfo billInfo = new BillInfo();
        if (mBillInfo == null) {
            mBillInfo = new BillInfo();
        }
        mBillInfo.money = Float.parseFloat(etMoney.getText().toString());
        mBillInfo.billType = mBillType;
        mBillInfo.date = DateUtils.DateToStr(new Date(System.currentTimeMillis()));
        mBillInfo.desc = etDesc.getText().toString();
        mBillInfo.payerId = mPayerId;
        return mBillInfo;
    }

    /**
     * 将账单对象转换为表单数据
     * @return
     */
    private ArrayList<RequestParameter> getBillInfoParam() {

        ArrayList<RequestParameter> params = new ArrayList<>();
        if (mBillInfo != null) {//更新账单需要有账单id
            params.add(new RequestParameter("_id", mBillInfo.id + ""));
        }
        params.add(new RequestParameter("money", etMoney.getText().toString()));
        params.add(new RequestParameter("billType", mBillType + ""));
        params.add(new RequestParameter("date", DateUtils.DateToStr(new Date())));
        params.add(new RequestParameter("desc", etDesc.getText().toString()));
        params.add(new RequestParameter("payerId", mPayerId + ""));
        params.add(new RequestParameter("recordId", Global.me.getId() + ""));

        return params;
    }

    /**
     * 初始化账单类型
     */
    private void initBillType(ArrayList<BillType> billTypes) {
        for (BillType billType : billTypes) {
            RadioButton rb = new RadioButton(this);
            rb.setId(billType.typeId);
            rb.setText(billType.typeName);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(layoutParams);
            rb.setChecked(true);
            rgGroup.addView(rb);
        }
        rgGroup.check(mBillType);
    }

    /**
     * 付款人选择框
     */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择付款人");
        builder.setIcon(R.mipmap.ic_launcher);
        final String[] payers = Global.getPayers();
        builder.setSingleChoiceItems(payers, mPayerId - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPayerId = Global.getPayerId(payers[which]);
                tvPayer.setText(payers[which]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}