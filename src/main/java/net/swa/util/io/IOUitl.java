package net.swa.util.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class IOUitl {


    /* Error */
    public static void copy(String srcFileName, String destFileName, boolean replace)
            throws Exception {
        // Byte code:
        //   0: aload_0
        //   1: ifnull +27 -> 28
        //   4: aload_0
        //   5: invokevirtual 30	java/lang/String:trim	()Ljava/lang/String;
        //   8: invokevirtual 36	java/lang/String:length	()I
        //   11: ifeq +17 -> 28
        //   14: aload_1
        //   15: ifnull +13 -> 28
        //   18: aload_1
        //   19: invokevirtual 30	java/lang/String:trim	()Ljava/lang/String;
        //   22: invokevirtual 36	java/lang/String:length	()I
        //   25: ifne +11 -> 36
        //   28: new 28	java/lang/Exception
        //   31: dup
        //   32: invokespecial 40	java/lang/Exception:<init>	()V
        //   35: athrow
        //   36: new 41	java/io/File
        //   39: dup
        //   40: aload_0
        //   41: invokespecial 43	java/io/File:<init>	(Ljava/lang/String;)V
        //   44: astore_3
        //   45: aload_3
        //   46: invokevirtual 46	java/io/File:exists	()Z
        //   49: ifne +11 -> 60
        //   52: new 28	java/lang/Exception
        //   55: dup
        //   56: invokespecial 40	java/lang/Exception:<init>	()V
        //   59: athrow
        //   60: new 41	java/io/File
        //   63: dup
        //   64: aload_1
        //   65: invokespecial 43	java/io/File:<init>	(Ljava/lang/String;)V
        //   68: astore_3
        //   69: aload_3
        //   70: invokevirtual 46	java/io/File:exists	()Z
        //   73: ifeq +12 -> 85
        //   76: iload_2
        //   77: ifeq +8 -> 85
        //   80: aload_3
        //   81: invokevirtual 50	java/io/File:delete	()Z
        //   84: pop
        //   85: aconst_null
        //   86: astore 4
        //   88: aconst_null
        //   89: astore 5
        //   91: new 53	java/io/FileInputStream
        //   94: dup
        //   95: aload_0
        //   96: invokespecial 55	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
        //   99: astore 4
        //   101: new 56	java/io/FileOutputStream
        //   104: dup
        //   105: aload_1
        //   106: invokespecial 58	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
        //   109: astore 5
        //   111: aload 4
        //   113: aload 5
        //   115: invokestatic 59	org/apache/commons/io/IOUtils:copyLarge	(Ljava/io/InputStream;Ljava/io/OutputStream;)J
        //   118: pop2
        //   119: goto +18 -> 137
        //   122: astore 6
        //   124: aload 4
        //   126: invokestatic 65	net/swa/util/io/IOUitl:closeWithWarnLog	(Ljava/io/InputStream;)V
        //   129: aload 5
        //   131: invokestatic 69	net/swa/util/io/IOUitl:closeWithWarnLog	(Ljava/io/OutputStream;)V
        //   134: aload 6
        //   136: athrow
        //   137: aload 4
        //   139: invokestatic 65	net/swa/util/io/IOUitl:closeWithWarnLog	(Ljava/io/InputStream;)V
        //   142: aload 5
        //   144: invokestatic 69	net/swa/util/io/IOUitl:closeWithWarnLog	(Ljava/io/OutputStream;)V
        //   147: return
        // Line number table:
        //   Java source line #36	-> byte code offset #0
        //   Java source line #37	-> byte code offset #18
        //   Java source line #40	-> byte code offset #28
        //   Java source line #42	-> byte code offset #36
        //   Java source line #43	-> byte code offset #45
        //   Java source line #46	-> byte code offset #52
        //   Java source line #48	-> byte code offset #60
        //   Java source line #49	-> byte code offset #69
        //   Java source line #51	-> byte code offset #80
        //   Java source line #53	-> byte code offset #85
        //   Java source line #54	-> byte code offset #88
        //   Java source line #57	-> byte code offset #91
        //   Java source line #58	-> byte code offset #101
        //   Java source line #59	-> byte code offset #111
        //   Java source line #62	-> byte code offset #122
        //   Java source line #63	-> byte code offset #124
        //   Java source line #64	-> byte code offset #129
        //   Java source line #65	-> byte code offset #134
        //   Java source line #63	-> byte code offset #137
        //   Java source line #64	-> byte code offset #142
        //   Java source line #66	-> byte code offset #147
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	148	0	srcFileName	String
        //   0	148	1	destFileName	String
        //   0	148	2	replace	boolean
        //   44	37	3	file	java.io.File
        //   86	52	4	input	java.io.FileInputStream
        //   89	54	5	output	java.io.FileOutputStream
        //   122	13	6	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   91	122	122	finally
    }

    public static void closeWithWarnLog(InputStream input) {
        if (input != null) {
            try {
                input.close();
                input = null;
            } catch (IOException ex) {

                log.warn("关闭文件输入流异常：", ex);

            }
        }
    }

    public static void closeWithWarnLog(OutputStream output) {
        if (output != null) {
            try {
                output.close();
                output = null;
            } catch (IOException ex) {

                log.warn("关闭文件输出流异常：", ex);

            }
        }
    }
}
