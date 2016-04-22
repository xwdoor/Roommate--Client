package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.db.BillDao;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;

import java.util.ArrayList;

/**
 * 获取某用户的账单
 *
 * Created by XWdoor on 2016/3/23.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockGetUserBill extends MockService {
    @Override
    public String getJsonData(Context context) {
        ArrayList<BillInfo> billInfos = BillDao.getInstance(context).getUserBill(Global.me.getId());

        Response response = getSuccessResponse();
        response.setResult(gson.toJson(billInfos));
        return gson.toJson(response);
    }
}
