package io.github.tool.core;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CodeHelper {
    public static final MessageDigest MD5_MESSAGE_DIGEST = get("MD5");

    public static byte[] md5(byte[] data) {
        return MD5_MESSAGE_DIGEST.digest(data);
    }

    public static String hex(byte[] data) {
        StringBuilder hex = new StringBuilder(data.length * 2);
        for (byte b : data) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String md5TextToHex(String text) {
        return md5ToHex(text.getBytes(StandardCharsets.UTF_8));
    }
    public static String md5ToHex(byte[] data) {
        return hex(md5(data));
    }
    private static final char[] BASE64C = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/'};
    private static final byte[] BASE64B = {62,-1,-1,-1,63,52,53,54,55,56,57,58,59,60,61,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,-1,-1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51}; //'A'=65,'a'=97,'0'=48,'+'=43,'/'=47 统一减43
    public static String base64Encode(byte[] params) { //将byte数组(或字符串)转换成base64
        if (params == null) return null;
        byte[] result = new byte[(params.length+2)/3*4]; //每3个字节一组,重组为4个字节一组(加2是向上取整)
        int index = 0;
        for (int i=0;i<(params.length/3*3);i+=3) { //除3会自动取整
            int bits = (params[i] & 0xff) << 16 | (params[i+1] & 0xff) << 8 | (params[i+2] & 0xff); //&0xff表示由byte转int,<<表示向左移多少位,高位会被丢弃,低位会补0
            result[index++] = (byte)BASE64C[(bits >>> 18) & 0x3f]; //&0x3f表示保留6位数(类似对64求余),>>表示向右移多少位,低位会被丢弃,高位补0(无符号)或1(有符号),>>>表示向右移多少位,高位补0(无符号)
            result[index++] = (byte)BASE64C[(bits >>> 12) & 0x3f];
            result[index++] = (byte)BASE64C[(bits >>> 6) & 0x3f];
            result[index++] = (byte)BASE64C[bits & 0x3f];
        }
        if (params.length%3 == 1) { //多余1个加两个=号
            int bits = (params[params.length-1] & 0xff) << 4;
            result[index++] = (byte)BASE64C[(bits >>> 6) & 0x3f];
            result[index++] = (byte)BASE64C[bits & 0x3f];
            result[index++] = '=';
            result[index] = '=';
        } else if (params.length%3 == 2) { //多余2个加一个=号
            int bits = (params[params.length-2] & 0xff) << 10 | (params[params.length-1] & 0xff) << 2;
            result[index++] = (byte)BASE64C[(bits >>> 12) & 0x3f];
            result[index++] = (byte)BASE64C[(bits >>> 6) & 0x3f];
            result[index++] = (byte)BASE64C[bits & 0x3f];
            result[index] = '=';
        }
        return new String(result);
    }


    public static String randomNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(Integer.MAX_VALUE));
    }

    public static  byte[] hmacSHA256(String message, String key) {
        byte[] data = null;
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            data = sha256_HMAC.doFinal(message.getBytes());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }

    public static String hmacSHA256ToBase64(String message, String key) {
        return base64Encode(hmacSHA256(message, key));
    }


    public static MessageDigest get(String algorithm) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return digest;
    }


    public static byte[] toByteArray(File file) {
        byte[] data = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            data = new byte[(int) file.length()];
            int count = inputStream.read(data);
            if (count != file.length()) {
                data = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}
