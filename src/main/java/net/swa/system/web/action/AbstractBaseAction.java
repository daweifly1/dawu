package net.swa.system.web.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.swa.util.JsonResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public abstract class AbstractBaseAction {
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
        String json = null;
        if ((obj instanceof Collection)) {
            JSONArray ja = JSONArray.fromObject(obj);
            json = ja.toString();
        } else {
            json = JSONObject.fromObject(obj).toString();
        }
        response.getWriter().println(json);
        response.getWriter().flush();
    }

    public String getJson(Object obj) {
        String json = null;
        if ((obj instanceof Collection)) {
            JSONArray ja = JSONArray.fromObject(obj);
            json = ja.toString();
        } else {
            json = JSONObject.fromObject(obj).toString();
        }
        return json;
    }

    public String getJsonWithCiecle(Object obj) {
        String json = null;
        if ((obj instanceof Collection)) {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setIgnoreDefaultExcludes(false);
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

            JSONArray ja = JSONArray.fromObject(obj, jsonConfig);
            json = ja.toString();
        } else {
            json = JSONObject.fromObject(obj).toString();
        }
        return json;
    }
}
