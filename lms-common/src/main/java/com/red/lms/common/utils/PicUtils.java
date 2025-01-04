package com.red.lms.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;

public class PicUtils {

    /**
     * 校验是否是图片
     *
     * @param imageBytes
     * @return
     */
    public static boolean isImage(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            img = null;
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param imageBytes
     * @return
     */
    public static String getFilePrefix(byte[] imageBytes) {
        //校验图片格式
        String hexStr = bytesToHexString(imageBytes);
        FilePrefixTypeEnum filePrefixTypeEnum = FilePrefixTypeEnum.getEnum(hexStr.substring(0, 6));
        if (filePrefixTypeEnum != null) {
            return filePrefixTypeEnum.getPrefix();
        }
        filePrefixTypeEnum = FilePrefixTypeEnum.getEnum(hexStr.substring(0, 8));
        if (filePrefixTypeEnum != null) {
            return filePrefixTypeEnum.getPrefix();
        }
        return null;
    }

    /**
     * 获取文件头字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
