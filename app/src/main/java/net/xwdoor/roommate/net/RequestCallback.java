package net.xwdoor.roommate.net;

/**
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public interface RequestCallback
{
    void onSuccess(String content);

    void onFail(String errorMessage);

    void onCookieExpired();
}
