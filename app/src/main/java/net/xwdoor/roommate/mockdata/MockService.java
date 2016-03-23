package net.xwdoor.roommate.mockdata;

import android.content.Context;

import com.google.gson.Gson;

import net.xwdoor.roommate.net.Response;

/**
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public abstract class MockService {

    protected final Gson gson;

    public MockService() {
        gson = new Gson();
    }

    public abstract String getJsonData(Context context);

    protected Response getSuccessResponse() {
        Response response = new Response();

        response.setCode(0);
        response.setError("");

        return response;
    }

    protected Response getFailResponse(int errorType, String errorMessage) {
        Response response = new Response();
        response.setCode(errorType);
        response.setError(errorMessage);

        return response;
    }
}
