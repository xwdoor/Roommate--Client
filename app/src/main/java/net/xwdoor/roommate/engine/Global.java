package net.xwdoor.roommate.engine;

import net.xwdoor.roommate.entity.BillInfo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by XWdoor on 2016/3/19.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Global {
    private static ArrayList<BillInfo> billInfos = null;

    /** 模拟账单数据 */
    public static ArrayList<BillInfo> getMockBillInfos(){
        if(billInfos == null){
            synchronized (Global.class){
                billInfos = new ArrayList<>();

                BillInfo billInfo = new BillInfo(23.5f,1,1,new Date(System.currentTimeMillis()),"备注");
                billInfos.add(billInfo);
                billInfos.add(billInfo);
                billInfos.add(billInfo);
            }
        }
        return  billInfos;
    }
}
