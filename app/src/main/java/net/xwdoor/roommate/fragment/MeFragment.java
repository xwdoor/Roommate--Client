package net.xwdoor.roommate.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.activity.BillListActivity;
import net.xwdoor.roommate.activity.FinishBillActivity;
import net.xwdoor.roommate.activity.LoginActivity;
import net.xwdoor.roommate.activity.UserListActivity;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MeFragment extends BaseFragment {

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_me, null);

        Button btnUserList = (Button) view.findViewById(R.id.btn_user_list);
        Button btnMyBill = (Button) view.findViewById(R.id.btn_my_bill);
        Button btnFinishBill = (Button) view.findViewById(R.id.btn_finish_bill);
        Button btnLogout = (Button) view.findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = mActivity.getSharedPreferences("roommate", Context.MODE_PRIVATE);
                sp.edit().clear().apply();
                LoginActivity.startAct(mActivity);
                mActivity.finish();
            }
        });

        btnMyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示账单列表
                BillListActivity.startAct(mActivity);

            }
        });

        btnFinishBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishBillActivity.startAct(mActivity);
            }
        });

        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListActivity.startAct(mActivity);
            }
        });

        return view;
    }

    @Override
    protected void initData() {

    }
}
