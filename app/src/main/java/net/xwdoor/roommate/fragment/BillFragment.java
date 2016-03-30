package net.xwdoor.roommate.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.activity.AddBillActivity;
import net.xwdoor.roommate.adapter.BillFragmentAdapter;
import net.xwdoor.roommate.adapter.ListDropDownAdapter;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.RequestCallback;
import net.xwdoor.roommate.view.DropDownMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 账单列表界面
 *
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillFragment extends BaseFragment {

    private ListView lvList;
    private BillFragmentAdapter mBillAdapter;
    private ArrayList<BillInfo> mBills;
    private DropDownMenu ddMenu;
    private String[] mHeader;
    private List<String> mPayers;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_bill, null);
        lvList = (ListView) view.findViewById(R.id.lv_list);
        ddMenu = (DropDownMenu) view.findViewById(R.id.ddm_dropDownMenu);

        //筛选条目
        mHeader = new String[]{"付款人","日期","价格"};
        mPayers = new ArrayList<>();

        //付款人筛选菜单
        mPayers.add("全部");
        mPayers.addAll(Arrays.asList(Global.getPayers()));
        ListView lvPayer = new ListView(mActivity);
        lvPayer.setDividerHeight(0);
        final ListDropDownAdapter payerAdapter = new ListDropDownAdapter(mActivity, mPayers);
        lvPayer.setAdapter(payerAdapter);

        //日期筛选菜单
        ListView lvDate = new ListView(mActivity);
        lvDate.setDividerHeight(0);

        //价格筛选菜单
        ListView lvPrice = new ListView(mActivity);
        lvPrice.setDividerHeight(0);

        ArrayList<View> filterViews = new ArrayList<>();
        filterViews.add(lvPayer);
        filterViews.add(lvDate);
        filterViews.add(lvPrice);

        lvList = new ListView(mActivity);
        ddMenu.setDropDownMenu(Arrays.asList(mHeader),filterViews,lvList);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillInfo billInfo = mBillAdapter.getItem(position);
                AddBillActivity.startActForResult(mActivity, billInfo);
            }
        });

        lvPayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                payerAdapter.setCheckItem(position);
                ddMenu.setTabText(position == 0 ? mHeader[0] : mPayers.get(position));
                mBillAdapter.filterBill(Global.getPayerId(mPayers.get(position)));
                ddMenu.closeMenu();
            }
        });

        return view;
    }

    @Override
    protected void initData() {

        RemoteService.getInstance().invoke(mActivity, RemoteService.API_KEY_GET_ALL_BILLS, null, new RequestCallback() {
            @Override
            public void onSuccess(String content) {
                //获取ArrayList<BillInfo>的类型，用于json解析
                mActivity.showLog("content", content);
                Type listType = new TypeToken<ArrayList<BillInfo>>() {
                }.getType();
                mBills = gson.fromJson(content, listType);
                mBillAdapter = new BillFragmentAdapter(mActivity, mBills);
                lvList.setAdapter(mBillAdapter);
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

    public void updateBill(BillInfo billInfo) {
        for (BillInfo info : mBills) {
            if(info.id == billInfo.id){
                info.date = billInfo.date;
                info.billType = billInfo.billType;
                info.money = billInfo.money;
                info.payerId = billInfo.payerId;
                info.desc = billInfo.desc;
//                info = billInfo;
                break;
            }
        }
        mBillAdapter.notifyDataSetChanged();
    }

    public void deleteBill(BillInfo billInfo) {
        for (BillInfo info : mBills) {
            if(info.id == billInfo.id){
                mBills.remove(info);
                break;
            }
        }
        mBillAdapter.notifyDataSetChanged();
    }
}
