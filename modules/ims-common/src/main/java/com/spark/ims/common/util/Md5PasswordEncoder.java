package com.spark.ims.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：
 *
 * @authhor liyuan
 * @data 2018/5/1 22:23
 */
public class Md5PasswordEncoder {

    private final static String[] hexDigits = {"0","1","2","3","4","5",
            "6","7","8","9","a","b","c","d","e","f"};

    private Object salt;
    private String algorithm = "MD5";

    public Md5PasswordEncoder(Object salt){
        this.salt = salt;
    }

    public Md5PasswordEncoder(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    public  String encode(String rawPass) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //加密后的字符串
            return byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("采用了不支持的算法, 算法名称:" + algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("不支持的编码导致无法获得加密后的密文。");
        }
    }

    public boolean isPasswordValid(String encPass, String rawPass) {
        String pass1 =""+ encPass;
        String pass2 = encode(rawPass);

        return pass1.equals(pass2);
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password ="";
        }

        if ((salt == null) ||"".equals(salt)) {
            return password;
        } else {
            return password +"{"+ salt.toString() +"}";
        }
    }

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 使用默认算法对密码进行加盐加密
     * @param password      密码
     * @param salt          盐
     * @return              加盐加密后的密文
     */
    public static String encryptPassword(String password, String salt) {
        String defaultMd5Pwd = new Md5PasswordEncoder(null).encode(password);
        return new Md5PasswordEncoder(salt).encode(defaultMd5Pwd);
    }
}
