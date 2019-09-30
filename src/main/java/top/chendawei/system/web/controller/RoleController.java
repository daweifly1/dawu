package top.chendawei.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.chendawei.system.beans.entity.Menu;
import top.chendawei.system.beans.entity.Role;
import top.chendawei.system.service.ICommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/role"})
public class RoleController extends AbstractBaseController {
    private static final long serialVersionUID = 6771957130989908200L;
    @Autowired
    private ICommonService commonService;

    @RequestMapping({"/listPage"})
    public String listPage() {
        return "system/role/list";
    }

    @RequestMapping({"/rootTree"})
    public void rootTree(HttpServletResponse rsp)
            throws Exception {
        String[] attrNames = new String[1];
        Object[] attrValues = new Object[1];
        StringBuilder buffer = new StringBuilder();
        buffer.append("<item text='root' id='0' open='1'  im0='user.png' im1='user.png' im2='user.png'  >");
        List<Role> roles = this.commonService.search(Role.class, attrNames, attrValues);
        for (Role role : roles) {
            buffer.append(role.toXml());
        }
        buffer.append("</item>");

        outString(buffer.toString(), rsp);
    }

    @RequestMapping({"/edit"})
    public ModelAndView edit(@ModelAttribute Role role, Long id)
            throws Exception {
        ModelAndView mv = new ModelAndView("system/role/edit");
        if ((id == null) || (0L == id.longValue())) {
            role = new Role();
        } else {
            role = this.commonService.commonFind(Role.class, id.longValue());
        }
        mv.addObject("role", role);
        if (role.getMenus() != null) {
            mv.addObject("menus", getJson(role.getMenus()));
        } else {
            mv.addObject("menus", "{}");
        }
        return mv;
    }

    @RequestMapping({"/save"})
    public void save(@ModelAttribute Role role, HttpServletRequest req, HttpServletResponse rsp)
            throws Exception {
        String[] menuIds = req.getParameterValues("menuId");
        List<Menu> menus = new ArrayList();
        if (menuIds != null) {
            String[] arrayOfString1;
            int j = (arrayOfString1 = menuIds).length;
            for (int i = 0; i < j; i++) {
                String s = arrayOfString1[i];

                Menu menu = this.commonService.commonFind(Menu.class, Long.parseLong(s));
                menus.add(menu);
            }
        }
        role.setMenus(menus);
        if (role.getId() == null) {
            this.commonService.commonAdd(role);
        } else {
            this.commonService.commonUpdate(role);
        }
        outSuccess(rsp);
    }
}
