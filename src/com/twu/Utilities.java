package com.twu;

import java.util.Scanner;

public class Utilities {
    public static int strToNumb(String str) {
        if (str.matches("^[0-9]+$")) {
            return Integer.parseInt(str,10);
        }
        throw new RuntimeException("！！你的输入有误！");
    }

    public static String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static void print(String output) {
        System.out.println(output);
    }

    public static void printDividedLine() {
        System.out.println("---------------------------------------------------");
    }

    public static void sendError(String error) {
        throw new RuntimeException(error);
    }
}
