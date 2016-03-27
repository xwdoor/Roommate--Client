package net.xwdoor.roommate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 账单数据库
 *
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_BILL = "billData";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_PAYER_ID = "payerId";
    public static final String COLUMN_BILL_TYPE = "billType";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DESC = "desc";

    public BillOpenHelper(Context context) {
        super(context, "billData", null, 1);
    }

    String createBillData = "create table "+TABLE_BILL+" (" +
            "_id integer primary key autoincrement, " +
            "money decimal(10,2), " +
            "payerId integer," +
            "billType integer," +
            "date varchar(20)," +
            "desc text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createBillData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
//                db.execSQL(createBillData);
                break;
        }
    }
}
