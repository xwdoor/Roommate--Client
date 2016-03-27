package net.xwdoor.roommate.fragment;

import android.view.View;
import android.widget.Button;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.activity.BillListActivity;

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

        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示账单列表
                BillListActivity.startAct(mActivity);
            }
        });

        return view;
    }

    /**
     * 加载我的账单
     */
    private void loadMyBill() {

    }

    @Override
    protected void initData() {

    }
}
