package top.chendawei.file.web.tag;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ImageTag
        extends TagSupport {
    private static final long serialVersionUID = 4969976335926608773L;
    private String name;
    private String minName;
    private String fileName;
    private String minFileName;
    private Integer width;
    private Integer height;
    private String ext;
    private String title;
    private String viewTag;
    private String folder;
    private long size;

    public int doStartTag()
            throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            StringBuilder html = new StringBuilder();
            long id = RandomUtils.nextLong();
            html.append("<input type=\"file\" name=\"file\" fname='f_" + this.name + "' id='f_" + id +
                    "' onchange=\"upload(this,'" + this.name + "','" + this.viewTag + "'," + this.width + "," + this.height + ",'" + this.ext +
                    "',false,'" + this.folder + "','" + this.minName + "')\" title='" + this.title + "'>");
            if (!StringUtils.isBlank(this.fileName)) {
                this.fileName = this.fileName.trim();
                html.append(getFileItem(this.fileName, this.minFileName));
            }
            out.println(html);
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return 0;
    }

    private String getFileItem(String fileName, String minFileName) {
        long id = RandomUtils.nextLong();
        String fileId = this.name.substring(this.name.indexOf(".") + 1);
        StringBuilder html = new StringBuilder();
        html.append("<div class='fileitemx' id='file_" + id + "'>");
        html.append("<input type='hidden' id='" + fileId + "' name='" + this.name + "' value='" + fileName + "'/><input type='hidden' id='" + fileId + "min' name='" + this.minName + "' value='" + minFileName + "'/>");

        html.append("<a id='a_" + id + "' class='fancybox' onclick=\"viewImage('" + fileName + "')\">点击预览</a>");
        html.append("<a class='right' aname='a_" + this.name + "' txt='manager.photo' pid='file_" + id +
                "' onclick=\"clearFile(this,'" + this.folder + "','" + fileName + "')\">删除</a>");
        html.append("</div>");
        return html.toString();
    }

    public int doEndTag()
            throws JspException {
        return 6;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewTag() {
        return this.viewTag;
    }

    public void setViewTag(String viewTag) {
        this.viewTag = viewTag;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getMinName() {
        return this.minName;
    }

    public void setMinName(String minName) {
        this.minName = minName;
    }

    public String getMinFileName() {
        return this.minFileName;
    }

    public void setMinFileName(String minFileName) {
        this.minFileName = minFileName;
    }
}
