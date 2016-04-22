package net.xwdoor.roommate.engine;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import net.xwdoor.roommate.mockdata.MockService;
import net.xwdoor.roommate.net.RequestCallback;
import net.xwdoor.roommate.net.RequestParameter;
import net.xwdoor.roommate.net.Response;
import net.xwdoor.roommate.net.UrlConfigManager;
import net.xwdoor.roommate.net.UrlData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程服务器访问
 *
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
    public static final String API_KEY_GET_UNFINISH_BILL = "getUnfinishBill";

    private final Gson gson;
    private static RemoteService service = null;
    private RequestQueue mQueue;

    private RemoteService() {
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
     * @param apiKey   接口名称
     * @param params   请求参数
     * @param callback 回调方法/接口
     */
    public void invoke(String apiKey, Context context,
                       List<RequestParameter> params, RequestCallback callback) {
        //查询接口信息
        UrlData urlData = UrlConfigManager.findURL(context, apiKey);
        if (urlData != null && !TextUtils.isEmpty(urlData.getMockClass())) {
            //访问模拟接口
            Response response = new Response();
            try {
                //通过 url.xml 文件中的记录进行反射
                MockService mockService = (MockService) Class.forName(urlData.getMockClass()).newInstance();
                response = gson.fromJson(mockService.getJsonData(context), Response.class);

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
            StringRequest request = createRequest(urlData, params, callback);

            if(mQueue == null){
                mQueue = Volley.newRequestQueue(context);
            }
            mQueue.add(request);
        }

    }

    /**
     * 创建请求
     *
     * @param urlData  接口信息
     * @param params   请求参数
     * @param callback 回调接口
     * @return StringRequest请求对象
     */
    private StringRequest createRequest(UrlData urlData, final List<RequestParameter> params, final RequestCallback callback) {
        StringRequest request = null;
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
            RequestListener listener = new RequestListener(callback);
            request = new StringRequest(url, listener, listener);

        } else if (REQUEST_POST.equals(urlData.getNetType())) {
            RequestListener listener = new RequestListener(callback);
            request = new StringRequest(Request.Method.POST, urlData.getUrl(), listener, listener) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = null;
                    if (params != null && params.size() > 0) {
                        map = new HashMap<>();
                        //添加请求参数
                        for (RequestParameter param : params) {
                            map.put(param.getName(), param.getValue());
                        }
                    }
                    return map;
                }
            };
        }

        return request;
    }

    /**
     * 对回调进行简单封装
     */
    class RequestListener implements com.android.volley.Response.Listener<String>, com.android.volley.Response.ErrorListener {

        private final RequestCallback mCallBack;

        public RequestListener(RequestCallback callback) {
            this.mCallBack = callback;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (mCallBack != null) {
                mCallBack.onFailure(volleyError.getMessage());
            }
        }

        @Override
        public void onResponse(String s) {
            if (mCallBack != null) {
                Response response = gson.fromJson(s, Response.class);
                if (response.hasError()) {
                    mCallBack.onFailure(response.getError());
                } else {
                    mCallBack.onSuccess(response.getResult());
                }
            }
        }
    }
}
