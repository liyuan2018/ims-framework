package com.spark.ims.common.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Base64Utils {

    /**
     * 把输入流转化为base64的字符串
     *
     * @param input 输入流
     * @return String
     * @throws java.io.IOException
     */
    public static String inputStreamToBase64(InputStream input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = input.read(b, 0, b.length)) != -1) {
            byteArrayOutputStream.write(b, 0, len);
        }
        byte[] buffer = byteArrayOutputStream.toByteArray();
        String base64 = new BASE64Encoder().encode(buffer);
        base64 = org.apache.commons.lang3.StringUtils.replace(org.apache.commons.lang3.StringUtils.replace(base64, "\r", ""), "\n", "");
        byteArrayOutputStream.close();
        input.close();
        return base64;
    }

    public static String getBase64(String s) throws UnsupportedEncodingException {
        byte[] bytes = Base64.encodeBase64(s.getBytes("utf-8"));
        return new String(bytes, "utf-8");
    }

    public static String getFromBase64(String s) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes("utf-8");
        byte[] convertBytes = Base64.decodeBase64(bytes);
        return new String(convertBytes, "utf-8");
    }

}
