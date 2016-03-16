package net.xwdoor.roommate.fragment;

import android.view.View;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.RemoteService;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillFragment extends BaseFragment {

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_bill,null);

        return view;
    }

    @Override
    protected void initData() {
        RemoteService.getInstance().invoke(mActivity, RemoteService.API_KEY_GET_BILLS, null, new BaseActivity.ARequestCallback() {
            @Override
            public void onSuccess(String content) {
//                List<BillInfo> bills = gson.fromJson()
            }
        });
    }
}
