package net.xwdoor.roommate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.xwdoor.roommate.entity.BillInfo;

import java.util.ArrayList;

/**
 * 账单数据库查询，单例模式
 * <p/>
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillDao {

    private final DbHelper mDbHelper;

    private static BillDao sInstance = null;

    public BillDao(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public static BillDao getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (BillDao.class) {
                if (sInstance == null) {
                    sInstance = new BillDao(ctx);
                }
            }
        }
        return sInstance;
    }

    /**
     * 添加账单
     */
    public void addBill(BillInfo billInfo) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = buildBillValues(billInfo);
        database.insert(DbHelper.TABLE_BILL, null, values);
        database.close();
    }

    public void deleteBill(int id) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_BILL, DbHelper.TABLE_BILL_COLUMN_ID + "=?", new String[]{id + ""});
    }

    public void updateBill(BillInfo billInfo) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = buildBillValues(billInfo);

        database.update(DbHelper.TABLE_BILL, values, DbHelper.TABLE_BILL_COLUMN_ID + "=?", new String[]{billInfo.id + ""});
    }

    /**
     * 获取所有账单数据
     */
    public ArrayList<BillInfo> getBills() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_BILL, null, null, null, null, null, "date desc");

        ArrayList<BillInfo> billInfos = parseBills(cursor);
        database.close();
        return billInfos;
    }

    /**
     * 获取某成员账单数据
     */
    public ArrayList<BillInfo> getUserBill(int id) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_BILL, null, DbHelper.TABLE_BILL_COLUMN_PAYER_ID + "=?", new String[]{id + ""}, null, null, "date desc");

        ArrayList<BillInfo> billInfos = parseBills(cursor);

        database.close();
        return billInfos;
    }

    /**
     * 按用户获取账单进行结算
     */
    public ArrayList<BillInfo> getUnfinishedBill() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        String sql = String.format("SELECT %s,SUM(%s) as %s FROM %s WHERE %s=%s GROUP BY %s",
                DbHelper.TABLE_BILL_COLUMN_PAYER_ID, DbHelper.TABLE_BILL_COLUMN_MONEY, DbHelper.TABLE_BILL_COLUMN_MONEY,
                DbHelper.TABLE_BILL, DbHelper.TABLE_BILL_COLUMN_IS_FINISH, 0, DbHelper.TABLE_BILL_COLUMN_PAYER_ID);
//        BaseActivity.showLog("查询账单：%s",sql);
//        sql = "SELECT payerId,SUM(money) as money,isFinish FROM billData where isFinish=0 GROUP BY payerId";
        Cursor cursor = database.rawQuery(sql, null);
        ArrayList<BillInfo> billInfos = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BillInfo billInfo = new BillInfo();
                billInfo.payerId = cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_PAYER_ID));
                billInfo.money = cursor.getFloat(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_MONEY));
                billInfos.add(billInfo);
//                Logger.object(billInfo);
            }
            cursor.close();
        }

        database.close();
        return billInfos;
    }

    /**
     * 结算所有账单
     */
    public void finishBills() {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_BILL_COLUMN_IS_FINISH, 1);

        database.update(DbHelper.TABLE_BILL, values, DbHelper.TABLE_BILL_COLUMN_IS_FINISH + "=?", new String[]{"0"});
    }

    private ArrayList<BillInfo> parseBills(Cursor cursor) {
        ArrayList<BillInfo> billInfos = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BillInfo billInfo = new BillInfo();
                billInfo.id = cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_ID));
                billInfo.money = cursor.getFloat(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_MONEY));
                billInfo.payerId = cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_PAYER_ID));
                billInfo.recordId = cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_RECORD_ID));
                billInfo.billType = cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_BILL_TYPE));
                billInfo.isFinish = cursor.getInt(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_IS_FINISH)) == 1;
                billInfo.date = cursor.getString(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_DATE));
                billInfo.desc = cursor.getString(cursor.getColumnIndex(DbHelper.TABLE_BILL_COLUMN_DESC));
                billInfos.add(billInfo);
            }
            cursor.close();
        }

        return billInfos;
    }

    private ContentValues buildBillValues(BillInfo billInfo) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_BILL_COLUMN_MONEY, billInfo.money);
        values.put(DbHelper.TABLE_BILL_COLUMN_PAYER_ID, billInfo.payerId);
        values.put(DbHelper.TABLE_BILL_COLUMN_RECORD_ID, billInfo.recordId);
        values.put(DbHelper.TABLE_BILL_COLUMN_BILL_TYPE, billInfo.billType);
        values.put(DbHelper.TABLE_BILL_COLUMN_IS_FINISH, billInfo.isFinish ? 1 : 0);
        values.put(DbHelper.TABLE_BILL_COLUMN_DATE, billInfo.date);
        values.put(DbHelper.TABLE_BILL_COLUMN_DESC, billInfo.desc);
        return values;
    }
}
