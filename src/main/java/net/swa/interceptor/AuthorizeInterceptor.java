package net.swa.interceptor;

import net.swa.system.beans.entity.OperationLog;
import net.swa.system.beans.entity.User;
import net.swa.system.service.ICommonService;
import net.swa.system.util.annotation.Log;
import net.swa.util.DateUtils;
import net.swa.util.http.HttpServletUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class AuthorizeInterceptor
        extends HandlerInterceptorAdapter {
    private final Logger log = Logger.getLogger(AuthorizeInterceptor.class);
    private List<String> includeAdmURLs;
    private List<String> includeFrontURLs;
    private List<String> exludeURLs;
    private ICommonService commonService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String uri = request.getRequestURI();
        boolean goon = true;
        if ((this.exludeURLs != null) && (this.exludeURLs.size() > 0)) {
            for (String s : this.exludeURLs) {
                if (uri.indexOf(s) != -1) {
                    goon = false;
                    break;
                }
            }
        }
        Object contextPath;
        if ((goon) && (this.includeFrontURLs != null) && (this.includeFrontURLs.size() > 0)) {
            for (String s : this.includeFrontURLs) {
                if (uri.indexOf(s) != -1) {
                    goon = false;
                    break;
                }
            }
            if (!goon) {
                String u = (String) request.getSession().getAttribute("wxid");
                if (StringUtils.isBlank(u)) {
                    contextPath = request.getContextPath();

                    response.sendRedirect(contextPath + "/jumpToLogin.jsp?redirectURL=" + URLEncoder.encode("index/main.do"));
                    this.log.debug("session wxid 过期。 " + uri);
                    return false;
                }
                this.log.debug("拦截器拦截到有人访问微信端页面");
            }
        }
        if ((goon) && (this.includeAdmURLs != null) && (this.includeAdmURLs.size() > 0)) {
            for (contextPath = this.includeAdmURLs.iterator(); ((Iterator) contextPath).hasNext(); ) {
                String s = (String) ((Iterator) contextPath).next();
                if (uri.indexOf(s) != -1) {
                    goon = false;
                    break;
                }
            }
            if (!goon) {
                User user = (User) request.getSession().getAttribute("cuser");
                if (user == null) {
                    String contextPath1 = request.getContextPath();
                    response.sendRedirect(contextPath1 + "/jumpToLogin.jsp?redirectURL=" + URLEncoder.encode("main.do"));
                    this.log.debug("session 过期。 " + uri);
                    return false;
                }
                String adminActions = "RoleActionMenuActionUserActionMenuAction";
                if (adminActions.contains(uri)) {
                    if (user.getUserType().shortValue() != 0) {
                        return false;
                    }
                }
            }
        }
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

    public ICommonService getCommonService() {
        return this.commonService;
    }

    @Required
    @Resource(name = "commonService")
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }

    public void setIncludeAdmURLs(List<String> includeAdmURLs) {
        this.includeAdmURLs = includeAdmURLs;
    }

    public void setIncludeFrontURLs(List<String> includeFrontURLs) {
        this.includeFrontURLs = includeFrontURLs;
    }

    public void setExludeURLs(List<String> exludeURLs) {
        this.exludeURLs = exludeURLs;
    }
}
