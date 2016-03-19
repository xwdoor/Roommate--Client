package net.xwdoor.roommate.mockdata;

import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;

import java.util.Date;

/**
 * Created by XWdoor on 2016/3/19.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockSaveBillSuccess extends MockService {
    @Override
    public String getJsonData() {
        BillInfo billInfo = new BillInfo(23.5f, 1, 1, new Date(System.currentTimeMillis()), "备注");

        Response response = getSuccessResponse();
        response.setResult(gson.toJson(billInfo));
        return gson.toJson(response);
    }
}
