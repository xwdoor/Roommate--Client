package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.db.BillDao;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;

import java.util.List;

/**
 * Created by XWdoor on 2016/3/15.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockGetAllBills extends MockService {
    @Override
    public String getJsonData(Context context) {
//        BillInfo billInfo = new BillInfo(23.5f,1,1,new Date(System.currentTimeMillis()),"备注");
//        List<BillInfo> billInfos = Global.getMockBillInfos();
        List<BillInfo> billInfos = BillDao.getInstance(context).getBills();
        Response response = getSuccessResponse();
        response.setResult(gson.toJson(billInfos));
        return gson.toJson(response);
    }
}
