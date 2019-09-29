package net.swa.index.web.action;

import net.swa.index.beans.entity.ApkVersion;
import net.swa.index.beans.entity.GymLog;
import net.swa.index.beans.entity.Members;
import net.swa.index.service.ApkVersionService;
import net.swa.index.service.IndexService;
import net.swa.system.web.action.AbstractBaseAction;
import net.swa.util.DateUtils;
import net.swa.util.EncryptTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/index"})
public class IndexAction
        extends AbstractBaseAction {
    private IndexService indexService;
    private ApkVersionService apkVersionService;

    @RequestMapping({"/login"})
    public void login(String username, String password, String imei, String type, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        map = this.indexService.login(username, password, imei, type);
        if (map.get("user") != null) {
            Members o = (Members) map.get("user");
            o.setPassword(null);
            map.put("user", o);
        }
        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("登录");
            glog.setUsername(username);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/register"})
    public void register(String username, String password, String imei, String question1, String answer1, String question2, String answer2, String question3, String answer3, String type, Integer qx1, Integer qx2, Integer qx3, Integer qx4, Integer qx5, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        qx1 = Integer.valueOf(qx1 == null ? 0 : 1);
        qx2 = Integer.valueOf(qx2 == null ? 0 : 1);
        qx3 = Integer.valueOf(qx3 == null ? 0 : 1);
        qx4 = Integer.valueOf(qx4 == null ? 0 : 1);
        qx5 = Integer.valueOf(qx5 == null ? 0 : 1);
        map = this.indexService.saveRegister(username, password, imei, question1, answer1, question2, answer2, question3, answer3, type, qx1, qx2, qx3, qx4, qx5);

        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("注册");
            if (map.get("result") != null) {
                glog.setUsername((String) map.get("result"));
            }
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/modifyPassword"})
    public void modifyPassword(String oldPassword, String newPassword, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);
        map = this.indexService.modifyPassword(username, oldPassword, newPassword, imei);

        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("密码修改");
            glog.setUsername(username);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/findPasswordQuestion"})
    public void findPasswordQuestion(String username, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        map = this.indexService.queryQuestions(username);

        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("查询找回密码问题");
            glog.setUsername(username);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/findPassword"})
    public void findPassword(String username, String question1, String answer1, String question2, String answer2, String question3, String answer3, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        map = this.indexService.queryPassword(username, question1, answer1, question2, answer2, question3, answer3);

        outJson(map, rsp);
    }

    @RequestMapping({"/resetPassword"})
    public void resetPassword(String username, String password, String secret, HttpServletResponse rsp)
            throws Exception {
        String token = null;
        try {
            EncryptTool tool = new EncryptTool(DateUtils.getCurrDate("yyyy-MM-dd"));
            token = tool.encrypt(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((token != null) && (secret != null) && (secret.equals(token))) {
            Map<String, Object> map = null;
            map = this.indexService.resetPassword(username, password);

            Boolean b = (Boolean) map.get("success");
            if ((b != null) && (b.booleanValue())) {
                GymLog glog = new GymLog();
                glog.setCreateTime(DateUtils.getCurrDate(null));
                glog.setName("密码修改");
                glog.setUsername(username);
                this.indexService.saveLog(glog);
            }
            outJson(map, rsp);
        } else {
            outError("安全secret校验失败", rsp);
        }
    }

    @RequestMapping({"/myAccount"})
    public void myAccount(String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);
        map = this.indexService.queryMyAccount(username);

        outJson(map, rsp);
    }

    @RequestMapping({"/queryDetail"})
    public void queryDetail(String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.queryDetail(username);

        outJson(map, rsp);
    }

    @RequestMapping({"/saveNewInfo"})
    public void saveNewInfo(String token, String imei, String type, Double money, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);
        map = this.indexService.saveNewInfo(username, type, money);

        outJson(map, rsp);
    }

    @RequestMapping({"/queryInfos"})
    public void queryInfos(String token, String imei, String type, Integer status, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);
        map = this.indexService.queryInfos(username, type, status);

        outJson(map, rsp);
    }

    @RequestMapping({"/queryMembers"})
    public void queryMembers(String token, String imei, String[] types, Boolean locked, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.queryMembers(username, types, locked);

        outJson(map, rsp);
    }

    @RequestMapping({"/queryMembersByType"})
    public void queryMembersByType(String token, String imei, String type1, String type2, String type3, Boolean locked, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        List<String> list = new ArrayList();
        if (!StringUtils.isBlank(type1)) {
            list.add(type1);
        }
        if (!StringUtils.isBlank(type2)) {
            list.add(type2);
        }
        if (!StringUtils.isBlank(type3)) {
            list.add(type3);
        }
        String[] types = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            types[i] = list.get(i);
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.queryMembers(username, types, locked);

        outJson(map, rsp);
    }

    @RequestMapping({"/shezhiByAdmin"})
    public void shezhiByAdmin(String username, Integer qx1, Integer qx2, Integer qx3, Integer qx4, Integer qx5, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.shezhiByAdmin(adminname, username, qx1, qx2, qx3, qx4, qx5);

        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("设置修改");
            glog.setUsername(adminname);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryNewInfo"})
    public void queryNewInfo(String token, String imei, String type, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.queryNewInfo(username, type);

        outJson(map, rsp);
    }

    @RequestMapping({"/updateMoney"})
    public void updateMoney(String token, String imei, Long id, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.updateMoney(username, id);

        outJson(map, rsp);
    }

    @RequestMapping({"/ignoreEditMoney"})
    public void ignoreEditMoney(String token, String imei, Long id, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.updateIgnoreMoney(username, id);

        outJson(map, rsp);
    }

    @RequestMapping({"/ignoreByAdmin"})
    public void ignoreByAdmin(String token, String imei, Long id, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.updateIgnoreByAdmin(username, id);

        outJson(map, rsp);
    }

    @RequestMapping({"/queryNewVersion"})
    public void queryNewVersion(HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = new HashMap();
        ApkVersion model = this.apkVersionService.queryLastAdmVersion(false);
        if (model != null) {
            map.put("success", Boolean.valueOf(true));
            map.put("model", model);
            map.put("message", "");
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "无版本信息");
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryAdminVersion"})
    public void queryAdminVersion(HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = new HashMap();
        ApkVersion model = this.apkVersionService.queryLastAdmVersion(true);
        if (model != null) {
            map.put("success", Boolean.valueOf(true));
            map.put("model", model);
            map.put("message", "");
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "无版本信息");
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryTypeExs"})
    public void queryTypeExs(String type, String username, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        map = this.indexService.queryTypeExs(type, username);
        outJson(map, rsp);
    }

    @RequestMapping({"/queryUserDetail"})
    public void queryUserDetail(String token, String imei, String date, String type, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);

        map = this.indexService.queryUserDetail(username, date, type);

        Boolean b = (Boolean) map.get("success");

        outJson(map, rsp);
    }

    @RequestMapping({"/queryByAdminDetail"})
    public void queryByAdminDetail(String token, String imei, String username, String date, String type, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);

        map = this.indexService.queryByAdminDetail(adminname, username, date, type);

        outJson(map, rsp);
    }

    @RequestMapping({"/queryRecordsAdm"})
    public void queryRecordsAdm(String token, String imei, String username, String date, String type, String status, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);

        map = this.indexService.queryRecordsAdm(adminname, username, date, type, status);

        outJson(map, rsp);
    }

    @RequestMapping({"/updateLimit"})
    public void updateLimit(Double minusLimit, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String username = tool.decrypt(token);
        if (minusLimit == null) {
            minusLimit = Double.valueOf(30.0D);
        }
        map = this.indexService.updateLimit(username, minusLimit);

        outJson(map, rsp);
    }

    @RequestMapping({"/editByAdmin"})
    public void editByAdmin(Long id, String username, String type, Double money, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.updateEditByAdmin(adminname, id, username, type, money);
        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("记录修改");
            glog.setUsername(adminname);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryShenjiByAdmin"})
    public void queryShenjiByAdmin(String username, String name, String sdate, String edate, String querydate, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.shenjiByAdmin(adminname, name, sdate, edate, querydate, username);

        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("审核");
            glog.setUsername(adminname);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryLockedUsers"})
    public void queryLockedUsers(String type, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.queryLockedUsers(adminname, type);
        outJson(map, rsp);
    }

    @RequestMapping({"/updateUnLocked"})
    public void updateUnLocked(String username, Long id, String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.updateUnLocked(adminname, username, id);
        outJson(map, rsp);
    }

    @RequestMapping({"/backUpDb"})
    public void backUpDb(String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.addBackUpDb(adminname);

        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("备份");
            glog.setUsername(adminname);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryBackHis"})
    public void queryBackHis(String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String adminname = tool.decrypt(token);
        map = this.indexService.queryBackHis(adminname);
        outJson(map, rsp);
    }

    @RequestMapping({"/logout"})
    public void logout(String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String loginname = tool.decrypt(token);
        map = this.indexService.queryOneUser(loginname);
        Boolean b = (Boolean) map.get("success");
        if ((b != null) && (b.booleanValue())) {
            GymLog glog = new GymLog();
            glog.setCreateTime(DateUtils.getCurrDate(null));
            glog.setName("退出");
            glog.setUsername(loginname);
            this.indexService.saveLog(glog);
        }
        outJson(map, rsp);
    }

    @RequestMapping({"/queryLastUpdate"})
    public void queryLastUpdate(String token, String imei, HttpServletResponse rsp)
            throws Exception {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(imei)) {
            imei = "";
        }
        EncryptTool tool = new EncryptTool(imei);
        String loginname = tool.decrypt(token);
        map = this.indexService.updateAfterQuery(loginname);
        outJson(map, rsp);
    }

    @Required
    @Resource
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    @Required
    @Resource
    public void setApkVersionService(ApkVersionService apkVersionService) {
        this.apkVersionService = apkVersionService;
    }
}
