package net.swa.system.web.action;

import net.swa.system.beans.entity.Dict;
import net.swa.system.service.ICommonService;
import net.swa.system.service.IDictService;
import net.swa.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping({"/dict"})
public class DictAction
        extends AbstractBaseAction {
    private static final long serialVersionUID = -985277678565361630L;
    private ICommonService commonService;
    private IDictService dictService;

    @RequestMapping({"/listPage"})
    public ModelAndView listPage()
            throws Exception {
        ModelAndView mv = new ModelAndView("system/dict/list");
        List<Dict> typeList = this.dictService.getDictType();
        mv.addObject("typeList", getJson(typeList));
        return mv;
    }

    @RequestMapping({"/listPage2"})
    public ModelAndView listPage2()
            throws Exception {
        ModelAndView mv = new ModelAndView("system/dict2/list");
        List<Dict> typeList = this.dictService.getDictType2();
        mv.addObject("typeList", getJson(typeList));
        return mv;
    }

    @RequestMapping({"/edit"})
    public ModelAndView edit(Long id)
            throws Exception {
        ModelAndView mv = new ModelAndView("system/dict/edit");
        Dict dict = new Dict();
        if (id != null) {
            dict = this.commonService.commonFind(Dict.class, id);
        }
        mv.addObject("dict", dict);
        return mv;
    }

    @RequestMapping({"/edit2"})
    public ModelAndView edit2(@ModelAttribute Dict dict, String type)
            throws Exception {
        ModelAndView mv = new ModelAndView("system/dict2/edit");
        if (dict == null) {
            dict = new Dict();
            if (!StringUtils.isBlank(type)) {
                dict.setTitle(type);
            }
        } else {
            dict = this.commonService.commonFind(Dict.class, dict.getId());
        }
        mv.addObject("dict", dict);
        return mv;
    }

    @RequestMapping({"/save"})
    public void save(@ModelAttribute Dict dict, HttpServletResponse rsp)
            throws Exception {
        JsonResult<Dict> json = new JsonResult();
        if (dict.getId() == 0L) {
            this.commonService.commonAdd(dict);
        } else {
            this.commonService.commonUpdate(dict);
        }
        outJson(json, rsp);
    }

    @RequestMapping({"/save2"})
    public void save2(@ModelAttribute Dict dict, HttpServletResponse rsp)
            throws Exception {
        JsonResult<Dict> json = new JsonResult();
        if (!StringUtils.isBlank(dict.getValue())) {
            if ("不限".equals(dict.getValue().trim())) {
                dict.setKey("");
            } else {
                dict.setKey(dict.getValue());
            }
        }
        if (dict.getId() == 0L) {
            this.commonService.commonAdd(dict);
        } else {
            this.commonService.commonUpdate(dict);
        }
        outJson(json, rsp);
    }

    @RequestMapping({"/savesort"})
    public void savesort(Long[] dicIds, Long[] dicNums, HttpServletResponse rsp)
            throws Exception {
        this.dictService.updateDicNum(dicIds, dicNums);
        JsonResult<Dict> json = new JsonResult();
        outJson(json, rsp);
    }

    @RequestMapping({"/delete"})
    public void delete(Long[] ids, HttpServletResponse rsp)
            throws Exception {
        JsonResult<String> json = this.dictService.openSessiondelete(ids);
        outJson(json, rsp);
    }

    @Resource
    @Required
    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }

    @Resource
    @Required
    public void setDictService(IDictService dictService) {
        this.dictService = dictService;
    }
}
