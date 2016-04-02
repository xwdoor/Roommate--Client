package net.xwdoor.roommate.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.activity.AddBillActivity;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AddBillFragment extends BaseFragment {
    private TextView tvAllBill;
    private TextView tvMothBill;
    private TextView tvNotBill;
    private TextView tvAddBill;
    private ImageView ivHead;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_add_bill, null);

        tvAllBill = (TextView) view.findViewById(R.id.tv_all_bill);
        tvMothBill = (TextView) view.findViewById(R.id.tv_moth_bill);
        tvNotBill = (TextView) view.findViewById(R.id.tv_not_bill);
        tvAddBill = (TextView) view.findViewById(R.id.tv_add_bill);
        ivHead = (ImageView) view.findViewById(R.id.iv_head_portrait);

        tvAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBillActivity.startAct(mActivity);
            }
        });
        return view;
    }

    @Override
    protected void initData() {

    }

}
