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

    private final BillOpenHelper mBillOpenHelper;

    private static BillDao sInstance = null;

    private BillDao(Context context) {
        mBillOpenHelper = new BillOpenHelper(context);
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
        SQLiteDatabase database = mBillOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BillOpenHelper.COLUMN_MONEY, billInfo.money);
        values.put(BillOpenHelper.COLUMN_PAYER_ID, billInfo.payerId);
        values.put(BillOpenHelper.COLUMN_BILL_TYPE, billInfo.billType);
        values.put(BillOpenHelper.COLUMN_DATE, billInfo.date);
        values.put(BillOpenHelper.COLUMN_DESC, billInfo.desc);
        database.insert(BillOpenHelper.TABLE_BILL, null, values);
        database.close();
    }

    public void deleteBill(int id) {
        SQLiteDatabase database = mBillOpenHelper.getWritableDatabase();
        database.delete(BillOpenHelper.TABLE_BILL, BillOpenHelper.COLUMN_ID + "=?", new String[]{id + ""});
    }

    public void updateBill(BillInfo billInfo) {
        SQLiteDatabase database = mBillOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BillOpenHelper.COLUMN_MONEY, billInfo.money);
        values.put(BillOpenHelper.COLUMN_PAYER_ID, billInfo.payerId);
        values.put(BillOpenHelper.COLUMN_BILL_TYPE, billInfo.billType);
        values.put(BillOpenHelper.COLUMN_DATE, billInfo.date);
        values.put(BillOpenHelper.COLUMN_DESC, billInfo.desc);
        database.update(BillOpenHelper.TABLE_BILL, values, BillOpenHelper.COLUMN_ID + "=?", new String[]{billInfo.id + ""});
    }

    /**
     * 获取所有账单数据
     */
    public ArrayList<BillInfo> getBills() {
        SQLiteDatabase database = mBillOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(BillOpenHelper.TABLE_BILL, null, null, null, null, null, null);

        ArrayList<BillInfo> billInfos = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BillInfo billInfo = new BillInfo();
                billInfo.id = cursor.getInt(cursor.getColumnIndex(BillOpenHelper.COLUMN_ID));
                billInfo.money = cursor.getFloat(cursor.getColumnIndex(BillOpenHelper.COLUMN_MONEY));
                billInfo.payerId = cursor.getInt(cursor.getColumnIndex(BillOpenHelper.COLUMN_PAYER_ID));
                billInfo.billType = cursor.getInt(cursor.getColumnIndex(BillOpenHelper.COLUMN_BILL_TYPE));
                billInfo.date = cursor.getString(cursor.getColumnIndex(BillOpenHelper.COLUMN_DATE));
                billInfo.desc = cursor.getString(cursor.getColumnIndex(BillOpenHelper.COLUMN_DESC));
                billInfos.add(billInfo);
            }
            cursor.close();
        }
        database.close();
        return billInfos;
    }
}
