package com.red.lms.common.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptUtils {
    public static void main(String[] args) {
        BasicTextEncryptor stringEncryptor = new BasicTextEncryptor();
        stringEncryptor.setPassword("123456");
        System.out.println(stringEncryptor.decrypt("D43tlPVNlcvGgS48wcPbfw=="));

    }
}
