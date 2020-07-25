package com.twu;

public class Admin extends User{

    static String adminName = "admin";
    static String adminPassword = "adminPassword";
    private String passWord;

    public Admin(String name, String passWord) {

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
