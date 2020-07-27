package com.twu;

/**
 * Singleton admin
 */
public class Admin extends User{

    private final static String adminName = "admin";
    private final static String adminPassword = "adminPassword";
    private final static Admin admin = new Admin(Admin.adminName, Admin.adminPassword);

    private String passWord;

    public static Admin getInstance() {
        return admin;
    }

    private Admin(String name, String passWord) {
        super(name);
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
