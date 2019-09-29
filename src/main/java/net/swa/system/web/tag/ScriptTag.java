package net.swa.system.web.tag;

import net.swa.util.DateUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScriptTag
        extends TagSupport {
    private static final long serialVersionUID = 7278706016067991155L;
    private String folder;

    public int doStartTag()
            throws JspException {
        String str = this.pageContext.getServletContext().getRealPath(this.folder);
        File dir = new File(str);
        File[] list = dir.listFiles();
        JspWriter out = this.pageContext.getOut();
        String s = DateUtils.dateToString(new Date(), "yyyy-MM-dd");
        try {
            File[] arrayOfFile1;
            int j = (arrayOfFile1 = list).length;
            for (int i = 0; i < j; i++) {
                File f = arrayOfFile1[i];
                out.println("<script type='text/javascript' src='" + this.folder + "/" + f.getName() + "?v=" + s + "'></script>");
            }
        } catch (IOException localIOException) {
        }
        return super.doStartTag();
    }

    public int doEndTag()
            throws JspException {
        return super.doEndTag();
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
