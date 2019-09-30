package top.chendawei.util;

import java.util.UUID;

public class StringUtil {
    public static String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static String numberAndLetter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getRandomString(int len) {
        char[] chars = numberAndLetter.toCharArray();
        String str = "";
        for (int i = 0; i < len; i++) {
            char c = chars[org.apache.commons.lang.math.RandomUtils.nextInt(numberAndLetter.length())];
            str = str + c;
        }
        return str;
    }

    public static boolean checkStr(String str) {
        for (int i = 0; i < str.length(); i++) {
            int c = str.codePointAt(i);
            if ((c < 0) || (c > 65535)) {
                return false;
            }
        }
        return true;
    }

    public static String filterStr(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int c = str.codePointAt(i);
            if ((c >= 0) && (c <= 65535)) {
                sb.append(Character.toChars(c));
            }
        }
        return sb.toString();
    }

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[(x % 62)]);
        }
        return shortBuffer.toString();
    }
}
