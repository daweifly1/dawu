package top.chendawei.index.web.action;

import lombok.extern.slf4j.Slf4j;
import top.chendawei.system.web.controller.AbstractBaseController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
public abstract class AbstractBaseIndexAction
        extends AbstractBaseController {
    private static final long serialVersionUID = 3625388438776145908L;

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
