package net.xwdoor.roommate.adapter.superAdapter;

/**
 * Created by XWdoor on 2016/4/15 015 10:41.
 * 博客：http://blog.csdn.net/xwdoor
 */
public interface IMulViewType<T> {

    int getViewTypeCount();

    int getItemViewType(int position, T t);

    int getLayoutId(int viewType);
}
