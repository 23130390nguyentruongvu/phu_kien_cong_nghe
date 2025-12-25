package vn.edu.hcmuaf.fit.pkcn.utils;

import java.security.MessageDigest;

public class HashMD5 {
    public static String MD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (Exception e){
            return null;
        }
    }
}
