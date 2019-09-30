package top.chendawei.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
    private static final String regxpForHtml = "<([^>]*)>";

    public static String parseHtmlFile(String file, Object bean)
            throws Exception {
        String path = HtmlUtil.class.getClassLoader().getResource(file)
                .getFile();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(path)), "utf-8"));
        StringBuilder html = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (bean != null) {
                line = parseLine(line, bean);
            }
            html.append(line);
        }
        reader.close();

        return html.toString();
    }

    private static String parseLine(String line, Object bean)
            throws Exception {
        StringBuilder newLine = new StringBuilder();
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();
        List<String> attributes = new ArrayList();
        Field[] arrayOfField1;
        int j = (arrayOfField1 = fields).length;
        for (int i = 0; i < j; i++) {
            Field field = arrayOfField1[i];
            attributes.add(field.getName());
        }
        StringTokenizer token = new StringTokenizer(line, "${}");
        while (token.hasMoreTokens()) {
            String part = token.nextToken();
            if (part.indexOf(".") < 0) {
                if (attributes.contains(part)) {
                    try {
                        Method getMethod = clz.getMethod("get" + StringUtils.capitalize(part));
                        Object obj = getMethod.invoke(bean);
                        part = obj == null ? "" : obj.toString();
                    } catch (Exception localException2) {
                    }
                }
            } else {
                String[] parts = part.split("\\.");
                String attr1 = parts[0];
                String attr2 = parts[1];
                try {
                    Method getMethod = clz.getMethod("get" + StringUtils.capitalize(attr1));
                    Object obj = getMethod.invoke(bean);
                    if (obj != null) {
                        getMethod = obj.getClass().getMethod("get" + StringUtils.capitalize(attr2));
                        Object val = getMethod.invoke(obj);
                        part = val == null ? "" : val.toString();
                    } else {
                        part = "";
                    }
                } catch (Exception localException1) {
                }
            }
            newLine.append(part);
        }
        return newLine.toString();
    }

    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile("<([^>]*)>");

        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();

        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");

            result1 = matcher.find();
        }
        matcher.appendTail(sb);

        String s = sb.toString();

        s = s.replace("\n", "");
        s = s.replace("\t", "");

        return s;
    }
}
