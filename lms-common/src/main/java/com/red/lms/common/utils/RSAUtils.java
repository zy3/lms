package com.red.lms.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 加密字符串
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String publicKey) throws Exception {
        return encrypt(data, getPublicKey(publicKey));
    }


    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * 解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String privateKey) throws Exception {
        return decrypt(data, getPrivateKey(privateKey));
    }

    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

//    public static void main(String[] args) {
//        try {
//            // 生成密钥对
//            KeyPair keyPair = getKeyPair();
//            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
////             String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMH+PakmbuD7vKrpQ/hCMHiWGsjzreNI0X0qS+7yETVO7vuadkAQOMdZDs3RdxhomerIYlWZrTGB0DW3mPN68qx0MebdgfZ9zag657byLLDlVV9JYk494JJW+0RXMr+/d4nOG/5n61veO41ZIfLxHowfxxmp0B16iDvT8/39QrzBAgMBAAECgYASzWGMk30LsVqH9jPwS/oGtgXClFu2O8iSKRdTAYNJd20SVdZAP1JBgBA/Qp4Yb4r7hpCBW3O96KcVjcX1U2VRxnuwTpJRim96a0jdscB/VCQ0XzppVeB8uBs4KUDfBKbK4NRqM+4TWj31OIWvhQ7im8VwqRDlpQ+izGQeZTbwAQJBAOPPHKYO37SKF9CSBf84REawP0lt0O1ZY8oQ01HDu2iRRxDYNdyrP3VR7oUKXnW7SKRjtOkIxrN2FErDCxSd1LECQQDZ/9s2suxbmcbK7YK83IWU1H/xLD7A+oC+P4yRpNmOxBpzyCzNVb3tsi+nzb6bsZ/or6DIdDUBicb4eBdJzK0RAkEAvHMqWdxYBntt8+W3/EbJhEhsMgwcFxwpRpDgnvTx5TC4vyB8fg1Ts3ORgpDvTykckJDCqTvPU8nMQ4RReD6QIQJBAMZwwhK7sq37gFsu8yiKMMr22PQny4QNQ+5qmMHnZucvmci1ehr8JuTdo8XDYvQFL8Q3Q0vVhH6+9TEu2DwkpZECQQCtqJiPtjQhr2aJ/8+8ZExqYlguaHYRASFMprySPNbujbGj4oLf9kWDxIrV7P4fNiF4F4VOseMku1wf5e/RujCk";
//              String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
////            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDB/j2pJm7g+7yq6UP4QjB4lhrI863jSNF9Kkvu8hE1Tu77mnZAEDjHWQ7N0XcYaJnqyGJVma0xgdA1t5jzevKsdDHm3YH2fc2oOue28iyw5VVfSWJOPeCSVvtEVzK/v3eJzhv+Z+tb3juNWSHy8R6MH8cZqdAdeog70/P9/UK8wQIDAQAB";
//            System.out.println("私钥:" + privateKey);
//            System.out.println("公钥:" + publicKey);
//            // RSA加密
//            String data = "Xx123456789";
//            String encryptData = encrypt(data, getPublicKey(publicKey));
//            System.out.println("加密后内容:" + encryptData);
//            // RSA解密
//            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
//            System.out.println("解密后内容:" + decryptData);
//
//            // RSA签名
//            String sign = sign(data, getPrivateKey(privateKey));
//            // RSA验签
//            boolean result = verify(data, getPublicKey(publicKey), sign);
//            System.out.print("验签结果:" + result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.print("加解密异常");
//        }
//    }
}
