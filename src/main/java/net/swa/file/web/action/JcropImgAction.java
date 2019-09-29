package net.swa.file.web.action;

import lombok.extern.slf4j.Slf4j;
import net.swa.file.beans.entity.Attachment;
import net.swa.system.service.ICommonService;
import net.swa.system.web.action.AbstractBaseAction;
import net.swa.util.ConfigUtil;
import net.swa.util.FileUtil;
import net.swa.util.JsonResult;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping({"/jcrop"})
public class JcropImgAction
        extends AbstractBaseAction {
    private static final long serialVersionUID = -3566256767528230895L;
    private ICommonService commonService;
    private String uploadPath;
    private String gabagePath;
    private String httpPath;

    public JcropImgAction() {
        this.uploadPath = ConfigUtil.getProperty("uploadPath");
        this.gabagePath = ConfigUtil.getProperty("gabagePath");
        this.httpPath = ConfigUtil.getProperty("httpPath");
        this.log.debug("gabagePath is no use :" + this.gabagePath);
    }

    @RequestMapping({"/uploadImg"})
    public void uploadImg(String folder, Integer limitwidth, Integer limitheight, String fileFileName, HttpServletRequest request, HttpServletResponse rsp)
            throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");

        fileFileName = file.getOriginalFilename();
        JsonResult<Object> json = new JsonResult();
        String path = this.uploadPath;
        String imgUrl = this.httpPath + folder + "/" + "resource/";
        File f = new File(path + File.separator + folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        File target = new File(path + File.separator + folder + File.separator + "resource");
        if (!target.exists()) {
            target.mkdirs();
        }
        String sourceName = fileFileName;
        String ext = FileUtil.getExtension(fileFileName);
        fileFileName = UUID.randomUUID().toString() + "." + ext;
        if ((file.getOriginalFilename() != null) && (!"".equals(file.getOriginalFilename()))) {
            List<String> fileTypes = new ArrayList();
            fileTypes.add("jpg");
            fileTypes.add("jpeg");
            fileTypes.add("bmp");
            fileTypes.add("gif");
            fileTypes.add("png");
            if (fileTypes.contains(ext)) {
                File desc = new File(target.getAbsoluteFile() + File.separator + fileFileName);
                try {
                    file.transferTo(desc);
                    if (validateFile(desc, limitwidth, limitheight)) {
                        json.setSuccess(true);

                        json.setAttribute("fileName", imgUrl + fileFileName);
                        json.setAttribute("fileSourceName", path + folder + "/resource/" + fileFileName);
                        json.setAttribute("sourceName", sourceName);
                    } else {
                        desc.delete();
                        this.log.debug("上传的源图片长宽不符合要求");
                        json.setSuccess(false);
                        json.setMessage("上传的源图片长宽不符合要求");
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    json.setSuccess(false);
                    json.setMessage("出现异常" + e);
                } catch (IOException e) {
                    e.printStackTrace();
                    json.setSuccess(false);
                    json.setMessage("出现异常" + e);
                }
            } else {
                this.log.debug("上传的源图片文件格式不符合要求");
                json.setSuccess(false);
                json.setMessage("附件文件格式不符合要求");
            }
        } else {
            this.log.debug("上传的源图片文件error");
            json.setSuccess(false);
            json.setMessage("附件文件error");
        }
        outJson(json, rsp);
    }

    /* Error */
    @RequestMapping({"/cutImg"})
    public void cutImg(Double x, Double y, Double w, Double h, Double sw, Double sh, Integer limitwidth, Integer limitheight, String imageUrl, HttpServletResponse rsp)
            throws Exception {
        // Byte code:
        //   0: aload_0
        //   1: getfield 29	net/swa/file/web/action/JcropImgAction:log	Lorg/apache/log4j/Logger;
        //   4: ldc_w 261
        //   7: invokevirtual 61	org/apache/log4j/Logger:debug	(Ljava/lang/Object;)V
        //   10: new 91	net/swa/util/JsonResult
        //   13: dup
        //   14: invokespecial 93	net/swa/util/JsonResult:<init>	()V
        //   17: astore 11
        //   19: aload 9
        //   21: invokestatic 263	org/jsoup/helper/StringUtil:isBlank	(Ljava/lang/String;)Z
        //   24: ifne +399 -> 423
        //   27: aload 9
        //   29: invokestatic 119	net/swa/util/FileUtil:getExtension	(Ljava/lang/String;)Ljava/lang/String;
        //   32: astore 12
        //   34: aload 9
        //   36: iconst_0
        //   37: aload 9
        //   39: ldc 117
        //   41: invokevirtual 269	java/lang/String:indexOf	(Ljava/lang/String;)I
        //   44: invokevirtual 273	java/lang/String:substring	(II)Ljava/lang/String;
        //   47: astore 13
        //   49: aload 9
        //   51: aload 9
        //   53: ldc 117
        //   55: invokevirtual 269	java/lang/String:indexOf	(Ljava/lang/String;)I
        //   58: bipush 9
        //   60: iadd
        //   61: invokevirtual 277	java/lang/String:substring	(I)Ljava/lang/String;
        //   64: astore 14
        //   66: aload 9
        //   68: ldc 102
        //   70: ldc -123
        //   72: invokevirtual 280	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //   75: aload_0
        //   76: getfield 38	net/swa/file/web/action/JcropImgAction:uploadPath	Ljava/lang/String;
        //   79: aload_0
        //   80: getfield 44	net/swa/file/web/action/JcropImgAction:httpPath	Ljava/lang/String;
        //   83: invokevirtual 280	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
        //   86: astore 15
        //   88: new 104	java/io/File
        //   91: dup
        //   92: aload 9
        //   94: invokevirtual 284	java/lang/String:trim	()Ljava/lang/String;
        //   97: invokespecial 109	java/io/File:<init>	(Ljava/lang/String;)V
        //   100: astore 16
        //   102: aconst_null
        //   103: astore 17
        //   105: aconst_null
        //   106: astore 18
        //   108: new 104	java/io/File
        //   111: dup
        //   112: new 46	java/lang/StringBuilder
        //   115: dup
        //   116: aload 13
        //   118: invokestatic 94	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
        //   121: invokespecial 50	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   124: aload 14
        //   126: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   129: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   132: invokespecial 109	java/io/File:<init>	(Ljava/lang/String;)V
        //   135: astore 19
        //   137: aload 19
        //   139: invokevirtual 110	java/io/File:exists	()Z
        //   142: ifne +9 -> 151
        //   145: aload 19
        //   147: invokevirtual 192	java/io/File:delete	()Z
        //   150: pop
        //   151: new 287	java/io/FileInputStream
        //   154: dup
        //   155: aload 16
        //   157: invokespecial 289	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   160: astore 17
        //   162: aload 16
        //   164: invokestatic 291	javax/imageio/ImageIO:read	(Ljava/io/File;)Ljava/awt/image/BufferedImage;
        //   167: astore 20
        //   169: aload_0
        //   170: aload 12
        //   172: invokespecial 297	net/swa/file/web/action/JcropImgAction:getImgType	(Ljava/lang/String;)Ljava/lang/String;
        //   175: astore 21
        //   177: aload 21
        //   179: invokestatic 300	javax/imageio/ImageIO:getImageReadersByFormatName	(Ljava/lang/String;)Ljava/util/Iterator;
        //   182: astore 22
        //   184: aload 22
        //   186: invokeinterface 304 1 0
        //   191: checkcast 310	javax/imageio/ImageReader
        //   194: astore 23
        //   196: aload 17
        //   198: invokestatic 312	javax/imageio/ImageIO:createImageInputStream	(Ljava/lang/Object;)Ljavax/imageio/stream/ImageInputStream;
        //   201: astore 18
        //   203: aload 23
        //   205: aload 18
        //   207: iconst_1
        //   208: invokevirtual 316	javax/imageio/ImageReader:setInput	(Ljava/lang/Object;Z)V
        //   211: aload 23
        //   213: invokevirtual 320	javax/imageio/ImageReader:getDefaultReadParam	()Ljavax/imageio/ImageReadParam;
        //   216: astore 24
        //   218: aload 20
        //   220: aconst_null
        //   221: invokevirtual 324	java/awt/Image:getWidth	(Ljava/awt/image/ImageObserver;)I
        //   224: istore 25
        //   226: aload 20
        //   228: aconst_null
        //   229: invokevirtual 330	java/awt/Image:getHeight	(Ljava/awt/image/ImageObserver;)I
        //   232: istore 26
        //   234: iload 25
        //   236: i2d
        //   237: aload_3
        //   238: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   241: aload 5
        //   243: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   246: ddiv
        //   247: dmul
        //   248: d2i
        //   249: istore 27
        //   251: iload 26
        //   253: i2d
        //   254: aload 4
        //   256: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   259: aload 6
        //   261: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   264: ddiv
        //   265: dmul
        //   266: d2i
        //   267: istore 28
        //   269: aload_1
        //   270: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   273: iload 25
        //   275: i2d
        //   276: dmul
        //   277: aload 5
        //   279: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   282: ddiv
        //   283: invokestatic 339	java/lang/Double:valueOf	(D)Ljava/lang/Double;
        //   286: astore_1
        //   287: aload_2
        //   288: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   291: iload 26
        //   293: i2d
        //   294: dmul
        //   295: aload 6
        //   297: invokevirtual 333	java/lang/Double:doubleValue	()D
        //   300: ddiv
        //   301: invokestatic 339	java/lang/Double:valueOf	(D)Ljava/lang/Double;
        //   304: astore_2
        //   305: new 342	java/awt/Rectangle
        //   308: dup
        //   309: aload_1
        //   310: invokevirtual 344	java/lang/Double:intValue	()I
        //   313: aload_2
        //   314: invokevirtual 344	java/lang/Double:intValue	()I
        //   317: iload 27
        //   319: iload 28
        //   321: invokespecial 348	java/awt/Rectangle:<init>	(IIII)V
        //   324: astore 29
        //   326: aload 24
        //   328: aload 29
        //   330: invokevirtual 351	javax/imageio/ImageReadParam:setSourceRegion	(Ljava/awt/Rectangle;)V
        //   333: aload 23
        //   335: iconst_0
        //   336: aload 24
        //   338: invokevirtual 357	javax/imageio/ImageReader:read	(ILjavax/imageio/ImageReadParam;)Ljava/awt/image/BufferedImage;
        //   341: astore 30
        //   343: aload 30
        //   345: aload 21
        //   347: aload 19
        //   349: invokestatic 360	javax/imageio/ImageIO:write	(Ljava/awt/image/RenderedImage;Ljava/lang/String;Ljava/io/File;)Z
        //   352: pop
        //   353: aload 11
        //   355: iconst_1
        //   356: invokevirtual 176	net/swa/util/JsonResult:setSuccess	(Z)V
        //   359: aload 11
        //   361: ldc -76
        //   363: aload 15
        //   365: invokevirtual 182	net/swa/util/JsonResult:setAttribute	(Ljava/lang/String;Ljava/lang/Object;)V
        //   368: goto +30 -> 398
        //   371: astore 31
        //   373: aload 17
        //   375: ifnull +8 -> 383
        //   378: aload 17
        //   380: invokevirtual 364	java/io/FileInputStream:close	()V
        //   383: aload 18
        //   385: ifnull +10 -> 395
        //   388: aload 18
        //   390: invokeinterface 367 1 0
        //   395: aload 31
        //   397: athrow
        //   398: aload 17
        //   400: ifnull +8 -> 408
        //   403: aload 17
        //   405: invokevirtual 364	java/io/FileInputStream:close	()V
        //   408: aload 18
        //   410: ifnull +37 -> 447
        //   413: aload 18
        //   415: invokeinterface 367 1 0
        //   420: goto +27 -> 447
        //   423: aload_0
        //   424: getfield 29	net/swa/file/web/action/JcropImgAction:log	Lorg/apache/log4j/Logger;
        //   427: ldc_w 370
        //   430: invokevirtual 61	org/apache/log4j/Logger:debug	(Ljava/lang/Object;)V
        //   433: aload 11
        //   435: iconst_0
        //   436: invokevirtual 176	net/swa/util/JsonResult:setSuccess	(Z)V
        //   439: aload 11
        //   441: ldc_w 372
        //   444: invokevirtual 197	net/swa/util/JsonResult:setMessage	(Ljava/lang/String;)V
        //   447: aload_0
        //   448: aload 11
        //   450: aload 10
        //   452: invokevirtual 218	net/swa/file/web/action/JcropImgAction:outJson	(Ljava/lang/Object;Ljavax/servlet/http/HttpServletResponse;)V
        //   455: return
        // Line number table:
        //   Java source line #173	-> byte code offset #0
        //   Java source line #174	-> byte code offset #10
        //   Java source line #175	-> byte code offset #19
        //   Java source line #177	-> byte code offset #27
        //   Java source line #178	-> byte code offset #34
        //   Java source line #179	-> byte code offset #49
        //   Java source line #180	-> byte code offset #66
        //   Java source line #181	-> byte code offset #88
        //   Java source line #182	-> byte code offset #102
        //   Java source line #183	-> byte code offset #105
        //   Java source line #184	-> byte code offset #108
        //   Java source line #185	-> byte code offset #137
        //   Java source line #187	-> byte code offset #145
        //   Java source line #192	-> byte code offset #151
        //   Java source line #193	-> byte code offset #162
        //   Java source line #199	-> byte code offset #169
        //   Java source line #201	-> byte code offset #177
        //   Java source line #202	-> byte code offset #184
        //   Java source line #204	-> byte code offset #196
        //   Java source line #210	-> byte code offset #203
        //   Java source line #217	-> byte code offset #211
        //   Java source line #218	-> byte code offset #218
        //   Java source line #219	-> byte code offset #226
        //   Java source line #221	-> byte code offset #234
        //   Java source line #222	-> byte code offset #251
        //   Java source line #224	-> byte code offset #269
        //   Java source line #225	-> byte code offset #287
        //   Java source line #230	-> byte code offset #305
        //   Java source line #233	-> byte code offset #326
        //   Java source line #239	-> byte code offset #333
        //   Java source line #242	-> byte code offset #343
        //   Java source line #244	-> byte code offset #353
        //   Java source line #252	-> byte code offset #359
        //   Java source line #255	-> byte code offset #371
        //   Java source line #256	-> byte code offset #373
        //   Java source line #257	-> byte code offset #378
        //   Java source line #258	-> byte code offset #383
        //   Java source line #259	-> byte code offset #388
        //   Java source line #260	-> byte code offset #395
        //   Java source line #256	-> byte code offset #398
        //   Java source line #257	-> byte code offset #403
        //   Java source line #258	-> byte code offset #408
        //   Java source line #259	-> byte code offset #413
        //   Java source line #264	-> byte code offset #423
        //   Java source line #265	-> byte code offset #433
        //   Java source line #266	-> byte code offset #439
        //   Java source line #268	-> byte code offset #447
        //   Java source line #269	-> byte code offset #455
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	456	0	this	JcropImgAction
        //   0	456	1	x	Double
        //   0	456	2	y	Double
        //   0	456	3	w	Double
        //   0	456	4	h	Double
        //   0	456	5	sw	Double
        //   0	456	6	sh	Double
        //   0	456	7	limitwidth	Integer
        //   0	456	8	limitheight	Integer
        //   0	456	9	imageUrl	String
        //   0	456	10	rsp	HttpServletResponse
        //   17	432	11	json	JsonResult<Object>
        //   32	139	12	ext	String
        //   47	70	13	dir	String
        //   64	61	14	fname	String
        //   86	278	15	netPath	String
        //   100	63	16	f	File
        //   103	301	17	is	java.io.FileInputStream
        //   106	308	18	iis	javax.imageio.stream.ImageInputStream
        //   135	213	19	mindesc	File
        //   167	60	20	img	Image
        //   175	171	21	imgType	String
        //   182	3	22	it	java.util.Iterator<javax.imageio.ImageReader>
        //   194	140	23	reader	javax.imageio.ImageReader
        //   216	121	24	param	javax.imageio.ImageReadParam
        //   224	50	25	rwidth	int
        //   232	60	26	rheight	int
        //   249	69	27	newWidth	int
        //   267	53	28	newHeight	int
        //   324	5	29	rect	java.awt.Rectangle
        //   341	3	30	bi	java.awt.image.BufferedImage
        //   371	25	31	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   151	371	371	finally
    }

    private String getImgType(String ext) {
        if (!StringUtil.isBlank(ext)) {
            if (ext.trim().contains("jpg")) {
                this.log.debug("获取图片格式jpg");
                return "jpg";
            }
            if (ext.trim().contains("png")) {
                this.log.debug("获取图片格式png");
                return "png";
            }
        }
        this.log.debug("获取图片格式失败" + ext);
        return ext;
    }

    private boolean validateFile(File file, Integer limitwidth, Integer limitheight) {
        try {
            Image img = ImageIO.read(file);
            if ((limitheight != null) && (limitheight.intValue() != 0)) {
                if (img.getHeight(null) > limitheight.intValue()) {
                    return false;
                }
            }
            if ((limitwidth != null) && (limitwidth.intValue() != 0)) {
                if (img.getWidth(null) > limitwidth.intValue()) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @RequestMapping({"/removeFile"})
    public void removeFile(String fileFileName, HttpServletResponse rsp)
            throws Exception {
        Attachment file = this.commonService.findByAttribute(Attachment.class, "uuid", fileFileName);
        if (file != null) {
            outSuccess(rsp);
        } else {
            outError("附件不存在", rsp);
        }
    }

    public ICommonService getCommonService() {
        return this.commonService;
    }

    @Required
    @Resource
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }
}
