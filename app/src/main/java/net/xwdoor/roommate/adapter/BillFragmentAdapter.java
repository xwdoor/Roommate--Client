package net.xwdoor.roommate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.entity.BillInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单数据适配器
 * <p/>
 * Created by XWdoor on 2016/3/16.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillFragmentAdapter extends BaseAdapter {

    private final Context mContext;
    private List<BillInfo> mList;
    private List<BillInfo> copyList;

    public BillFragmentAdapter(Context context, List<BillInfo> bills) {
        mContext = context;
        mList = bills;
        copyList = new ArrayList<>();
        copyList.addAll(bills);
    }

    /**
     * 筛选账单
     *
     * @param payerId 付款人id，若筛选全部，付款人id为-1或null
     */
    public void filterBill(int payerId) {

        mList.clear();
        if (payerId == -1) {//显示全部
            mList.addAll(copyList);
        } else if (payerId >= 0) {//显示某付款人id的账单
            for (BillInfo billInfo : copyList) {
                if (billInfo.payerId == payerId) {
                    mList.add(billInfo);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public BillInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_fragment_bill, null);
        TextView tvMoney = (TextView) view.findViewById(R.id.tv_money);
        TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
        TextView tvType = (TextView) view.findViewById(R.id.tv_type);
        TextView tvPayerName = (TextView) view.findViewById(R.id.tv_payerName);
        BillInfo info = getItem(position);
        tvMoney.setText("￥" + info.money);
        tvDate.setText(info.date);
        tvType.setText(Global.getBillType(info.billType));
        tvPayerName.setText("(" + Global.getPayerName(info.payerId) + ")");
        return view;
    }
}
