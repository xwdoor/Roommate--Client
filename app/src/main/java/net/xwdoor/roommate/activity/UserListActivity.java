package net.xwdoor.roommate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import net.xwdoor.roommate.R;
import net.xwdoor.roommate.adapter.superAdapter.IViewItemBindData;
import net.xwdoor.roommate.adapter.superAdapter.SuperAdapter;
import net.xwdoor.roommate.adapter.superAdapter.SuperViewHolder;
import net.xwdoor.roommate.base.BaseActivity;
import net.xwdoor.roommate.engine.Global;
import net.xwdoor.roommate.engine.RemoteService;
import net.xwdoor.roommate.engine.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/5/4 20:06.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class UserListActivity extends BaseActivity {

    private ListView mLvUserList;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, UserListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_list);
        TextView tvAddUser = (TextView) findViewById(R.id.tv_add_user);
        TextView tvRefresh = (TextView) findViewById(R.id.tv_refresh);
        mLvUserList = (ListView) findViewById(R.id.lv_user_list);

        tvAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserActivity.startAct(UserListActivity.this,null);
            }
        });

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        initAdapter();
    }

    private void initAdapter() {
        SuperAdapter<User> adapter = new SuperAdapter<>(this, Global.sUserList,R.layout.item_user_list);
        adapter.setItemBindListener(new IViewItemBindData<User>() {
            @Override
            public void onBindItem(SuperViewHolder holder, int viewType, int position, User user) {
                holder.setText(R.id.tv_name,user.getRealName());
                holder.setText(R.id.tv_phone,user.getPhone());
            }
        });
        mLvUserList.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        //初始化所有用户
        RemoteService.getInstance().invoke(RemoteService.API_KEY_GET_ROOMMATES, this,
                null, new ARequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        Type listType = new TypeToken<ArrayList<User>>() {
                        }.getType();

                        Global.sUserList = gson.fromJson(content, listType);
                        initAdapter();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        showToast("刷新失败");
                    }
                });
    }
}