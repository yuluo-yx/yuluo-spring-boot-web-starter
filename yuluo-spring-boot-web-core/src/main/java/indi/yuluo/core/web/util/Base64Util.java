package indi.yuluo.core.web.util;

import java.util.Base64;

public final class Base64Util {

    private Base64Util() {
    }

    /**
     * 检车字符串是否为 base64 格式
     * @param base64 base64 目标字符串
     * @return       判断结果 false -> 不是 true -> 是
     */
    public static boolean isBase64(String base64) {

        if (base64 == null || base64.isEmpty()) {
            return false;
        }

        try {
            return Base64.getDecoder().decode(base64) != null;
        } catch (Exception e) {
            return false;
        }
    }

}
