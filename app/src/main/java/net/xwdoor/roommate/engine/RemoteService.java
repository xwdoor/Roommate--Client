package net.xwdoor.roommate.engine;

import com.google.gson.Gson;

import net.xwdoor.roommate.mockdata.MockLoginSuccess;
import net.xwdoor.roommate.mockdata.MockService;
import net.xwdoor.roommate.net.RequestCallback;
import net.xwdoor.roommate.net.Response;

import okhttp3.OkHttpClient;

/**
 * 远程服务器访问
 * <p>
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class RemoteService {
    public static final String API_LOGIN = "login";

    private static RemoteService service = null;
    private final OkHttpClient mHttpClient;
    private final Gson gson;

    private RemoteService() {
        mHttpClient = new OkHttpClient();
        gson = new Gson();
    }

    public static synchronized RemoteService getInstance() {
        if (RemoteService.service == null) {
            RemoteService.service = new RemoteService();
        }
        return RemoteService.service;
    }

    public void invoke(String apiKey, RequestCallback callback) {
        MockService mockService = new MockLoginSuccess();
        Response responseInJson = gson.fromJson(mockService.getJsonData(), Response.class);
        if (callback != null) {
            if (responseInJson.isFail()) {
                callback.onFail(responseInJson.getError());
            } else {
                callback.onSuccess(responseInJson.getResult());
            }
        }
    }
}
