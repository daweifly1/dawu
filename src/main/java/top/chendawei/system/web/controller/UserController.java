package top.chendawei.system.web.controller;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.chendawei.system.beans.entity.Dict;
import top.chendawei.system.beans.entity.Role;
import top.chendawei.system.beans.entity.User;
import top.chendawei.system.service.ICommonService;
import top.chendawei.util.DateUtils;
import top.chendawei.util.EncryptTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping({"/user"})
public class UserController extends AbstractBaseController {
    private static final long serialVersionUID = 8631793382515907988L;
    private ICommonService commonService;

//    public static void main(String[] args) {
//        User user = new User();
//        user.setPassword(StringUtil.getRandomString(6));
//        try {
//            String html = HtmlUtil.parseHtmlFile("net/swa/util/templete.html", user);
//            System.out.println(html);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @RequestMapping({"/listPage"})
    public String listPage()
            throws Exception {
        return "system/user/list";
    }

    @RequestMapping({"/checkUserId"})
    public void checkUserId(@ModelAttribute User user, HttpServletResponse rsp)
            throws Exception {
        List<User> list = this.commonService.search("loginName", user.getLoginName(), User.class);
        outString("" + (list.size() == 0), rsp);
    }

    @RequestMapping({"/edit"})
    public ModelAndView edit(@ModelAttribute User user, Long id)
            throws Exception {
        ModelAndView mv = new ModelAndView("system/user/edit");
        List<Dict> userTypes = null;
        if ((id == null) || (0L == id.longValue())) {
            user = new User();

            User cuser = getCookieUser();
            if (cuser != null) {
                userTypes = this.commonService.search("title", "用户类型", Dict.class);
            }
        } else {
            userTypes = this.commonService.search("title", "用户类型", Dict.class);
            user = this.commonService.commonFind(User.class, id.longValue());
        }
        mv.addObject("user", user);
        mv.addObject("userTypes", getJson(userTypes));
        return mv;
    }



    @RequestMapping({"/save"})
    public void save(@ModelAttribute User user, HttpServletResponse rsp)
            throws Exception {
        user.setRole(this.commonService.commonFind(Role.class, user.getRole().getId().longValue()));
        if (user.getId() == 0L) {
            user.setRegDate(DateUtils.getCurrDate(null));
            user.setPassword("888888");

            EncryptTool tool = new EncryptTool(user.getLoginName());
            user.setPassword(tool.encrypt(user.getPassword()));
            this.commonService.commonAdd(user);
        } else {
            this.commonService.commonUpdate(user);
        }
        outSuccess(rsp);
    }

    @Resource
    @Required
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }
}
