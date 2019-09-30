package top.chendawei.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.chendawei.main.UserFacadeImpl;
import top.chendawei.system.beans.entity.OperationLog;
import top.chendawei.system.beans.entity.User;
import top.chendawei.system.service.ICommonService;
import top.chendawei.system.util.annotation.Log;
import top.chendawei.util.DateUtils;
import top.chendawei.util.http.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;

@Slf4j
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserFacadeImpl userFacade;
    @Autowired
    CustomsSecurityProperties customsSecurityProperties;
    @Autowired
    private ICommonService commonService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String uri = request.getRequestURI();
        boolean goon = true;
        if (null != customsSecurityProperties.getPermitAccessUrls() && customsSecurityProperties.getPermitAccessUrls().length > 0) {
            for (String s : customsSecurityProperties.getPermitAccessUrls()) {
                if (uri.indexOf(s) != -1) {
                    goon = false;
                    break;
                }
            }
        }
        User user = userFacade.getCookieUser();
        if (user == null) {
            String contextPath1 = request.getContextPath();
            response.sendRedirect(contextPath1 + "/jumpToLogin.jsp?redirectURL=" + URLEncoder.encode("main.do"));
            this.log.debug("cookie 过期。 " + uri);
            return false;
        }
        //TODO 二次校验权限
        if (goon) {
            this.log.debug(uri + "====到死也没有拦截到");
        }
        return super.preHandle(request, response, handler);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            String descript = log.description();

            OperationLog oLog = new OperationLog();
            String uri = request.getRequestURI();

            User user = (User) request.getSession().getAttribute("cuser");
            if (user != null) {
                oLog.setUserid(user.getLoginName());
            }
            oLog.setUpdateDate(DateUtils.getCurrDate(null));
            String ip = HttpServletUtil.getIpFromRequest(request);
            oLog.setIp(ip);
            oLog.setUri(uri);
            oLog.setDescript(descript);
            this.commonService.commonAdd(oLog);
        }
        super.afterCompletion(request, response, handler, ex);
    }

}
