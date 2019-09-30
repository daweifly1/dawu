package top.chendawei.ring.web.action;

import top.chendawei.system.service.ICommonService;
import top.chendawei.system.web.controller.AbstractBaseController;
import top.chendawei.util.JsonResult;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/comm"})
public class CommonAction
        extends AbstractBaseController {
    private static final long serialVersionUID = 523073008569799989L;
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
    public void delete(String type, Long[] ids, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JsonResult<String> json = new JsonResult();
        try {
            this.commonService.commonDelete(type, ids);
        } catch (Exception e) {
            e.printStackTrace();
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
        List<String> attrValues = null;
        List<String> operators = null;
        String[] attrNames = request.getParameterValues("attrNames");
        if (attrNames == null) {
            attrNames = new String[0];
        }
        List<String> paramName = new ArrayList();
        attrValues = new ArrayList();
        operators = new ArrayList();
        for (int i = 0; i < attrNames.length; i++) {
            String[] value = request.getParameterValues(attrNames[i]);
            if (!paramName.contains(attrNames[i])) {
                if (value != null) {
                    if (value.length > 1) {
                        String[] opers = request.getParameterValues(attrNames[i] + "_operator");
                        for (int j = 0; j < value.length; j++) {
                            attrValues.add(value[j]);
                            paramName.add(attrNames[i]);
                            operators.add(opers[j]);
                        }
                    } else if (value.length == 1) {
                        paramName.add(attrNames[i]);
                        attrValues.add(value[0]);
                        operators.add(request.getParameter(attrNames[i] + "_operator"));
                    }
                }
            }
        }
        Class<?> cLz = Class.forName(type);
        String[] searchAttr = new String[paramName.size()];
        paramName.toArray(searchAttr);
        String[] searchVal = new String[paramName.size()];
        attrValues.toArray(searchVal);
        String[] searchOper = new String[paramName.size()];
        operators.toArray(searchOper);

        JsonResult<?> json = this.commonService.search(searchAttr, searchVal, searchOper, cLz, currentPage.intValue(), pageSize.intValue(), orderBy, orderType);
        json.setCurrentPage(currentPage.intValue());
        outJson(json, response);
    }

    public ICommonService getCommonService() {
        return this.commonService;
    }

    @Resource
    @Required
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }
}
