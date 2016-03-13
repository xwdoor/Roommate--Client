package net.xwdoor.roommate.fragment;

import android.view.View;

import net.xwdoor.roommate.R;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MeFragment extends BaseFragment {

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_me,null);

        return view;
    }

    @Override
    protected void initData() {

    }
}
