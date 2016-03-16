package net.xwdoor.roommate.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 账单
 *
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillInfo implements Serializable {
    public BillInfo() {
    }

    /**
     * 创建账单
     *
     * @param money 金额
     * @param payerId 付款人
     * @param billType 账单类型
     * @param date 日期
     * @param remark 备注
     */
    public BillInfo(float money, int payerId, int billType, Date date, String remark) {
        this.money = money;
        this.payerId = payerId;
        this.billType = billType;
        this.date = date;
        this.remark = remark;
    }

    /** 金额 */
    public float money;
    /** 付款人 */
    public int payerId;
    /** 账单类型 */
    public int billType;
//    private String billDesc;
    /** 日期 */
    public Date date;
    /** 描述 */
    private String remark;

    public String toJson() {
        String json =
                "{" +
                "money=" + money +
                ", payerId=" + payerId +
                ", billType=" + billType +
                ", date=" + date +
                ", remark='" + remark + '\'' +
                '}';
        return json.replaceAll("=",":").replaceAll("'","\"");
    }
}
