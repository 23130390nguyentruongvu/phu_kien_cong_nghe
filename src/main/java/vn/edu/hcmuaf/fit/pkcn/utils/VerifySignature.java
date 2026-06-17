package vn.edu.hcmuaf.fit.pkcn.utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerifySignature {
    //dataNeedHash la loai du lieu khong duoc bam, no la du lieu duoc sinh tu OrderHashDataFormatter
    public static boolean verifySignature(String base64PublicKey, String base64Signature, String dataNeedHash, String algorithm) {
        try {
            if (base64PublicKey == null || base64Signature == null || dataNeedHash == null || algorithm == null) {
                return false;
            }

            String cleanKey = base64PublicKey.trim()
                    .replace("\r", "")
                    .replace("\n", "")
                    .replace(" ", "");

            if (!KeyValidator.isValidPublicKey(cleanKey, algorithm)) {
                return false;
            }

            byte[] keyBytes = Base64.getDecoder().decode(cleanKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PublicKey publicKey = keyFactory.generatePublic(spec);

            String sigAlgorithm;
            if ("RSA".equalsIgnoreCase(algorithm)) {
                sigAlgorithm = "SHA256withRSA";
            } else if ("DSA".equalsIgnoreCase(algorithm)) {
                sigAlgorithm = "SHA256withDSA";
            } else {
                return false;
            }

            Signature signatureInstance = Signature.getInstance(sigAlgorithm);
            signatureInstance.initVerify(publicKey);

            String hexHashData = HashSHA256.SHA256(dataNeedHash);

            int len = hexHashData.length();
            byte[] hashBytesForVerify = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                hashBytesForVerify[i / 2] = (byte) ((Character.digit(hexHashData.charAt(i), 16) << 4)
                        + Character.digit(hexHashData.charAt(i + 1), 16));
            }

            signatureInstance.update(hashBytesForVerify);

            byte[] signatureBytes = Base64.getDecoder().decode(base64Signature.trim());
            return signatureInstance.verify(signatureBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
