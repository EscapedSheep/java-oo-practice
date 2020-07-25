package com.twu;

public class Main {

    public static void main(String[] args) {
        UserOperatingSystem system = UserOperatingSystem.getInstance();
        system.login();
        system.exit();
    }

}
