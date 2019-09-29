package net.swa.main.web.action;

import lombok.extern.slf4j.Slf4j;
import net.swa.system.beans.entity.Menu;
import net.swa.system.beans.entity.User;
import net.swa.system.service.ICommonService;
import net.swa.system.service.IMenuService;
import net.swa.system.web.controller.AbstractBaseController;
import net.swa.util.EncryptTool;
import net.swa.util.JsonResult;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MainAction
        extends AbstractBaseController {
    private static final long serialVersionUID = -8445520563685861470L;
    private ICommonService commonService;
    private IMenuService menuService;

    @RequestMapping({"/main"})
    public ModelAndView main(HttpServletRequest request, HttpSession session)
            throws Exception {
        log.debug("进入管理后台。。。");
        User user = (User) session.getAttribute("cuser");
        if (user == null) {
            return new ModelAndView("main/login");
        }
        List<Menu> roots = this.menuService.getRootsByRoleId(((User) session.getAttribute("cuser")).getRole().getId().longValue());
        ModelAndView mv = new ModelAndView("main/main");
        mv.addObject("roots", getJsonWithCiecle(roots));
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping({"/adminLogin"})
    public void adminLogin(String code, @ModelAttribute User user, HttpSession session, HttpServletResponse response)
            throws Exception {
        Object vc = session.getAttribute("validateCode");
        String validateCode = "";
        if (vc != null) {
            validateCode = vc.toString();
        }
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
                session.setAttribute("cuser", dbUser);
            } else {
                json.setSuccess(false);
                json.setMessage("用户名或密码错误");
            }
        }

        outJson(json, response);
    }

    @RequestMapping({"/logout"})
    public String logout(HttpSession session) {
        session.removeAttribute("cuser");
        return "redirect:/main.do";
    }

    @RequestMapping({"/profile"})
    public String profile() {
        return "main/updatePwd";
    }

    @RequestMapping({"/resetPwd"})
    public void resetPwd(String oldpwd, String loginName, String password, HttpSession session, HttpServletResponse response)
            throws Exception {
        User u = (User) session.getAttribute("cuser");
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
