package top.chendawei.system.web.controller;

import top.chendawei.system.beans.entity.Menu;
import top.chendawei.system.service.ICommonService;
import top.chendawei.system.service.IMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping({"/menu"})
public class MenuController extends AbstractBaseController {
    private static final long serialVersionUID = -3395155013072080770L;
    private ICommonService commonService;
    private IMenuService menuService;

    @RequestMapping({"/listPage"})
    public String listPage()
            throws Exception {
        return "system/menu/list";
    }

    @RequestMapping({"/rootTree"})
    public void rootTree(HttpServletRequest req, HttpServletResponse rsp)
            throws Exception {
        String str = req.getParameter("value");
        long roleId = 0L;
        if (!StringUtils.isEmpty(str)) {
            roleId = Long.parseLong(str);
        }
        List<Menu> rootList = this.menuService.getRootMenu(roleId);
        Menu root = new Menu();
        root.setId(Long.valueOf(0L));
        root.setTitle("功能菜单");
        root.setSubMenus(rootList);

        outString(root.toXml(), rsp);
    }

    @RequestMapping({"/edit"})
    public ModelAndView edit(@ModelAttribute Menu menu, Long id)
            throws Exception {
        ModelAndView mv = new ModelAndView("system/menu/edit");
        if ((id != null) && (id.longValue() != 0L)) {
            menu = this.commonService.commonFind(Menu.class, id.longValue());
        } else {
            menu = new Menu();
        }
        mv.addObject("menu", menu);
        return mv;
    }

    @RequestMapping({"/save"})
    public void save(@ModelAttribute Menu menu, HttpServletResponse rsp)
            throws Exception {
        if ((menu.getParent() != null) && ((menu.getParent().getId() == null) || (menu.getParent().getId().longValue() == 0L))) {
            menu.setParent(null);
        }
        if ((menu.getId() == null) || (0L == menu.getId().longValue())) {
            this.commonService.commonAdd(menu);
        } else {
            this.commonService.commonUpdate(menu);
        }
        outSuccess(rsp);
    }

    @RequestMapping({"/delete"})
    public void delete(@ModelAttribute Menu menu, Long id, HttpServletResponse rsp)
            throws Exception {
        List<Menu> subMenus = this.commonService.search("parent.id", id, Menu.class);
        if (subMenus.size() > 0) {
            outError("该菜单下含有子菜单，请先删除子菜单！", rsp);
        } else {
            this.commonService.commonDelete("Menu", menu.getId());
            outSuccess(rsp);
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
