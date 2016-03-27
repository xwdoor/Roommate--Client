package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;
import net.xwdoor.roommate.utils.DateUtils;

import java.util.Date;

/**
 * Created by XWdoor on 2016/3/25.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockDeleteBill extends MockService {
    @Override
    public String getJsonData(Context context) {
        BillInfo billInfo = new BillInfo(23.5f, 1, 1,
                DateUtils.DateToStr(new Date(System.currentTimeMillis())), "备注");

        Response response = getSuccessResponse();
        response.setResult(gson.toJson(billInfo));
        return gson.toJson(response);
    }
}
