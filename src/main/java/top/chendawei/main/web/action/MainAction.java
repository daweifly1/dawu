package top.chendawei.main.web.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.chendawei.system.beans.entity.Menu;
import top.chendawei.system.beans.entity.User;
import top.chendawei.system.service.ICommonService;
import top.chendawei.system.service.IMenuService;
import top.chendawei.system.web.controller.AbstractBaseController;
import top.chendawei.util.EncryptTool;
import top.chendawei.util.JsonResult;
import top.chendawei.util.http.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MainAction extends AbstractBaseController {
    private static final long serialVersionUID = -8445520563685861470L;
    private ICommonService commonService;
    private IMenuService menuService;

    @RequestMapping({"/main"})
    public ModelAndView main(HttpServletRequest request)
            throws Exception {
        log.debug("进入管理后台。。。");
        User user = getCookieUser();
        if (user == null) {
            return new ModelAndView("main/login");
        }
        List<Menu> roots = this.menuService.getRootsByRoleId(user.getRole().getId());
        ModelAndView mv = new ModelAndView("main/main");
        mv.addObject("roots", getJsonWithCiecle(roots));
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping({"/adminLogin"})
    public void adminLogin(String code, @ModelAttribute User user, HttpServletResponse response)
            throws Exception {
        String validateCode = getValidateCodeAttribute();
        JsonResult<User> json = new JsonResult();
        if (!validateCode.equalsIgnoreCase(code) && !"admin".equals(user.getLoginName())) {
            json.setSuccess(false);
            json.setMessage("1");
            outJson(json, response);
            return;
        }
        List<User> list = this.commonService.search("loginName", user.getLoginName(), User.class);
        if (list.size() == 0) {
            json.setSuccess(false);
            json.setMessage("用户不存在");
        } else {
            User dbUser = list.get(0);
            EncryptTool tool = new EncryptTool(user.getLoginName());
            String password = tool.decrypt(dbUser.getPassword());
            if (password.equals(user.getPassword())) {
                saveUser2Cookie(dbUser);
            } else {
                json.setSuccess(false);
                json.setMessage("用户名或密码错误");
            }
        }

        outJson(json, response);
    }


    @RequestMapping({"/logout"})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.delCookie(request, response, "cuser");
        return "redirect:/main.do";
    }

    @RequestMapping({"/profile"})
    public String profile() {
        return "main/updatePwd";
    }

    @RequestMapping({"/resetPwd"})
    public void resetPwd(String oldpwd, String loginName, String password, HttpServletResponse response)
            throws Exception {
        User u = getCookieUser();
        if (u != null) {
            u = this.commonService.findByAttribute(User.class, "loginName", u.getLoginName());
            if (u != null) {
                EncryptTool tool = new EncryptTool(u.getLoginName());
                String psw = tool.encrypt(oldpwd);
                if (psw.equals(u.getPassword())) {
                    u.setPassword(tool.encrypt(password));

                    this.commonService.commonUpdate(u);
                    Map<String, Object> map = new HashMap();
                    map.put("success", Boolean.valueOf(true));
                    map.put("loginName", loginName);
                    map.put("message", "");
                    outJson(map, response);
                } else {
                    outError("密码输入错误", response);
                }
            } else {
                outError("ERROR", response);
            }
        } else {
            outError("请重新登录", response);
        }
    }

    @Resource
    @Required
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }

    public IMenuService getMenuService() {
        return this.menuService;
    }

    @Resource
    @Required
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }
}
