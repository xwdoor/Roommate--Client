package net.xwdoor.roommate.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.adapter.BillFragmentAdapter;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.RequestCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillFragment extends BaseFragment {

    private ListView lvList;
    private BillFragmentAdapter mAdapter;
    private ArrayList<BillInfo> mBills;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_bill, null);
        lvList = (ListView) view.findViewById(R.id.lv_list);

        return view;
    }

    @Override
    protected void initData() {
        RemoteService.getInstance().invoke(mActivity, RemoteService.API_KEY_GET_BILLS, null, new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                //获取ArrayList<BillInfo>的类型，用于json解析
                mActivity.showLog("content", content);
                Type listType = new TypeToken<ArrayList<BillInfo>>() {
                }.getType();
                mBills = gson.fromJson(content, listType);
                mAdapter = new BillFragmentAdapter(mActivity, mBills);
                lvList.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                mActivity.showToast(errorMessage);
            }

            @Override
            public void onCookieExpired() {

            }
        });
    }
}
