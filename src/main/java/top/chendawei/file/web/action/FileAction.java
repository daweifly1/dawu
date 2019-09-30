package top.chendawei.file.web.action;

import lombok.extern.slf4j.Slf4j;
import top.chendawei.file.beans.entity.Attachment;
import top.chendawei.system.beans.entity.User;
import top.chendawei.system.service.ICommonService;
import top.chendawei.system.web.controller.AbstractBaseController;
import top.chendawei.util.ConfigUtil;
import top.chendawei.util.EncryptTool;
import top.chendawei.util.FileUtil;
import top.chendawei.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping({"/file"})
@Slf4j
public class FileAction extends AbstractBaseController {
    private static final long serialVersionUID = -3566256767528230895L;
    private String uploadPath;
    private String gabagePath;
    private String httpPath;
    private ICommonService commonService;

    public FileAction() {
        this.uploadPath = ConfigUtil.getProperty("uploadPath");
        this.gabagePath = ConfigUtil.getProperty("gabagePath");
        this.httpPath = ConfigUtil.getProperty("httpPath");
    }

    @RequestMapping({"/removeFile"})
    public void removeFile(boolean delete, String fileFileName, HttpServletResponse rsp)
            throws Exception {
        Attachment file = this.commonService.findByAttribute(Attachment.class, "uuid", fileFileName);
        if (file != null) {
            if (delete) {
                this.commonService.commonDelete("Attachment", Long.valueOf(file.getId()));
                String fname = fileFileName.replace(this.httpPath, "").replace("/", "");
                String path = fileFileName.replace(this.httpPath, this.uploadPath);
                File target = new File(path);
                File gcFile = new File(this.gabagePath);
                if (!gcFile.exists()) {
                    gcFile.mkdirs();
                }
                File fnew = new File(this.gabagePath + File.separator + fname);
                target.renameTo(fnew);
                target.delete();
            }
            outSuccess(rsp);
        } else {
            outError("附件不存在", rsp);
        }
    }

    /* Error */
    @RequestMapping({"/download"})
    public String download(String fileFileName, HttpServletResponse response) throws Exception {
        String path = fileFileName.replace(httpPath, uploadPath); //部署时候可配置
        File target = new File(path);
        InputStream in = new FileInputStream(target);
        response.setHeader("Content-disposition", "attachment;filename=" + fileFileName);
        response.setIntHeader("Content-Length", in.available());
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
            bis = new BufferedInputStream(in);
            byte[] b = new byte[1024];
            int len;
            while ((len = bis.read(b)) > 0) {
                bos.write(b, 0, len);
            }
            bos.flush();
        } catch (Exception e) {
            log.warn("", e);
        } finally {
            bos.close();
            bis.close();
        }
        return null;
    }

    @RequestMapping({"/upload"})
    public void upload(String folder, Integer width, Integer height, Double outputWidth, Double outputHeight, String fileFileName, Integer size, HttpServletRequest request, HttpServletRequest req, HttpServletResponse response, HttpSession session)
            throws Exception {
        if ((outputWidth == null) || (outputHeight == null)) {
            outputHeight = Double.valueOf(120.0D);
            outputWidth = Double.valueOf(120.0D);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        fileFileName = file.getOriginalFilename();

        JsonResult<Object> json = new JsonResult();
        boolean mat = true;
        User u = (User) session.getAttribute("cuser");
        if (u == null) {
            this.log.debug("session 为空，应该是session过期，或移动端调用");
            mat = false;
            String mac = req.getParameter("mac");
            String token = req.getParameter("token");
            if (!StringUtils.isBlank(token)) {
                if (StringUtils.isBlank(mac)) {
                    mac = "dewei_default_key";
                }
                EncryptTool tool = new EncryptTool(mac);
                String sid = tool.decrypt(token);
                mat = sid.matches("\\d+");
                this.log.debug("移动端校验结果：" + mat);
            }
        }
        if (mat) {
            String path = this.uploadPath;
            String imgUrl = this.httpPath + folder + "/";
            File target = new File(path + File.separator + folder);
            if (!target.exists()) {
                target.mkdirs();
            }
            File mintarget = new File(path + File.separator + folder + "/min/");
            if (!mintarget.exists()) {
                mintarget.mkdirs();
            }
            String sourceName = fileFileName;
            String ext = FileUtil.getExtension(fileFileName);
            fileFileName = UUID.randomUUID().toString() + "." + ext;
            File desc = new File(target.getAbsoluteFile() + File.separator + fileFileName);
            if ((file.getOriginalFilename() != null) && (!"".equals(file.getOriginalFilename()))) {
                List<String> fileTypes = new ArrayList();
                fileTypes.add("jpg");
                fileTypes.add("jpeg");
                fileTypes.add("bmp");
                fileTypes.add("gif");
                fileTypes.add("png");
                fileTypes.add("apk");
                fileTypes.add("APK");
                fileTypes.add("ios");
                if (fileTypes.contains(ext)) {
                    file.transferTo(desc);
                    if (!validateImage(width, height, desc)) {
                        json.setSuccess(false);
                        json.setMessage("图片尺寸不符合要求");
                        desc.delete();
                    } else {
                        json.setSuccess(true);

                        Attachment attachment = new Attachment();
                        attachment.setName(sourceName);
                        attachment.setUuid(imgUrl + fileFileName);
                        attachment.setExt(ext);
                        attachment.setPath(desc.getAbsolutePath());
                        this.commonService.commonAdd(attachment);
                        json.setAttribute("fileName", imgUrl + fileFileName);
                        json.setAttribute("minfileName", imgUrl + "min/" + fileFileName);
                        json.setAttribute("sourceName", sourceName);
                    }
                } else {
                    json.setSuccess(false);
                    json.setMessage("附件格式不符合要求" + ext);
                }
            } else {
                json.setSuccess(false);
                json.setMessage("附件格式不符合要求,名称为空");
            }
        } else {
            json.setSuccess(false);
            json.setMessage("token 校验失败");
        }
        outJson(json, response);
    }

    private boolean validateImage(Integer width, Integer height, File file)
            throws Exception {
        if ((width != null) && (width.intValue() != 0) && (height != null) && (height.intValue() != 0)) {
            FileInputStream in = new FileInputStream(file);
            BufferedImage img = ImageIO.read(in);
            return (img.getWidth() <= width.intValue()) || (img.getHeight() <= height.intValue());
        }
        return true;
    }

    @RequestMapping({"/viewImg"})
    public void viewImg(String imgUrl, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        try {
            URL url = new URL(imgUrl);
            URLConnection con = url.openConnection();
            int index = imgUrl.indexOf("/", 10);
            con.setRequestProperty("Host", index == -1 ? imgUrl.substring(7) : imgUrl.substring(7, index));
            con.setRequestProperty("Referer", null);
            InputStream is = con.getInputStream();
            BufferedImage image = ImageIO.read(is);
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ICommonService getCommonService() {
        return this.commonService;
    }

    @Resource
    @Required
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }
}
