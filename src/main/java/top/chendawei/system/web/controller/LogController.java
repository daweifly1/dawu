package top.chendawei.system.web.controller;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.chendawei.system.beans.entity.User;
import top.chendawei.system.service.ICommonService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/log"})
public class LogController extends AbstractBaseController {
    private static final long serialVersionUID = -8971852540641555704L;
    private ICommonService commonService;

    @RequestMapping({"/listPage"})
    public ModelAndView listPage() {
        User u = (User) getCookieUser();
        ModelAndView mv = new ModelAndView("system/log/list");
        String loginName = "";
        boolean admin = false;
        if (null != u.getUserType() && u.getUserType().shortValue() == 0) {
            admin = true;
        } else {
            loginName = u.getLoginName();
            admin = false;
        }
        mv.addObject("loginName", loginName);
        mv.addObject("admin", Boolean.valueOf(admin));
        return mv;
    }

    @RequestMapping({"/ShiXiao"})
    public void ShiXiao(Long[] ids, HttpServletResponse rsp)
            throws Exception {
        this.commonService.commonUpdateStatus("OperationLog", ids, -1);
        outSuccess(rsp);
    }

    @Resource
    @Required
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }
}
