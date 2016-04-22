package net.xwdoor.roommate.adapter.superAdapter;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by XWdoor on 2016/4/15 015 11:51.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class SuperAdapter<T> extends BaseSuperAdapter<T> {

    protected IViewItemBindData<T> mItemBindListener;
    protected OnItemClickListener mItemClickListener;
    protected OnItemLongClickListener mItemLongClickListener;

    public SuperAdapter(Context context, List<T> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    public SuperAdapter(Context context, List<T> items, IMulViewType<T> mulViewType) {
        super(context, items, mulViewType);
    }

    @Override
    protected void onBindItem(SuperViewHolder holder, int viewType, int position, T t) {
        if (mItemBindListener != null) {
            mItemBindListener.onBindItem(holder, viewType, position, t);
        } else if (t instanceof String) {
            //只显示字符串的ListView
        }
    }

    @Override
    protected void onHolderCreated(SuperViewHolder holder, final int viewType, final int position) {
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, viewType, position);
                }
            }
        });
        holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onItemLongClick(v, viewType, position);
                    return true;
                }
                return false;
            }
        });
    }

    public void setItemBindListener(IViewItemBindData<T> itemBindListener) {
        mItemBindListener = itemBindListener;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }
}
