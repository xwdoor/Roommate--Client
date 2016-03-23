package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.engine.User;
import net.xwdoor.roommate.net.Response;

/**
 * 模拟登录成功
 *
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockLoginSuccess extends MockService {
    @Override
    public String getJsonData(Context context) {
        Response response = getSuccessResponse();

        User user = new User("xwdoor","xwdoor");
        user.setMail("xwdoor@126.com");
        user.setPhone("18684033888");
        user.setRealName("肖威");
        response.setResult(gson.toJson(user));

        return gson.toJson(response);
    }
}
