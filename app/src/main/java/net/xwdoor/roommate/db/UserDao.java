package net.xwdoor.roommate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.xwdoor.roommate.engine.User;

import java.util.ArrayList;

/**
 * Created by XWdoor on 2016/4/21 021 13:13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class UserDao {

    private final DbHelper mDbHelper;

    private static UserDao sInstance = null;

    private UserDao(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public static UserDao getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (UserDao.class) {
                if (sInstance == null) {
                    sInstance = new UserDao(ctx);
                }
            }
        }
        return sInstance;
    }

    public void addUser(User user){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_USER_COLUMN_MAIL,user.getMail());
        values.put(DbHelper.TABLE_USER_COLUMN_USER_NAME,user.getUserName());
        values.put(DbHelper.TABLE_USER_COLUMN_REAL_NAME,user.getRealName());
        values.put(DbHelper.TABLE_USER_COLUMN_PASSWORD,user.getPassword());
        values.put(DbHelper.TABLE_USER_COLUMN_PHONE,user.getPhone());
        database.insert(DbHelper.TABLE_USER, null, values);
        database.close();
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_USER, null, null, null, null, null, null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_USER_COLUMN_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(DbHelper.TABLE_USER_COLUMN_USER_NAME)));
                user.setRealName(cursor.getString(cursor.getColumnIndex(DbHelper.TABLE_USER_COLUMN_REAL_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(DbHelper.TABLE_USER_COLUMN_PHONE)));
                user.setMail(cursor.getString(cursor.getColumnIndex(DbHelper.TABLE_USER_COLUMN_MAIL)));

                users.add(user);
            }

            cursor.close();
        }
        database.close();

        return users;
    }
}
