package com.spark.ims.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 描述:DES加密解密工具类
 *
 *Created by liyuan on 2018/4/27.
 */
public class DESUtils {

    Key key;

    public DESUtils(String str) {
        setKey(str);// 生成密匙
    }

    public DESUtils() {
       //setKey("YWRtaW4=");
    }

    /**
     * 根据参数生成KEY
     */
    public void setKey(String strKey) {
        try {
            strKey = MD5Utils.encrypt(strKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key  = keyFactory.generateSecret(new DESKeySpec(strKey.getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error setKey DESUtils class. Cause: " + e);
        }
    }


    /**
     * 加密String明文输入,String密文输出
     */
    public String encrypt(String strMing,String key) {
        setKey(key);
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = this.getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error encrypt DESUtils class. Cause: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String decrypt(String strMi,String key) {
        setKey(key);
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error decrypt DESUtils class. Cause: " + e);
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error getEncCode DESUtils class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error getDesCode DESUtils class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }


//
//    public static void main(String args[]) throws Exception {
//
//        String aa = MD5Utils.encrypt("lyy");
//        System.out.println(aa);
//        DESUtils des = new DESUtils();
//        //des.setKey(aa);
//        String str1 = "1";
//        // DES加密
//        String str2 = des.encrypt(str1,"lyy");
//        DESUtils des1 = new DESUtils();
//        String deStr = des1.decrypt(str2,"lyy");
//        System.out.println("密文:" + str2);
//        // DES解密
//        System.out.println("明文:" + deStr);
//
//    }
}
