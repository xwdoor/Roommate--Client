package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.engine.User;
import net.xwdoor.roommate.net.Response;

import java.util.ArrayList;

/**
 * 获取室友列表
 *
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockGetRoommates extends MockService {
    @Override
    public String getJsonData(Context context) {
        ArrayList<User> users = new ArrayList<>();

        User user = new User("肖威","");
        user.setId(0);
        user.setRealName("肖威");
        users.add(user);

        user = new User("曹静波","");
        user.setId(1);
        user.setRealName("曹静波");
        users.add(user);

        user = new User("赵景明","");
        user.setId(2);
        user.setRealName("赵景明");
        users.add(user);

        user = new User("白杨","");
        user.setId(3);
        user.setRealName("白杨");
        users.add(user);

        Response response = getSuccessResponse();
        response.setResult(gson.toJson(users));
        return gson.toJson(response);
    }
}
