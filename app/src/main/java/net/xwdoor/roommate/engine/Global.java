package net.xwdoor.roommate.engine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.entity.BillType;
import net.xwdoor.roommate.net.RequestCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by XWdoor on 2016/3/19.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class Global {
    public static ArrayList<User> sUserList = null;
    public static User me;
    public static ArrayList<BillType> sBillType;

    public static void init(BaseActivity activity) {
        final Gson gson = new Gson();
        //初始化所有用户
        RemoteService.getInstance(activity).invoke(RemoteService.API_KEY_GET_ROOMMATES,
                null, new RequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        Type listType = new TypeToken<ArrayList<User>>() {
                        }.getType();

                        sUserList = gson.fromJson(content, listType);
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }

                    @Override
                    public void onCookieExpired() {

                    }
                });

        //获取账单类型
        RemoteService.getInstance(activity).invoke(RemoteService.API_KEY_GET_BILL_TYPE,
                null, new RequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        Type listType = new TypeToken<ArrayList<BillType>>() {
                        }.getType();
                        sBillType = gson.fromJson(content, listType);

                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }

                    @Override
                    public void onCookieExpired() {

                    }
                });

    }

    public static String getBillType(int typeId) {
        String type = "其他";
        switch (typeId) {
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

    private static HashMap<String, String> payerList = null;

    private static HashMap<String, String> getPayerList() {
        if (payerList == null) {
            payerList = new HashMap<>();
//            payerList.put(String.valueOf(0), "肖威");
//            payerList.put(String.valueOf(1), "曹静波");
//            payerList.put(String.valueOf(2), "赵景明");
//            payerList.put(String.valueOf(3), "白杨");
//            payerList.put("肖威", String.valueOf(0));
//            payerList.put("曹静波", String.valueOf(1));
//            payerList.put("赵景明", String.valueOf(2));
//            payerList.put("白杨", String.valueOf(3));

            for (User user : sUserList) {
                payerList.put(String.valueOf(user.getId()), user.getRealName());
                payerList.put(user.getRealName(), String.valueOf(user.getId()));
            }
        }
        return payerList;
    }

    /**
     * 根据付款人id获取付款人名字
     */
    public static String getPayerName(int payerId) {
        return getPayerList().get(payerId + "");
    }

    public static int getPayerId(String payerName) {
        int id;
        try {
            id = Integer.parseInt(getPayerList().get(payerName));
        }catch (Exception e){
            id = -1;
        }
        return id;
    }

    /**
     * 获取所有付款人
     */
    public static String[] getPayers() {
        String[] payers = new String[sUserList.size()];
        for(int i = 0;i<sUserList.size();i++){
            payers[i] = sUserList.get(i).getRealName();
        }
        return payers;
    }
}
