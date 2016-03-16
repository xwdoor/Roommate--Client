package net.xwdoor.roommate.entity;

import java.io.Serializable;

/**
 * 应用信息类
 *
 * Created by XWdoor on 2016/3/12.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class AppInfo implements Serializable {
    /**
     * 版本号
     */
    public int versionCode;
    /**
     * 版本名
     */
    public String versionName;
    /**
     * 描述
     */
    public String description;
    /**
     * 下载地址
     */
    public String downloadUrl;

    public AppInfo(int versionCode, String versionName) {
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public AppInfo() {
        this(0,"0");
    }

    public String toJson() {
        String json =
                "{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", description='" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
        return json.replaceAll("=",":").replaceAll("'","\"");
    }
}
