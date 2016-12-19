package com.android.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述: MD5加密工具类
 * Created by mjd on 2016/11/25.
 */

public class MD5Utils {

    /**
     * Per thread MD5 instance.
     */
    private static final ThreadLocal<MessageDigest> perThreadMd5 =
            new ThreadLocal<MessageDigest>() {
                @Override
                protected MessageDigest initialValue() {
                    try {
                        return MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException("MD5 implementation not found", e);
                    }
                }

                ;
            };


    /**
     * Generate MD5 digest.
     */
    public static byte[] getMd5Digest(byte[] input) {
        MessageDigest md5 = perThreadMd5.get();
        md5.reset();
        md5.update(input);
        return md5.digest();
    }


    /**
     *  byte to hex String
     */
    private static String bytesToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Generate MD5 digest.
     */
    public static String getMd5Str(byte[] input){
        byte[] md5Digest = getMd5Digest(input);
        return bytesToHexStr(md5Digest);
    }

}
