package net.xwdoor.roommate.adapter.superAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by XWdoor on 2016/4/15 015 9:03.
 * 博客：http://blog.csdn.net/xwdoor
 */
public abstract class BaseSuperAdapter<T> extends BaseAdapter {

    protected List<T> mList;
    protected int mLayoutResId;
    protected IMulViewType<T> mMulViewType;
    protected final LayoutInflater mInflater;

    public BaseSuperAdapter(Context context, List<T> items, int layoutResId) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = items;
        this.mLayoutResId = layoutResId;
        this.mMulViewType = null;
    }

    public BaseSuperAdapter(Context context, List<T> items, IMulViewType<T> mulViewType) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = items;
        this.mMulViewType = mulViewType;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mList.size())
            return null;
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMulViewType != null) {
            return mMulViewType.getItemViewType(position, mList.get(position));
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        if (mMulViewType != null) {
            return mMulViewType.getViewTypeCount();
        }
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuperViewHolder holder;
        int viewType = getItemViewType(position);
        if (mMulViewType != null) {
            holder = SuperViewHolder.getHolder(convertView, mInflater, mMulViewType.getLayoutId(viewType), parent);
        } else {
            holder = SuperViewHolder.getHolder(convertView, mInflater, mLayoutResId, parent);
        }
        onHolderCreated(holder, position, viewType);
        onBindItem(holder, getItemViewType(position), position, mList.get(position));
        return holder.mItemView;
    }

    /**
     * 当Item的绑定关系发生变化时发生，即当新的Item显示时发生
     *
     * @param holder 每个Item的视图，可以通过此设置UI数据
     * @param viewType Item的类型
     * @param position item的位置
     * @param t 该Item对应的数据
     */
    protected abstract void onBindItem(SuperViewHolder holder, int viewType, int position, T t);

    protected abstract void onHolderCreated(SuperViewHolder holder, int viewType, int position);
}
