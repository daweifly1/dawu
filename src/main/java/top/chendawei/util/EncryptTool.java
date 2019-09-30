package top.chendawei.util;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class EncryptTool {
    private static String strDefaultKey = "xiaowei";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public EncryptTool()
            throws Exception {
        this(strDefaultKey);
    }

    public EncryptTool(String strKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Security.addProvider(new SunJCE());
        Key key = getKey(strKey.getBytes());

        this.encryptCipher = Cipher.getInstance("DES");
        this.encryptCipher.init(1, key);

        this.decryptCipher = Cipher.getInstance("DES");
        this.decryptCipher.init(2, key);
    }

    public static String byteArr2HexStr(byte[] arrB)
            throws Exception {
        int iLen = arrB.length;

        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp += 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn)
            throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[(i / 2)] = ((byte) Integer.parseInt(strTmp, 16));
        }
        return arrOut;
    }

    public static final String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            mdInst.update(btInput);

            byte[] md = mdInst.digest();

            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] encrypt(byte[] arrB)
            throws Exception {
        return this.encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn)
            throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrB)
            throws Exception {
        return this.decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) {
        try {
            return new String(decrypt(hexStr2ByteArr(strIn)));
        } catch (Exception e) {
        }
        return "";
    }

    private Key getKey(byte[] arrBTmp) {
        byte[] arrB = new byte[8];
        for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new SecretKeySpec(arrB, "DES");

        return key;
    }
}
