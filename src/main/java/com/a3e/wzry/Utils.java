package com.a3e.wzry;

import java.io.File;

public class Utils {
    public static File mkdir(String dir) {
        File file = new File(dir);
        if (!file.isDirectory()) {
            if (!file.mkdir()) {
                System.out.println("[ERROR] failed to mkdir");
            }
        }
        return file;
    }

    public static File mkdir(File file) {
        if (!file.isDirectory()) {
            if (!file.mkdir()) {
                System.out.println("[ERROR] failed to mkdir");
            }
        }
        return file;
    }

    public static File join(File s1, String s2) {
        File ret = new File(s1, s2);
        return ret;
    }
}
