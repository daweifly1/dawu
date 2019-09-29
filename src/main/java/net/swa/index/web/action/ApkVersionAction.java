package net.swa.index.web.action;

import net.swa.index.beans.entity.ApkVersion;
import net.swa.index.service.ApkVersionService;
import net.swa.system.web.action.AbstractBaseAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping({"/version"})
public class ApkVersionAction
        extends AbstractBaseAction {
    private static final long serialVersionUID = -1541842392065452147L;
    private final Logger log = Logger.getLogger(ApkVersionAction.class);
    private ApkVersionService apkVersionService;

    @RequestMapping({"/index"})
    public ModelAndView index(@ModelAttribute ApkVersion model) {
        ModelAndView mv = new ModelAndView("index/index");
        model = this.apkVersionService.queryLastAdmVersion(false);
        if (model == null) {
            model = new ApkVersion();
        }
        mv.addObject("model", model);
        return mv;
    }

    @RequestMapping({"/admin"})
    public ModelAndView admin(@ModelAttribute ApkVersion model) {
        ModelAndView mv = new ModelAndView("index/admin");
        model = this.apkVersionService.queryLastAdmVersion(true);
        if (model == null) {
            model = new ApkVersion();
        }
        mv.addObject("model", model);
        return mv;
    }

    @RequestMapping({"/save"})
    public void save(@ModelAttribute ApkVersion model, HttpServletRequest request, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = this.apkVersionService.save(model);
        outJson(map, rsp);
    }

    @Required
    @Resource
    public void setApkVersionService(ApkVersionService apkVersionService) {
        this.apkVersionService = apkVersionService;
    }
}
