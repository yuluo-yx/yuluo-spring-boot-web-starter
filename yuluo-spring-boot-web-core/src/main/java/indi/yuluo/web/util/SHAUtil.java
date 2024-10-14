package indi.yuluo.web.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class SHAUtil {

    private SHAUtil() {
    }

    /**
     * 计算输入字符串的 SHA-1 签名
     *
     * @param input 要计算签名的字符串
     * @return SHA-1 签名的十六进制字符串
     */
    public static String sha1(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-1计算出错", e);
        }
    }
}
