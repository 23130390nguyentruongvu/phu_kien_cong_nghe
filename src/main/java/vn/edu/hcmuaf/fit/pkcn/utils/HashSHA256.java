package vn.edu.hcmuaf.fit.pkcn.utils;

import java.security.MessageDigest;

public class HashSHA256 {
    public static String SHA256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] array = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
