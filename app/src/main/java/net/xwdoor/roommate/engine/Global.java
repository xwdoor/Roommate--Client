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
        RemoteService.getInstance().invoke(RemoteService.API_KEY_GET_ROOMMATES, activity,
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
        RemoteService.getInstance().invoke(RemoteService.API_KEY_GET_BILL_TYPE, activity,
                null, new RequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        BaseActivity.showLog("获取账单类型：%s", content);
                        Type listType = new TypeToken<ArrayList<BillType>>() {
                        }.getType();
                        sBillType = gson.fromJson(content, listType);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        sBillType = new ArrayList<>();
                        sBillType.add(new BillType(1, "市场"));
                        sBillType.add(new BillType(2, "超市"));
                        sBillType.add(new BillType(3, "水费"));
                        sBillType.add(new BillType(4, "电费"));
                        sBillType.add(new BillType(5, "气费"));
                        sBillType.add(new BillType(6, "其他"));
                    }

                    @Override
                    public void onCookieExpired() {

                    }
                });

    }

    public static String getBillType(int typeId) {
        String type = "其他";
        for (BillType billType : sBillType) {
            if (billType.typeId == typeId) {
                type = billType.typeName;
                break;
            }
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

            if (sUserList != null) {
                for (User user : sUserList) {
                    payerList.put(String.valueOf(user.getId()), user.getRealName());
                    payerList.put(user.getRealName(), String.valueOf(user.getId()));
                }
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
        } catch (Exception e) {
            id = -1;
        }
        return id;
    }

    /**
     * 获取所有付款人
     */
    public static String[] getPayers() {
        String[] payers = new String[sUserList.size()];
        for (int i = 0; i < sUserList.size(); i++) {
            payers[i] = sUserList.get(i).getRealName();
        }
        return payers;
    }
}
