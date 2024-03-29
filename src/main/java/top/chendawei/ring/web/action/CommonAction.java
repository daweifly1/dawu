package top.chendawei.ring.web.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.chendawei.system.service.ICommonService;
import top.chendawei.system.web.controller.AbstractBaseController;
import top.chendawei.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping({"/comm"})
public class CommonAction extends AbstractBaseController {
    private static final long serialVersionUID = 523073008569799989L;

    @Autowired
    private ICommonService commonService;

    @RequestMapping({"/updateStatus"})
    public void updateStatus(String type, Long[] ids, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JsonResult<String> json = new JsonResult();
        int status = Integer.parseInt(request.getParameter("status"));
        this.commonService.commonUpdateStatus(type, ids, status);
        outJson(json, response);
    }

    @RequestMapping({"/delete"})
    public void delete(String type, Long[] ids, HttpServletResponse response)
            throws Exception {
        JsonResult<String> json = new JsonResult();
        try {
            this.commonService.commonDelete(type, ids);
        } catch (Exception e) {
            log.error("", e);
            json.setSuccess(false);
            json.setMessage(e.getMessage());
        }
        outJson(json, response);
    }

    @RequestMapping({"/search"})
    public void search(Integer currentPage, Integer pageSize, String orderBy, String orderType, String type, Long[] ids, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (currentPage == null) {
            currentPage = Integer.valueOf(1);
        }
        if (pageSize == null) {
            pageSize = Integer.valueOf(1);
        }
        currentPage = Integer.valueOf(currentPage.intValue() == 0 ? 1 : currentPage.intValue());
        pageSize = Integer.valueOf(pageSize.intValue() == 0 ? 20 : pageSize.intValue());
        List<Object> attrValues = null;
        List<String> operators = null;
        String[] attrNames = request.getParameterValues("attrNames");
        if (attrNames == null) {
            attrNames = new String[0];
        }
        List<String> paramName = new ArrayList();
        attrValues = new ArrayList();
        operators = new ArrayList();
        Class<?> cLz = Class.forName(type);
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        Field[] fields = cLz.getDeclaredFields();
        for (Field f : fields) {
            fieldMap.put(f.getName(), f);
        }


        for (int i = 0; i < attrNames.length; i++) {
            String[] value = request.getParameterValues(attrNames[i]);
            if (!paramName.contains(attrNames[i])) {
                Field f = fieldMap.get(attrNames[i]);
                if (null == f) {
                    continue;
                }
                if (value != null) {
                    if (value.length > 1) {
                        String[] opers = request.getParameterValues(attrNames[i] + "_operator");
                        for (int j = 0; j < value.length; j++) {
                            if (f.getType().toString().contains("String")) {
                                attrValues.add(value[j]);
                            } else if (f.getType().toString().toLowerCase().contains("long")) {
                                attrValues.add(Long.parseLong(value[j]));
                            } else if (f.getType().toString().toLowerCase().contains("int")) {
                                attrValues.add(Integer.parseInt(value[j]));
                            } else if (f.getType().toString().toLowerCase().contains("short")) {
                                attrValues.add(Short.parseShort(value[j]));
                            } else if (f.getType().toString().toLowerCase().contains("bool")) {
                                attrValues.add(Boolean.parseBoolean(value[j]));
                            }
                            paramName.add(attrNames[i]);
                            operators.add(opers[j]);
                        }
                    } else if (value.length == 1) {
                        paramName.add(attrNames[i]);
                        if (f.getType().toString().contains("String")) {
                            attrValues.add(value[0]);
                        } else if (f.getType().toString().toLowerCase().contains("long")) {
                            attrValues.add(Long.parseLong(value[0]));
                        } else if (f.getType().toString().toLowerCase().contains("int")) {
                            attrValues.add(Integer.parseInt(value[0]));
                        } else if (f.getType().toString().toLowerCase().contains("short")) {
                            attrValues.add(Short.parseShort(value[0]));
                        } else if (f.getType().toString().toLowerCase().contains("bool")) {
                            attrValues.add(Boolean.parseBoolean(value[0]));
                        }
                        operators.add(request.getParameter(attrNames[i] + "_operator"));
                    }
                }
            }
        }
        String[] searchAttr = new String[paramName.size()];
        paramName.toArray(searchAttr);
        Object[] searchVal = new Object[paramName.size()];
        attrValues.toArray(searchVal);
        String[] searchOper = new String[paramName.size()];
        operators.toArray(searchOper);

        JsonResult<?> json = this.commonService.search(searchAttr, searchVal, searchOper, cLz, currentPage.intValue(), pageSize.intValue(), orderBy, orderType);
        json.setCurrentPage(currentPage.intValue());
        outJson(json, response);
    }

}
