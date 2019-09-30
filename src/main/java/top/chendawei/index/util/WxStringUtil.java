package top.chendawei.index.util;

import top.chendawei.util.DateUtils;
import top.chendawei.util.EncryptTool;

import java.util.Arrays;

public class WxStringUtil {
    public static String getSuffix(String wxid2, String gzhId2)
            throws Exception {
        long ts = DateUtils.getCurrDate().getTime();
        String[] array = {wxid2, Long.toString(ts), gzhId2};
        Arrays.sort(array);
        String str = "";
        String[] arrayOfString1;
        int j = (arrayOfString1 = array).length;
        for (int i = 0; i < j; i++) {
            String s = arrayOfString1[i];

            str = str + s;
        }
        EncryptTool tool = new EncryptTool();
        String token = tool.encrypt(str);
        String r = "?wxid=" + wxid2 + "&gzhId=" + gzhId2 + "&ts=" + ts + "&token=" + token;
        return r;
    }
}
