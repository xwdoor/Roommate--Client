package net.xwdoor.roommate.mockdata;

import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;

import java.util.Date;
import java.util.List;

/**
 * Created by XWdoor on 2016/3/15.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockGetBills extends MockService {
    @Override
    public String getJsonData() {
        BillInfo billInfo = new BillInfo(23.5f,1,1,new Date(System.currentTimeMillis()),"备注");
        List<BillInfo> billInfos = Global.getMockBillInfos();

        Response response = getSuccessResponse();
        response.setResult(gson.toJson(billInfos));
        return gson.toJson(response);
    }
}
