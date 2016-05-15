package net.xwdoor.roommate.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.activity.AddUserActivity;
import net.xwdoor.roommate.activity.BillListActivity;
import net.xwdoor.roommate.activity.FinishBillActivity;
import net.xwdoor.roommate.activity.LoginActivity;
import net.xwdoor.roommate.activity.UserListActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.RequestParameter;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MeFragment extends BaseFragment {

    private TextView mTvTotalBill;
    private TextView mTvUnfinishedBill;
    private TextView mTvFinishBill;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_me, null);

        mTvTotalBill = (TextView) view.findViewById(R.id.tv_total_bill);
        mTvUnfinishedBill = (TextView) view.findViewById(R.id.tv_unfinished_bill);
        mTvFinishBill = (TextView) view.findViewById(R.id.tv_finish_bill);
        Button btnUserList = (Button) view.findViewById(R.id.btn_user_list);
        Button btnMyBill = (Button) view.findViewById(R.id.btn_my_bill);
        Button btnFinishBill = (Button) view.findViewById(R.id.btn_finish_bill);
        Button btnLogout = (Button) view.findViewById(R.id.btn_logout);
        Button btnPersonalInfo = (Button) view.findViewById(R.id.btn_personal_info);

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

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserActivity.startAct(mActivity, Global.me);
            }
        });

        return view;
    }

    @Override
    protected void initData() {
        //加载个人账单，显示未结算、已结算、总账单
        ArrayList<RequestParameter> params = new ArrayList<>();
        params.add(new RequestParameter("payerId", Global.me.getId() + ""));
        RemoteService.getInstance().invoke(RemoteService.API_KEY_GET_USER_BILL, mActivity,
                params, mActivity.new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        Type listType = new TypeToken<ArrayList<BillInfo>>() {
                        }.getType();

                        ArrayList<BillInfo> bills = gson.fromJson(content, listType);
                        if (bills != null && !bills.isEmpty()) {
                            float totalBill = 0, finishBill = 0, unfinishedBill = 0;
                            for (BillInfo info : bills) {
                                totalBill += info.money;
                                if (info.isFinish) {
                                    finishBill += info.money;
                                } else {
                                    unfinishedBill += info.money;
                                }
                            }
                            mTvTotalBill.setText(totalBill + "");
                            mTvFinishBill.setText(finishBill + "");
                            mTvUnfinishedBill.setText(unfinishedBill + "");
                        }
                    }

                }
        );
    }
}
