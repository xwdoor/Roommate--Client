package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.db.BillDao;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/4/22 022 10:19.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockGetUnfinishBill extends MockService {
    @Override
    public String getJsonData(Context context) {

        ArrayList<BillInfo> billInfos = BillDao.getInstance(context).getUnfinishedBill();
        Response response = getSuccessResponse();
        response.setResult(gson.toJson(billInfos));

        return gson.toJson(response);
    }
}
