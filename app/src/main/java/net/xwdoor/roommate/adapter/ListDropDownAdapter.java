package net.xwdoor.roommate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.xwdoor.roommate.R;

import java.util.List;


/**
 * 筛选菜单适配器
 * <p/>
 * Created by XWdoor on 2016/3/30 030 16:17.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class ListDropDownAdapter extends BaseAdapter {
    private final Context mContext;
    private List<String> mList;
    private int mCheckItemPosition;

    public ListDropDownAdapter(Context context, List<String> bills) {
        mContext = context;
        mList = bills;
    }

    public void setCheckItem(int position) {
        mCheckItemPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_drop_down_menu, null);
            holder = new ViewHolder();
            holder.tvText = (TextView) view.findViewById(R.id.tv_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvText.setText(mList.get(position));
        if (mCheckItemPosition != -1) {
            if (mCheckItemPosition == position) {
                holder.tvText.setTextColor(mContext.getResources().getColor(R.color.drop_down_selected));
                holder.tvText.setBackgroundResource(R.color.check_bg);
            } else {
                holder.tvText.setTextColor(mContext.getResources().getColor(R.color.drop_down_unselected));
                holder.tvText.setBackgroundResource(R.color.white);
            }
        }

        return view;
    }

    static class ViewHolder {
        TextView tvText;
    }
}
