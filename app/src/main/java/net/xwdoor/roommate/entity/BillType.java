package net.xwdoor.roommate.entity;

/**
 * Created by XWdoor on 2016/3/20.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class BillType {
    public int typeId;
    public String typeName;

    public BillType() {
    }

    public BillType(int type, String desc) {
        this.typeId = type;
        this.typeName = desc;
    }
}
