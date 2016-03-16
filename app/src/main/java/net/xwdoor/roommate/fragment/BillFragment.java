package net.xwdoor.roommate.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.adapter.BillFragmentAdapter;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillFragment extends BaseFragment {

    private ListView lvList;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_bill,null);
        lvList = (ListView) view.findViewById(R.id.lv_list);

        return view;
    }

    @Override
    protected void initData() {
        RemoteService.getInstance().invoke(mActivity, RemoteService.API_KEY_GET_BILLS, null, new BaseActivity.ARequestCallback() {
            @Override
            public void onSuccess(String content) {
//                Response response = gson.fromJson(content,Response.class);
                //获取ArrayList<BillInfo>的类型，用于json解析
                BaseActivity.showLog("content",content);
                Type listType = new TypeToken<ArrayList<BillInfo>>(){}.getType();
                ArrayList<BillInfo> bills = gson.fromJson(content, listType);
                BillFragmentAdapter adapter = new BillFragmentAdapter(mActivity,bills);
                lvList.setAdapter(adapter);
            }
        });
    }
}
