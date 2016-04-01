package net.xwdoor.roommate.engine;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;

import net.xwdoor.roommate.mockdata.MockService;
import net.xwdoor.roommate.net.RequestCallback;
import net.xwdoor.roommate.net.RequestParameter;
import net.xwdoor.roommate.net.Response;
import net.xwdoor.roommate.net.UrlConfigManager;
import net.xwdoor.roommate.net.UrlData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 远程服务器访问
 * <p/>
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class RemoteService {
    // 区分get还是post的枚举
    public static final String REQUEST_GET = "get";
    public static final String REQUEST_POST = "post";

    public static final String API_KEY_LOGIN = "login";
    public static final String API_KEY_GET_ALL_BILLS = "getAllBills";
    public static final String API_KEY_GET_USER_BILL = "getUserBill";
    public static final String API_KEY_SAVE_BILL = "saveBill";
    public static final String API_KEY_UPDATE_BILL = "updateBill";
    public static final String API_KEY_DELETE_BILL = "deleteBill";
    public static final String API_KEY_GET_BILL_TYPE = "getBillType";
    public static final String API_KEY_GET_ROOMMATES = "getRoommates";

    private final OkHttpClient mHttpClient;
    private final Gson gson;
    private Response mResponse;

    private static RemoteService service = null;

    private RemoteService() {
        mHttpClient = new OkHttpClient();
        gson = new Gson();
    }

    public static RemoteService getInstance() {
        if (service == null) {
            synchronized (RemoteService.class) {
                service = new RemoteService();
            }
        }
        return service;
    }


    /**
     * 调用网络请求
     *
     * @param activity
     * @param apiKey   接口名称
     * @param params   请求参数
     * @param callback 回调方法/接口
     */
    public void invoke(Activity activity, String apiKey,
                       List<RequestParameter> params, RequestCallback callback) {
        //查询接口信息
        UrlData urlData = UrlConfigManager.findURL(activity, apiKey);
        if (urlData != null && !TextUtils.isEmpty(urlData.getMockClass())) {
            //访问模拟接口
            Response response = new Response();
            try {
                //通过 url.xml 文件中的记录进行反射
                MockService mockService = (MockService) Class.forName(urlData.getMockClass()).newInstance();
                response = gson.fromJson(mockService.getJsonData(activity), Response.class);

            } catch (InstantiationException e) {
                response.setCode(-1);
                response.setError(e.getMessage());
            } catch (IllegalAccessException e) {
                response.setCode(-2);
                response.setError(e.getMessage());
            } catch (ClassNotFoundException e) {
                response.setCode(-3);
                response.setError(e.getMessage());
            }
            if (callback != null) {
                if (response.hasError()) {
                    callback.onFailure(response.getError());
                } else {
                    callback.onSuccess(response.getResult());
                }
            }
        } else {
            //访问真实接口
            Request request = createRequest(urlData, params);
            execRequest(request, callback);
        }

    }

    /**
     * 创建请求
     *
     * @param urlData 接口信息
     * @param params  请求参数
     * @return
     */
    private Request createRequest(UrlData urlData, List<RequestParameter> params) {
        Request request = null;
        if (REQUEST_GET.equals(urlData.getNetType())) {
            String url = urlData.getUrl();
            if (params != null && params.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                //添加请求参数
                for (RequestParameter param : params) {
                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(param.getName() + "=" + param.getValue());
                    } else {
                        stringBuilder.append("&" + param.getName() + "=" + param.getValue());
                    }
                }
                url += "?" + stringBuilder.toString();
            }
            request = new Request.Builder()
                    .url(url)
                    .build();

        } else if (REQUEST_POST.equals(urlData.getNetType())) {
            //表单数据
            FormBody.Builder builder = new FormBody.Builder();
            for (RequestParameter param : params) {
                builder.add(param.getName(), param.getValue());//添加键值对数据
            }
            RequestBody formBody = builder.build();
            //构建请求
            request = new Request.Builder()
                    .url(urlData.getUrl())
                    .post(formBody)
                    .build();
        }

        return request;

    }

    /**
     * 执行网络请求
     *
     * @param request  网络请求对象
     * @param callBack 回调接口
     */
    private void execRequest(Request request, final RequestCallback callBack) {
//        mResponse = new Response();
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                mResponse.setCode(-4);
//                mResponse.setError(e.getMessage());
//                callBack.onFailure(gson.toJson(mResponse));
                callBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                mResponse.setCode(0);
//                mResponse.setResult(response.body().string());
//                callBack.onSuccess(gson.toJson(response));
                callBack.onSuccess(response.body().string());
            }
        });
    }
}
