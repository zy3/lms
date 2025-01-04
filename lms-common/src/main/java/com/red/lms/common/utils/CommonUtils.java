package com.red.lms.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class CommonUtils {

    private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};


    /**
     * 32位唯一码
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 6位随机验证码
     *
     * @return
     */
    public static String randomCode() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }

    /**
     * 编码（应用涉及到的自动生成编码）
     * 格式为时间+四位随机字母
     *
     * @return
     */
    public static String generateCode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        stringBuilder.append(new SimpleDateFormat("HHmmssSSS").format(new Date()));
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[r.nextInt(letters.length)]);
        }
        return stringBuilder.toString();
    }


    /**
     * 生成邀请码
     *
     * @return
     */
    public static String generateInviteCode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        stringBuilder.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[r.nextInt(letters.length)]);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成借据号
     * @param productCode
     * @return
     */
    public static String generateLoanNo(String productCode) {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        stringBuilder.append(productCode);
        stringBuilder.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[r.nextInt(letters.length)]);
        }
        return stringBuilder.toString();
    }

}
