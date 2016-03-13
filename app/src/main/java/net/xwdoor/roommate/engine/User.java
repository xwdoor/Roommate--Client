package net.xwdoor.roommate.engine;

import java.io.Serializable;

/**
 * Created by XWdoor on 2016/3/13.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class User implements Serializable {
    private int id;
    private String userName;
    private String realName;
    private String phone;
    private String password;
    private String mail;

    public User(String userName, String password) {
        this.password = password;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}