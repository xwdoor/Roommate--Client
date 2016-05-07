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

    private int filterPayerId = -1;
    private Boolean filterIsFinish = null;

    public BillFragmentAdapter(Context context, List<BillInfo> bills) {
        mContext = context;
        mList = bills;
        copyList = new ArrayList<>();
        copyList.addAll(bills);
    }

    /**
     * 设置筛选条件
     *
     * @param filterPayerId 付款人id，若显示全部，则为-1
     */
    public void setFilter(int filterPayerId) {
        this.filterPayerId = filterPayerId;
        filterBill();
    }

    /**
     * 设置筛选条件
     *
     * @param filterIsFinish 是否已结算，若显示全部，则为null
     */
    public void setFilter(Boolean filterIsFinish) {
        this.filterIsFinish = filterIsFinish;
        filterBill();
    }

    public void clearFilter() {
        this.filterPayerId = -1;
        this.filterIsFinish = null;
        filterBill();
    }

    /**
     * 筛选账单
     *
     * @param payerId  付款人id，若显示全部，则为-1
     * @param isfinish 是否结算，若显示全部，则为null
     */
    public void filterBill(int payerId, Boolean isfinish) {
        this.filterPayerId = payerId;
        this.filterIsFinish = isfinish;
        filterBill();
    }

    /**
     * 筛选账单
     */
    private void filterBill() {
        mList.clear();
        for (BillInfo billInfo : copyList) {
            if (filterPayerId == -1 || billInfo.payerId == filterPayerId) {//显示全部付款人或某付款人id的账单
                if (filterIsFinish == null || billInfo.isFinish == filterIsFinish) {//判断是否结算或显示全部
                    mList.add(billInfo);
                }
            }
        }

//        if (filterPayerId == -1) {//显示全部付款人
//            mList.addAll(copyList);
//        } else if (filterPayerId >= 0) {//显示某付款人id的账单
//            for (BillInfo billInfo : copyList) {
//                if (billInfo.payerId == filterPayerId) {
//                    mList.add(billInfo);
//                }
//            }
//        }
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_fragment_bill, null);
            holder = new ViewHolder();
            holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tvPayerName = (TextView) convertView.findViewById(R.id.tv_payerName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillInfo info = getItem(position);
        holder.tvMoney.setText("￥" + info.money);
        holder.tvDate.setText(info.date);
        if (position > 0) {
            if (getItem(position - 1).date.equals(info.date)) {
                holder.tvDate.setVisibility(View.INVISIBLE);
            } else {
                holder.tvDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvDate.setVisibility(View.VISIBLE);
        }
        holder.tvType.setText(Global.getBillType(info.billType));
        holder.tvPayerName.setText("(" + Global.getPayerName(info.payerId) + ")");
        return convertView;
    }

    static class ViewHolder {
        TextView tvMoney;
        TextView tvDate;
        TextView tvType;
        TextView tvPayerName;
    }
}
