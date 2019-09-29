package net.swa.index.web.action;

import net.swa.system.web.action.AbstractBaseAction;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class AbstractBaseIndexAction
        extends AbstractBaseAction {
    private static final long serialVersionUID = 3625388438776145908L;
    private final Logger log = Logger.getLogger(AbstractBaseIndexAction.class);

    protected void addCookie(HttpServletResponse resp, String name, String value) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(31536000);
            resp.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            this.log.debug("UnsupportedEncodingException 转化中文");
            e.printStackTrace();
        }
    }

    protected String getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            Cookie[] arrayOfCookie1;
            int j = (arrayOfCookie1 = cookies).length;
            for (int i = 0; i < j; i++) {
                Cookie cookie = arrayOfCookie1[i];
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}