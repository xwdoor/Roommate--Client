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
import net.xwdoor.roommate.fragment.BaseFragment;
import net.xwdoor.roommate.fragment.BillFragment;
import net.xwdoor.roommate.fragment.MeFragment;
import net.xwdoor.roommate.fragment.PropertyFragment;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MainActivity extends BaseActivity {

    private static final String TAG_PROPERTY_FRAGMENT = "property_fragment";
    private static final String TAG_BILL_FRAGMENT = "bill_fragment";
    private static final String TAG_ME_FRAGMENT = "me_fragment";
    private PropertyFragment mPropertyFragment;
    private BillFragment mBillFragment;
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
        mPropertyFragment = new PropertyFragment();
        mBillFragment = new BillFragment();
        mMeFragment = new MeFragment();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initFragment(mPropertyFragment, TAG_PROPERTY_FRAGMENT);
        RadioGroup rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_property://概况
                        initFragment(mPropertyFragment, TAG_PROPERTY_FRAGMENT);
                        break;
                    case R.id.rb_bill://账单
                        initFragment(mBillFragment, TAG_BILL_FRAGMENT);
                        break;
                    case R.id.rb_me://我
                        initFragment(mMeFragment, TAG_ME_FRAGMENT);
                        break;
                }
            }
        });

    }

    /**
     * 初始化Fragment
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
}