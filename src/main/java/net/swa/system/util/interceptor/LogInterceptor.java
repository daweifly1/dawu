package net.swa.system.util.interceptor;

import net.swa.system.beans.entity.OperationLog;
import net.swa.system.beans.entity.User;
import net.swa.system.service.ICommonService;
import net.swa.system.util.annotation.Log;
import net.swa.util.DateUtils;
import net.swa.util.http.HttpServletUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LogInterceptor
        extends HandlerInterceptorAdapter {
    @Resource(name = "commonService")
    private ICommonService commonService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
        String methodName = methodNameResolver.getHandlerMethodName(request);
        String clzName = request.getClass().getCanonicalName();
        Class<?> clz = Class.forName(clzName);
        Method method = clz.getMethod(methodName);
        if (method.isAnnotationPresent(Log.class)) {
            User user = (User) request.getSession().getAttribute("cuser");
            OperationLog oLog = new OperationLog();
            if (user != null) {
                oLog.setUserid(user.getLoginName());
            }
            oLog.setUpdateDate(DateUtils.getCurrDate(null));
            String ip = HttpServletUtil.getIpFromRequest(request);
            oLog.setIp(ip);
            String uri = request.getRequestURI();
            oLog.setUri(uri);

            Log log = method.getAnnotation(Log.class);
            String descript = log.description();
            oLog.setDescript(descript);
            this.commonService.commonAdd(oLog);
        }
        return super.preHandle(request, response, handler);
    }
}
