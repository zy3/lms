package com.red.lms.common.utils;

import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Slf4j
public class EncryptUtils {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    //盐的长度
    public static final int SALT_SIZE = 16;

    //生成密文的长度
    public static final int HASH_SIZE = 32;

    // 迭代次数
    public static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 全局数组
     **/
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * 对输入的密码进行验证
     */
    public static boolean verify(String password, String salt, String key)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用相同的盐值对用户输入的密码进行加密
        String result = getPBKDF2(password, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return result.equals(key);
    }

    /**
     * 根据password和salt生成密文
     */
    public static String getPBKDF2(String password, String salt) {
        try {
            //将16进制字符串形式的salt转换成byte数组
            byte[] bytes = DatatypeConverter.parseHexBinary(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
            //将byte数组转换为16进制的字符串
            return DatatypeConverter.printHexBinary(hash);
        } catch (Exception e) {
            log.error("PBKDF2 error", e);
            throw new CommonException(CodeEnum.SYSTEM_ERROR, "加密失败");
        }
    }

    /**
     * 返回形式为数字跟字符串
     *
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * MD5加密
     *
     * @param str 待加密的字符串
     * @return
     */
    public static String getMD5(String str) {
        String result = null;
        try {
            result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            log.error("MD5 error", ex);
            throw new CommonException(CodeEnum.SYSTEM_ERROR, "加密失败");
        }
        return result;
    }

    /**
     * MD5加密
     *
     * @param str       待加密的字符串
     * @param lowerCase 大小写
     * @return
     */
    public static String getMD5(String str, boolean lowerCase) {
        String result = null;
        try {
            result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
            if (lowerCase) {
                result = result.toLowerCase();
            }
        } catch (NoSuchAlgorithmException ex) {
            log.error("MD5 error", ex);
            throw new CommonException(CodeEnum.SYSTEM_ERROR, "加密失败");
        }
        return result;
    }

    /**
     * MD5后取前面两个字符、最后两个字符作为盐值
     *
     * @param password
     * @return
     */
    private static String getSalt(String password) {
        String md5 = getMD5(password);
        StringBuilder salt = new StringBuilder();
        if (md5.length() >= 2) {
            salt.append(md5.substring(0, 2));
            salt.append(md5.substring(md5.length() - 2, md5.length()));
        } else {
            salt.append(md5);
        }
        return salt.toString();
    }

    public static String encryptPBKDF2(String password) {
        //盐值
        String salt = EncryptUtils.getSalt(password);
        //加密
        String encryptPassword = EncryptUtils.getPBKDF2(password, salt);
        if (StringUtils.isBlank(encryptPassword)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "加密失败");
        }
        return encryptPassword;
    }

}
