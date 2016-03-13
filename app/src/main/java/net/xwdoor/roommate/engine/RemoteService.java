package net.xwdoor.roommate.engine;

import okhttp3.OkHttpClient;

/**
 * 远程服务器访问
 *
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class RemoteService {
    private static RemoteService service = null;
    private final OkHttpClient mHttpClient;

    private RemoteService() {
        mHttpClient = new OkHttpClient();
    }

    public static synchronized RemoteService getInstance() {
        if (RemoteService.service == null) {
            RemoteService.service = new RemoteService();
        }
        return RemoteService.service;
    }
}
