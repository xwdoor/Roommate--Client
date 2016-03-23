package net.xwdoor.roommate.engine;

import net.xwdoor.roommate.entity.BillInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

                BillInfo billInfo = new BillInfo(23.5f,1,1,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),"备注");
                billInfos.add(billInfo);
                billInfos.add(billInfo);
                billInfos.add(billInfo);
            }
        }
        return  billInfos;
    }

    public static String getBillType(int typeId){
        String type = "其他";
        switch (typeId){
            case 0:
                type = "市场";
                break;
            case 1:
                type = "超市";
                break;
            case 2:
                type = "水费";
                break;
            case 3:
                type = "电费";
                break;
            case 4:
                type = "气费";
                break;
            default:
                type = "其他";
                break;
        }
        return type;
    }
}
