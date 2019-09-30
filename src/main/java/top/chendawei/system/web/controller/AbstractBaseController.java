package top.chendawei.system.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.chendawei.main.UserFacadeImpl;
import top.chendawei.system.beans.entity.User;
import top.chendawei.util.JsonResult;
import top.chendawei.util.fastjson.FastJsonUtil;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public abstract class AbstractBaseController {
    @Autowired
    private UserFacadeImpl userFacade;

    protected void outError(String msg, HttpServletResponse response)
            throws Exception {
        JsonResult<Object> json = new JsonResult();
        json.setSuccess(false);
        json.setMessage(msg);
        outJson(json, response);
    }

    protected void outSuccess(HttpServletResponse response)
            throws Exception {
        JsonResult<Object> json = new JsonResult();
        outJson(json, response);
    }

    protected void outString(String obj, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println(obj);
        response.getWriter().flush();
    }

    public void outJson(Object obj, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        String json = FastJsonUtil.toJSONString(obj);

        response.getWriter().println(json);
        response.getWriter().flush();
    }

    public String getJson(Object obj) {
        String json = FastJsonUtil.toJSONString(obj);
        return json;
    }

    public String getJsonWithCiecle(Object obj) {
        //TODO
        return getJson(obj);
    }


    public void saveUser2Cookie(User dbUser) {
        userFacade.saveUser2Cookie(dbUser);
    }

    public String getValidateCodeAttribute() {
        return userFacade.getValidateCodeAttribute();
    }

    public User getCookieUser() {
        return userFacade.getCookieUser();
    }

    public void saveValidateCodeAttribute(String code) {
        userFacade.saveValidateCodeAttribute(code);
    }


}
