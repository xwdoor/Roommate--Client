package net.xwdoor.roommate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 账单数据库
 * <p>
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_BILL = "billData";
    public static final String TABLE_BILL_COLUMN_ID = "_id";
    public static final String TABLE_BILL_COLUMN_MONEY = "money";
    public static final String TABLE_BILL_COLUMN_PAYER_ID = "payerId";
    public static final String TABLE_BILL_COLUMN_RECORD_ID = "recordId";
    public static final String TABLE_BILL_COLUMN_BILL_TYPE = "billType";
    public static final String TABLE_BILL_COLUMN_IS_FINISH = "isFinish";
    public static final String TABLE_BILL_COLUMN_DATE = "date";
    public static final String TABLE_BILL_COLUMN_DESC = "desc";

    public static final String TABLE_USER = "R_User";
    public static final String TABLE_USER_COLUMN_ID = "_id";
    public static final String TABLE_USER_COLUMN_USER_NAME = "userName";
    public static final String TABLE_USER_COLUMN_REAL_NAME = "realName";
    public static final String TABLE_USER_COLUMN_PHONE = "phone";
    public static final String TABLE_USER_COLUMN_PASSWORD = "password";
    public static final String TABLE_USER_COLUMN_MAIL = "mail";

    public DbHelper(Context context) {
        super(context, "billData", null, 2);
    }

    String createBillData = "create table " + TABLE_BILL + " (" +
            "_id integer primary key autoincrement, " +
            "money decimal(10,2), " +
            "payerId integer," +
            "recordId integer," +
            "billType integer," +
            "isFinish integer," +
            "date varchar(20)," +
            "desc text)";


    String createUser = "create table " + TABLE_USER + " (" +
            "_id integer primary key autoincrement, " +
            "userName varchar(20)," +
            "realName varchar(20)," +
            "phone varchar(20)," +
            "password varchar(20)," +
            "mail varchar(20))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createBillData);
        db.execSQL(createUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("alter table " + TABLE_BILL + " add recordId integer default 0");
                db.execSQL("alter table " + TABLE_BILL + " add isFinish integer default 0");
                db.execSQL(createUser);
                break;
        }
    }
}
