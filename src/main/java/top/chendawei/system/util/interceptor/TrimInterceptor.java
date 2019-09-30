package top.chendawei.system.util.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TrimInterceptor
        extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Map map = request.getParameterMap();
        Set keys = map.keySet();
        Iterator iters = keys.iterator();
        while (iters.hasNext()) {
            Object key = iters.next();
            Object value = map.get(key);
            map.put(key, transfer((String[]) value));
        }
        return super.preHandle(request, response, handler);
    }

    private String[] transfer(String[] params) {
        for (int i = 0; i < params.length; i++) {
            if (!StringUtils.isEmpty(params[i])) {
                params[i] = params[i].trim();
            }
        }
        return params;
    }
}
