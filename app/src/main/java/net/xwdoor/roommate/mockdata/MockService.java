package net.xwdoor.roommate.mockdata;

import net.xwdoor.roommate.net.Response;

/**
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public abstract class MockService {
    public abstract String getJsonData();

    protected Response getSuccessResponse(){
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
