package vn.edu.hcmuaf.fit.pkcn.utils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyValidator {
    public static boolean isValidPublicKey(String publicKeyBase64, String algorithm) {
        if (publicKeyBase64 == null || publicKeyBase64.trim().isEmpty()) {
            return false;
        }

        try {
            String cleanKey = publicKeyBase64.trim()
                    .replace("\r", "")
                    .replace("\n", "")
                    .replace(" ", "");

            byte[] keyBytes = Base64.getDecoder().decode(cleanKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            return publicKey != null;

        } catch (Exception e) {
            return false;
        }
    }
}