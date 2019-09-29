package net.swa.file.web.tag;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FileTag
        extends TagSupport {
    private static final long serialVersionUID = 4969976335926608773L;
    private String name;
    private String fileName;
    private boolean multible;
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
            html.append("<input type=\"file\" name=\"file\" fname='f_" + this.name + "' id='f_" + id + "' onchange=\"upload(this,'" + this.name + "','" + this.viewTag + "'," + this.width + "," + this.height + ",'" + this.ext + "'," + this.multible + ",'" + this.folder + "')\" title='" + this.title + "'>");
            if (!StringUtils.isBlank(this.fileName)) {
                String[] parts = this.fileName.split(",");
                String[] arrayOfString1;
                int j = (arrayOfString1 = parts).length;
                for (int i = 0; i < j; i++) {
                    String file = arrayOfString1[i];

                    file = file.trim();
                    html.append(getFileItem(file));
                }
            }
            out.println(html);
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return 0;
    }

    private String getFileItem(String fileName) {
        long id = RandomUtils.nextLong();
        String fileId = this.name.substring(this.name.indexOf(".") + 1);
        StringBuilder html = new StringBuilder();
        html.append("<div class='fileitemx' id='file_" + id + "'>");
        html.append("<input type='hidden' id='" + fileId + "' name='" + this.name + "' value='" + fileName + "'/>");

        html.append("<a id='a_" + id + "' class='fancybox' onclick=\"viewImage('" + fileName + "')\">点击查看</a>");
        html.append("<a class='right' aname='a_" + this.name + "' txt='manager.photo' pid='file_" + id + "' onclick=\"clearFile(this,'" + this.folder + "','" + fileName + "')\">删除</a>");
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

    public boolean isMultible() {
        return this.multible;
    }

    public void setMultible(boolean multible) {
        this.multible = multible;
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
}
