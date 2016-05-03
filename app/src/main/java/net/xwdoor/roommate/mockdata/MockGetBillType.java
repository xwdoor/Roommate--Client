package net.xwdoor.roommate.mockdata;

import android.content.Context;

import net.xwdoor.roommate.entity.BillType;
import net.xwdoor.roommate.net.Response;

import java.util.ArrayList;

/**
 * 获取所有的账单类型
 *
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class MockGetBillType extends MockService {
    @Override
    public String getJsonData(Context context) {
        Response response = getSuccessResponse();
        ArrayList<BillType> billTypes = new ArrayList<>();
        billTypes.add(new BillType(1,"市场"));
        billTypes.add(new BillType(2,"超市"));
        billTypes.add(new BillType(3,"水费"));
        billTypes.add(new BillType(4,"电费"));
        billTypes.add(new BillType(5,"气费"));
        billTypes.add(new BillType(6,"其他"));
        response.setResult(gson.toJson(billTypes));
        return gson.toJson(response);
    }
}
