package net.xwdoor.roommate.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.fragment.BaseFragment;
import net.xwdoor.roommate.fragment.BillListFragment;
import net.xwdoor.roommate.fragment.MeFragment;
import net.xwdoor.roommate.fragment.AddBillFragment;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MainActivity extends BaseActivity {

    private static final String TAG_PROPERTY_FRAGMENT = "property_fragment";
    private static final String TAG_BILL_FRAGMENT = "bill_fragment";
    private static final String TAG_ME_FRAGMENT = "me_fragment";
    public static final int REQUEST_CODE_ADD_BILL = 1;
    public static final int REQUEST_CODE_UPDATE_BILL = 2;
    public static final int RESULT_CODE_SAVE = 3;
    public static final int RESULT_CODE_DELETE = 4;

    private AddBillFragment mAddBillFragment;
    private BillListFragment mBillListFragment;
    private MeFragment mMeFragment;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startActForResult(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    @Override
    protected void initVariables() {
        mAddBillFragment = new AddBillFragment();
        mBillListFragment = new BillListFragment();
        mMeFragment = new MeFragment();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        RadioGroup rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_property://概况
                        initFragment(mAddBillFragment, TAG_PROPERTY_FRAGMENT);
                        break;
                    case R.id.rb_bill://账单
                        initFragment(mBillListFragment, TAG_BILL_FRAGMENT);
                        break;
                    case R.id.rb_me://我
                        initFragment(mMeFragment, TAG_ME_FRAGMENT);
                        break;
                }
            }
        });
        initFragment(mAddBillFragment, TAG_PROPERTY_FRAGMENT);
    }

    /**
     * 初始化Fragment
     *
     * @param fragment
     * @param fragmentTag
     */
    private void initFragment(BaseFragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_root, fragment, fragmentTag);
        transaction.commit();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_BILL://添加账单
                break;
            case REQUEST_CODE_UPDATE_BILL://修改账单
                //点击保存才有效果，点击返回不做处理
                if (data != null) {
                    BillInfo billInfo = (BillInfo) data.getSerializableExtra("billInfo");
                    if (resultCode == RESULT_CODE_SAVE) {
                        showLog("更新账单列表");
                        mBillListFragment.updateBill(billInfo);
                    } else if (resultCode == RESULT_CODE_DELETE) {
                        //删除账单
                        mBillListFragment.deleteBill(billInfo);
                    }
                }
                break;
        }
    }
}