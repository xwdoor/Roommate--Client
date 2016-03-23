package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.entity.BillInfo;
import net.xwdoor.roommate.net.Response;
import net.xwdoor.roommate.utils.DateUtils;

import java.util.Date;

/**
 * 更新账单
 *
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockUpdateBill extends MockService {
    @Override
    public String getJsonData(Context context) {
        Response response = getSuccessResponse();
        BillInfo billInfo = new BillInfo(23.5f, 1, 1,
                DateUtils.DateToStr(new Date(System.currentTimeMillis())), "备注");
        response.setResult(gson.toJson(billInfo));
        return gson.toJson(response);
    }
}
