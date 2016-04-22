package net.xwdoor.roommate.adapter.superAdapter;

/**
 * Created by XWdoor on 2016/4/15 015 12:53.
 * 博客：http://blog.csdn.net/xwdoor
 */
public interface IViewItemBindData<T> {
    void onBindItem(SuperViewHolder holder, int viewType, int position, T t);
}
