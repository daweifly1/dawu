package top.chendawei.main;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.chendawei.interceptor.CustomsSecurityProperties;
import top.chendawei.system.beans.entity.User;
import top.chendawei.util.EncryptTool;
import top.chendawei.util.fastjson.FastJsonUtil;
import top.chendawei.util.http.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class UserFacadeImpl {

    public static final ThreadLocal<User> USER_TL = new ThreadLocal<User>();

    @Autowired
    private CustomsSecurityProperties customsSecurityProperties;

    public String getToken(User user) {
        String token = JWT.create().withAudience(FastJsonUtil.toJSONString(user))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    public String getDecode(String token) {
        String s = JWT.decode(token).getAudience().get(0);
        return s;
    }

    public User getCookieUser() {
        User u = USER_TL.get();
        if (null != u) {
            return u;
        }
        String token = CookieUtil.getCookieValueByName(getHttpRequest(), "cuser");
        if (StringUtils.isBlank(token)) {
            log.info("getCookieUser null 1");
            return null;
        }
        String userStr = getDecode(token);
        if (StringUtils.isBlank(userStr)) {
            log.info("getCookieUser error 2");
            return null;
        }
        u = FastJsonUtil.parse(userStr, User.class);
        USER_TL.set(u);
        return u;
    }

    public void saveUser2Cookie(User dbUser) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        CookieUtil.setCookie(response, "cuser", getToken(dbUser), 0);
    }


    public static HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes != null ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
    }

    public void saveValidateCodeAttribute(String code) {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = requestAttributes.getResponse();
//        CookieUtil.setCookie(response, "validateCode", encodeValidateCode(code), 0);
        getHttpSession().setAttribute("validateCode", code);
    }

    public String getValidateCodeAttribute() {
//        String s = CookieUtil.getCookieValueByName(getHttpRequest(), "validateCode");
//        return decodeValidateCode(s);
        return getHttpSession().getAttribute("validateCode").toString();
    }

    private String decodeValidateCode(String code) {
        EncryptTool tool = null;
        try {
            tool = new EncryptTool(customsSecurityProperties.getValidateCodePub());
            String s = tool.decrypt(code);
            if (s.contains("====")) {
                return s.substring(0, s.indexOf("===="));
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return "";
    }

    private String getHttpSessionId() {
        return getHttpSession().getId();
    }

    private HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }

    private String encodeValidateCode(String code) {
        EncryptTool tool = null;
        String sid = getHttpSessionId();
        code = code + "====" + sid;
        try {
            tool = new EncryptTool(customsSecurityProperties.getValidateCodePub());
            String s = tool.encrypt(code);
            return s;
        } catch (Exception e) {
            log.error("", e);
        }
        return "";
    }

}
