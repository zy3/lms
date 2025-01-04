package com.red.lms.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 根据文件字符串流区分图片的格式
 */
public enum FilePrefixTypeEnum {
    JPG("FFD8FF", "jpg"),
    PNG("89504E47", "png"),
    GIF("47494638", "gif"),
    TIF("49492A00", "tif");

    private String frontChar;
    private String prefix;

    FilePrefixTypeEnum(String frontChar, String prefix) {
        this.frontChar = frontChar;
        this.prefix = prefix;
    }

    public static FilePrefixTypeEnum getEnum(String frontChar) {
        List<FilePrefixTypeEnum> filePrefixTypeEnumList = Arrays.asList(FilePrefixTypeEnum.values());
        if (filePrefixTypeEnumList == null || filePrefixTypeEnumList.isEmpty()) {
            return null;
        }
        for (FilePrefixTypeEnum filePrefixTypeEnum : filePrefixTypeEnumList) {
            if (filePrefixTypeEnum.getFrontChar().equalsIgnoreCase(frontChar)) {
                return filePrefixTypeEnum;
            }
        }
        return null;
    }

    public String getFrontChar() {
        return frontChar;
    }

    public void setFrontChar(String frontChar) {
        this.frontChar = frontChar;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
