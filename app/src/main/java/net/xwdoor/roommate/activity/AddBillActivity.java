package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.db.BillDao;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.engine.User;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.entity.BillType;
import net.xwdoor.roommate.net.RequestParameter;
import net.xwdoor.roommate.utils.DateUtils;

import java.lang.reflect.Type;
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
        } else {
            isUpdateBill = false;
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_bill);

        Button btnAddBill = (Button) findViewById(R.id.btn_add_bill_again);
        Button btnSave = (Button) findViewById(R.id.btn_save);
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        etMoney = (EditText) findViewById(R.id.et_money);
        etPayer = (EditText) findViewById(R.id.et_payer);
        etDesc = (EditText) findViewById(R.id.et_desc);

        if (isUpdateBill) {
            etMoney.setText(mBillInfo.money + "");
            etPayer.setText(mBillInfo.payerId + "");
            etDesc.setText(mBillInfo.desc);
            btnAddBill.setVisibility(View.GONE);
        }

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

//        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_market://市场
//                        mBillType = 0;
//                        break;
//                    case R.id.rb_super_market://超市
//                        mBillType = 1;
//                        break;
//                    case R.id.rb_water://水费
//                        mBillType = 2;
//                        break;
//                    case R.id.rb_electricity://电费
//                        mBillType = 3;
//                        break;
//                    case R.id.rb_gas://气费
//                        mBillType = 4;
//                        break;
//                    case R.id.rb_other://其他
//                        mBillType = 5;
//                        break;
//                }
//            }
//        });
//        rgGroup.check(R.id.rb_market);
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
        if (isUpdateBill) {//更新账单
            getBillInfo();
            //更新数据库账单
            BillDao.getInstance(this).updateBill(mBillInfo);

            //更新服务器账单
            RemoteService.getInstance().invoke(this, RemoteService.API_KEY_UPDATE_BILL,
                    params, new ARequestCallback() {
                        @Override
                        public void onSuccess(String content) {
                            showToast("保存成功");
                            setResult(MainActivity.RESULT_CODE_SAVE,new Intent().putExtra("billInfo",mBillInfo));
                            finish();
                        }
                    });

        } else {
            //添加账单
            BillDao.getInstance(this).addBill(getBillInfo());
            RemoteService.getInstance().invoke(this, RemoteService.API_KEY_SAVE_BILL,
                    params, new ARequestCallback() {
                        @Override
                        public void onSuccess(String content) {
                            showToast("保存成功");
                            setResult(MainActivity.RESULT_CODE_SAVE);
//                        Global.getMockBillInfos().add(getBillInfo());
                            finish();
                        }
                    });
        }
    }

    private BillInfo getBillInfo() {
//        BillInfo billInfo = new BillInfo();
        if (mBillInfo == null) {
            mBillInfo = new BillInfo();
        }
        mBillInfo.money = Float.parseFloat(etMoney.getText().toString());
        mBillInfo.billType = mBillType;
        mBillInfo.date = DateUtils.DateToStr(new Date(System.currentTimeMillis()));
        mBillInfo.desc = etDesc.getText().toString();
        mBillInfo.payerId = 0;
        return mBillInfo;
    }

    private ArrayList<RequestParameter> getBillInfoParam() {

        ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
        if (mBillInfo != null) {
            params.add(new RequestParameter("id", mBillInfo.id + ""));
        }
        params.add(new RequestParameter("money", etMoney.getText().toString()));
        params.add(new RequestParameter("billType", mBillType + ""));
        params.add(new RequestParameter("date", new Date(System.currentTimeMillis()).toString()));
        params.add(new RequestParameter("desc", etDesc.getText().toString()));
        params.add(new RequestParameter("payerId", "0"));
        return params;
    }

    @Override
    protected void loadData() {
        //获取室友列表
        RemoteService.getInstance().invoke(this, RemoteService.API_KEY_GET_ROOMMATES, null, new ARequestCallback() {
            @Override
            public void onSuccess(String content) {
                Type listType = new TypeToken<ArrayList<User>>() {
                }.getType();
                ArrayList<User> userList = gson.fromJson(content, listType);
                if (isUpdateBill) {
                    for (User user : userList) {
                        if (user.getId() == mBillInfo.payerId) {
                            etPayer.setText(user.getRealName());
                            break;
                        }
                    }
                }
            }
        });

        //获取账单类型
        RemoteService.getInstance().invoke(this, RemoteService.API_KEY_GET_BILL_TYPE, null, new ARequestCallback() {
            @Override
            public void onSuccess(String content) {
                Type listType = new TypeToken<ArrayList<BillType>>() {
                }.getType();
                ArrayList<BillType> billTypes = gson.fromJson(content, listType);
                initBillType(billTypes);
            }
        });
    }

    /**
     * 初始化账单类型
     */
    private void initBillType(ArrayList<BillType> billTypes) {
        for (BillType billType : billTypes) {
            RadioButton rb = new RadioButton(this);
            rb.setId(billType.type);
            rb.setText(billType.desc);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(layoutParams);
            rb.setChecked(true);
            rgGroup.addView(rb);
        }
        if (isUpdateBill) {
            rgGroup.check(mBillInfo.billType);
        }
    }
}