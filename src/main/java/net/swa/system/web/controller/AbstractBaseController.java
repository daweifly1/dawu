package net.swa.system.web.controller;


import net.swa.util.JsonResult;
import net.swa.util.fastjson.FastJsonUtil;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractBaseController {
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
}
