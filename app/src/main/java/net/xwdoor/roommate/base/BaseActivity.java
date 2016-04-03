package net.xwdoor.roommate.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import net.xwdoor.roommate.net.RequestCallback;

/**
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG_LOG = "//";
    protected Gson gson;

    /** 打印日志 */
    public static void showLog(String message, Object... args){
        Logger.i(message, args);
    }

    /** 打印Json日志 */
    public static void showJson(String json){
        Logger.json(json);
    }

    /** 打印数组、列表集合、实体类 */
    public static void showObject(Object object){
        Logger.object(object);
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /**
     * 初始化变量，包括启动Activity传过来的变量和Activity内的变量
     */
    protected abstract void initVariables();

    /**
     * 初始化视图，加载layout布局文件，初始化控件，为控件挂上事件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 加载数据，包括网络数据，缓存数据，用户数据，调用服务器接口获取的数据
     */
    protected abstract void loadData();

    public abstract class ARequestCallback implements RequestCallback{

        @Override
        public void onFailure(String errorMessage) {
            showLog("请求失败：%s",errorMessage);
            showToast("网络错误");
        }

        @Override
        public void onCookieExpired() {

        }
    }
}
