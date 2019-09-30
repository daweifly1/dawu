package top.chendawei.index.service.impl;

import lombok.extern.slf4j.Slf4j;
import top.chendawei.index.beans.entity.BackDbHis;
import top.chendawei.index.beans.entity.Deductions;
import top.chendawei.index.beans.entity.GymLog;
import top.chendawei.index.beans.entity.MemberMoney;
import top.chendawei.index.beans.entity.Members;
import top.chendawei.index.beans.entity.QuestionAswer;
import top.chendawei.index.service.IndexService;
import top.chendawei.system.dao.HibernateDaoSupport;
import top.chendawei.util.ConfigUtil;
import top.chendawei.util.Const;
import top.chendawei.util.DateUtils;
import top.chendawei.util.EncryptTool;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("indexService")
public class IndexServiceImpl
        extends HibernateDaoSupport
        implements IndexService {

    public Map<String, Object> login(String loginName, String password, String mac, String type) {
        Map<String, Object> map = new HashMap();
        if ((!StringUtils.isBlank(loginName)) && (!StringUtils.isBlank(password))) {
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);

            List l = q.list();
            if ((l != null) && (l.size() == 1)) {
                Members o = (Members) l.get(0);
                if (o.getLocked() == null) {
                    o.setLocked(Boolean.valueOf(false));
                }
                if (!o.isPassed()) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户未被审核");
                    return map;
                }
                if (!StringUtils.isBlank(type)) {
                    if (!type.equals(o.getRoleId())) {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "登陆角色不一致");
                        return map;
                    }
                }
                if (EncryptTool.MD5(password).equals(o.getPassword())) {
                    if (o.getLocked().booleanValue()) {
                        if (DateUtils.isOutMinus(o.getLockedTime(), 10)) {
                            o.setLocked(Boolean.valueOf(false));
                            o.setErrorTimes(null);
                            o.setFirstLogin(Boolean.valueOf(false));
                            o.setErrorTimes(Integer.valueOf(0));
                        } else {
                            map.put("success", Boolean.valueOf(false));
                            map.put("token", null);
                            map.put("user", null);
                            map.put("message", "用户状态锁定");
                            return map;
                        }
                    }
                    o.setFirstLogin(Boolean.valueOf(false));
                    o.setErrorTimes(Integer.valueOf(0));
                    session.update(o);
                    map.put("success", Boolean.valueOf(false));
                    map.put("token", null);
                    map.put("user", o);
                    map.put("message", "获取token失败");
                    if (StringUtils.isBlank(mac)) {
                        mac = "";
                    }
                    try {
                        EncryptTool tool = new EncryptTool(mac);
                        String token = tool.encrypt(loginName);
                        map.put("success", Boolean.valueOf(true));

                        map.put("token", token);
                        map.put("message", "");
                    } catch (Exception e) {
                        map.put("message", "生成token异常");
                        e.printStackTrace();
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "密码错误");
                    o.setErrorTimes(Integer.valueOf(o.getErrorTimes().intValue() + 1));
                    if (o.getErrorTimes().intValue() < 5) {
                        o.setLocked(Boolean.valueOf(false));
                        map.put("message", "密码错误,您还有" + (5 - o.getErrorTimes().intValue()) + "次机会");
                    } else {
                        o.setLocked(Boolean.valueOf(true));
                        map.put("message", "用户状态已锁定");
                        o.setLockedTime(DateUtils.getCurrDate(null));
                    }
                    session.update(o);
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "用户名不存在");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名密码都不能为空");
        }
        return map;
    }

    public Map<String, Object> saveRegister(String username, String password, String imei, String question1, String answer1, String question2, String answer2, String question3, String answer3, String type, Integer qx1, Integer qx2, Integer qx3, Integer qx4, Integer qx5) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(password)) {
            Session session = getCurrentSession();
            Members m = new Members();
            m.setLoginName("");
            m.setCreateTime(DateUtils.getCurrDate(null));
            m.setLocked(Boolean.valueOf(false));
            m.setMac(imei);
            m.setPassword(EncryptTool.MD5(password));
            m.setRoleId(type);
            if ((type != null) && ("1".equals(type))) {
                m.setLocked(Boolean.valueOf(false));

                m.setPassed(true);
            } else {
                m.setLocked(Boolean.valueOf(true));
                m.setPassed(false);
                m.setLockedTime(DateUtils.getCurrDate(null));
            }
            String uuid = "";
            if ((!StringUtils.isBlank(username)) && (!StringUtils.isBlank(type)) && (!"0".equals(type))) {
                String hql = "from Members where loginName=:loginName";
                Query q = session.createQuery(hql);
                q.setString("loginName", username);
                List l = q.list();
                if ((l != null) && (l.size() > 0)) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户名已经存在");
                    return map;
                }
                m.setLoginName(username);

                m.setQx1(qx1);
                m.setQx2(qx2);
                m.setQx3(qx3);
                m.setQx4(qx4);
                m.setQx5(qx5);
                m.setErrorTimes(Integer.valueOf(0));
                m.setFirstLogin(Boolean.valueOf(true));
                Long id = (Long) session.save(m);
                uuid = "A" + id;
            } else {
                m.setErrorTimes(Integer.valueOf(0));
                m.setFirstLogin(Boolean.valueOf(true));
                Long id = (Long) session.save(m);
                uuid = "A" + id;
                m.setLoginName(uuid);
            }
            m.setUuid(uuid);
            session.update(m);
            if ((!StringUtils.isBlank(type)) && ("0".equals(type))) {
                Query q = null;
                String hql = " from MemberMoney where loginName=:loginName ";
                q = session.createQuery(hql);
                q.setString("loginName", uuid);
                List<MemberMoney> l = q.list();
                MemberMoney um = null;
                if ((l != null) && (l.size() > 0)) {
                    um = l.get(0);
                }
                if (um == null) {
                    um = new MemberMoney();
                    um.setLoginName(uuid);
                    um.setUpdateTime(DateUtils.getCurrDate(null));
                    um.setMoney(Double.valueOf(0.0D));
                    um.setMinusLimit(Const.LIMIT_MONEY);
                    session.save(um);
                }
            }
            QuestionAswer a = new QuestionAswer();
            a.setLoginName(m.getLoginName());
            if ((!StringUtils.isBlank(question1)) && (!StringUtils.isBlank(answer1))) {
                a.setQuestion(question1);
                a.setAnswer(answer1);
                session.save(a);
            }
            QuestionAswer a2 = new QuestionAswer();
            a2.setLoginName(m.getLoginName());
            if ((!StringUtils.isBlank(question2)) && (!StringUtils.isBlank(answer2))) {
                a2.setQuestion(question2);
                a2.setAnswer(answer2);
                session.save(a2);
            }
            QuestionAswer a3 = new QuestionAswer();
            a3.setLoginName(m.getLoginName());
            if ((!StringUtils.isBlank(question3)) && (!StringUtils.isBlank(answer3))) {
                a3.setQuestion(question3);
                a3.setAnswer(answer3);
                session.save(a3);
            }
            map.put("success", Boolean.valueOf(true));
            map.put("message", "");
            map.put("result", m.getLoginName());
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "密码不能为空");
        }
        return map;
    }

    public Map<String, Object> modifyPassword(String loginName, String oldPassword, String newPassword, String mac) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List l = q.list();
            if ((l != null) && (l.size() == 1)) {
                Members o = (Members) l.get(0);
                if (EncryptTool.MD5(oldPassword).equals(o.getPassword())) {
                    o.setPassword(EncryptTool.MD5(newPassword));
                    session.update(o);
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "原密码错误");
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "用户名不存在");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token 校验失败");
        }
        return map;
    }

    public Map<String, Object> queryQuestions(String loginName) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户没有设置问题");
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from QuestionAswer where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<QuestionAswer> l = q.list();
            if ((l != null) && (l.size() > 0)) {
                map.put("message", "用户有设置问题");
                QuestionAswer o = l.get(0);
                map.put("success", Boolean.valueOf(true));
                map.put("question1", o.getQuestion());
                if (l.size() > 1) {
                    o = l.get(1);
                    map.put("question2", o.getQuestion());
                }
                if (l.size() > 2) {
                    o = l.get(2);
                    map.put("question3", o.getQuestion());
                }
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名不为空");
        }
        return map;
    }

    public Map<String, Object> queryPassword(String loginName, String question1, String answer1, String question2, String answer2, String question3, String answer3) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "未设置问题");
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from QuestionAswer where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<QuestionAswer> l = q.list();
            if ((l != null) && (l.size() > 0)) {
                Map<String, String> m = new HashMap();
                map.put("success", Boolean.valueOf(false));
                map.put("message", "问题没有回答");

                boolean checkOK = false;
                for (QuestionAswer a : l) {
                    m.put(a.getQuestion(), a.getAnswer());
                }
                if (!StringUtils.isBlank(question1)) {
                    if ((m.get(question1) != null) && (m.get(question1).equals(answer1))) {
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "问题回答正确");
                        checkOK = true;
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "问题回答错误");
                        map.put("question", question1);
                    }
                }
                if ((!StringUtils.isBlank(question2)) && (!checkOK)) {
                    if ((m.get(question2) != null) && (m.get(question2).equals(answer2))) {
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "问题回答正确");
                        checkOK = true;
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "问题回答错误");
                        map.put("question", question2);
                    }
                }
                if ((!StringUtils.isBlank(question3)) && (!checkOK)) {
                    if ((m.get(question3) != null) && (m.get(question3).equals(answer3))) {
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "问题回答正确");
                        checkOK = true;
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "问题回答错误");
                        map.put("question", question3);
                    }
                }
                if (checkOK) {
                    try {
                        EncryptTool tool = new EncryptTool(DateUtils.getCurrDate("yyyy-MM-dd"));
                        String token = tool.encrypt(loginName);
                        map.put("secret", token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名不为空");
        }
        return map;
    }

    public Map<String, Object> resetPassword(String loginName, String password) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户不存在");
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> l = q.list();
            if ((l != null) && (l.size() == 1)) {
                Members m = l.get(0);
                m.setPassword(EncryptTool.MD5(password));
                m.setErrorTimes(Integer.valueOf(0));
                session.update(m);
                map.put("success", Boolean.valueOf(true));
                map.put("message", "用户密码修改成功");
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "查询用户出错");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名不为空");
        }
        return map;
    }

    public Map<String, Object> queryMyAccount(String loginName) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            map.put("success", Boolean.valueOf(true));
            map.put("message", "");
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from MemberMoney where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<MemberMoney> l = q.list();
            if (l != null) {
                if (l.size() > 0) {
                    MemberMoney m = l.get(0);
                    map.put("result", m.getMoney());
                    map.put("account", m);
                    map.put("minusLimit", m.getMinusLimit());
                } else {
                    map.put("result", Double.valueOf(0.0D));
                    map.put("minusLimit", Const.LIMIT_MONEY);
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "查询用户帐户出错");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名获取失败，token无效");
        }
        return map;
    }

    public Map<String, Object> queryDetail(String loginName) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            map.put("success", Boolean.valueOf(true));
            map.put("message", "");
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from Deductions where username=:loginName and status in(1,2)";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Deductions> l = q.list();
            if (l != null) {
                map.put("result", l);
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "查询用户帐户出错");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名不为空");
        }
        return map;
    }

    public Map<String, Object> saveNewInfo(String loginName, String type, Double money) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            if (StringUtils.isBlank(type)) {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "type 值错误");
                return map;
            }
            if ((money == null) || (money.doubleValue() < 0.0D)) {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "金额要大于0");
                return map;
            }
            if ((type.equals("1")) || (type.equals("0"))) {
                Query q = null;
                Session session = getCurrentSession();
                String hql = "from Members where loginName=:loginName";
                q = session.createQuery(hql);
                q.setString("loginName", loginName);
                List<Members> l = q.list();
                if ((l != null) && (l.size() == 1)) {
                    Members u = l.get(0);
                    if ("0".equals(u.getRoleId())) {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "普通用户无权限");
                        return map;
                    }
                    if (("1".equals(u.getRoleId())) || ((u.getQx1() != null) && (1 == u.getQx1().intValue()))) {
                        Deductions d = new Deductions();
                        d.setCreateTime(DateUtils.getCurrDate(null));
                        d.setExeCname(u.getLoginName());
                        d.setExeUser(u.getLoginName());
                        d.setStatus(Integer.valueOf(0));
                        d.setType(type);
                        d.setMoney(money);
                        d.setUsername(null);
                        session.save(d);
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "操作成功");
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "该用户无权限");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户查询出错");
                }
                return map;
            }
            map.put("success", Boolean.valueOf(false));
            map.put("message", "type 值错误");
            return map;
        }
        map.put("success", Boolean.valueOf(false));
        map.put("message", "用户名不为空");

        return map;
    }

    public Map<String, Object> queryNewInfo(String loginName, String type) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            if ((StringUtils.isBlank(type)) || (type.endsWith("1")) || (type.endsWith("0"))) {
                Query q = null;
                Session session = getCurrentSession();
                String hql = "from Members where loginName=:loginName";
                q = session.createQuery(hql);
                q.setString("loginName", loginName);
                List<Members> l = q.list();
                if ((l != null) && (l.size() == 1)) {
                    Members u = l.get(0);
                    if ("0".equals(u.getRoleId())) {
                        hql = "from Deductions where  status=0";
                        if (!StringUtils.isBlank(type)) {
                            hql = hql + " and type=:type ";
                        }
                        hql = hql + " order by createTime desc ";
                        q = session.createQuery(hql);
                        if (!StringUtils.isBlank(type)) {
                            q.setString("type", type);
                        }
                        l = q.list();

                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "");
                        map.put("list", l);

                        hql = "from MemberMoney where loginName=:loginName";
                        q = session.createQuery(hql);
                        q.setString("loginName", loginName);
                        List<MemberMoney> ml = q.list();
                        if ((ml != null) && (ml.size() > 0)) {
                            MemberMoney m = ml.get(0);
                            map.put("yue", m.getMoney());
                            map.put("minusLimit", m.getMinusLimit());
                        } else {
                            map.put("yue", Double.valueOf(0.0D));
                            map.put("minusLimit", Const.LIMIT_MONEY);
                        }
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "用户非普通客户");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户查询出错");
                }
                return map;
            }
            map.put("success", Boolean.valueOf(false));
            map.put("message", "type 值错误");
            return map;
        }
        map.put("success", Boolean.valueOf(false));
        map.put("message", "用户名不为空");

        return map;
    }

    public Map<String, Object> updateMoney(String loginName, Long id) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();

            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Deductions d = (Deductions) session.get(Deductions.class, id);
                if (d != null) {
                    if (d.getStatus().intValue() != 0) {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "该充值或扣款记录已经处理");
                    } else if (StringUtils.isBlank(d.getUsername())) {
                        q = null;
                        hql = " from MemberMoney where loginName=:loginName ";
                        q = session.createQuery(hql);
                        q.setString("loginName", loginName);
                        List<MemberMoney> l = q.list();
                        MemberMoney um = null;
                        if ((l != null) && (l.size() > 0)) {
                            um = l.get(0);
                        }
                        if ("1".equals(d.getType())) {
                            if (um == null) {
                                map.put("success", Boolean.valueOf(false));
                                map.put("message", "您的账户无余额");
                            } else if (um.getMinusLimit().doubleValue() < d.getMoney().doubleValue()) {
                                map.put("success", Boolean.valueOf(false));
                                map.put("message", "超过您的限额");
                            } else if (Const.sub(um.getMoney().doubleValue(), d.getMoney().doubleValue()) < 0.0D) {
                                map.put("success", Boolean.valueOf(false));
                                map.put("message", "您的账户余额不足");
                            } else {
                                um.setMoney(Double.valueOf(Const.sub(um.getMoney().doubleValue(), d.getMoney().doubleValue())));
                                d.setStatus(Integer.valueOf(1));
                                um.setUpdateTime(DateUtils.getCurrDate(null));

                                d.setCompleteTime(DateUtils.getCurrDate(null));

                                d.setUsername(loginName);
                                d.setRemind(false);
                                session.update(d);
                                session.update(um);
                                map.put("success", Boolean.valueOf(true));
                                map.put("message", "操作成功");
                            }
                        } else if ("0".equals(d.getType())) {
                            if (um == null) {
                                um = new MemberMoney();
                                um.setLoginName(loginName);
                                um.setUpdateTime(DateUtils.getCurrDate(null));
                                um.setMoney(Double.valueOf(0.0D));
                                um.setMinusLimit(Const.LIMIT_MONEY);

                                session.save(um);
                            }
                            um.setMoney(Double.valueOf(Const.add(um.getMoney().doubleValue(), d.getMoney().doubleValue())));
                            d.setStatus(Integer.valueOf(1));
                            d.setUsername(loginName);
                            um.setUpdateTime(DateUtils.getCurrDate(null));
                            d.setCompleteTime(DateUtils.getCurrDate(null));
                            session.update(d);
                            session.update(um);
                            map.put("success", Boolean.valueOf(true));
                            map.put("message", "");
                        } else {
                            map.put("success", Boolean.valueOf(false));
                            map.put("message", "类型不明确");
                        }
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "该充值或扣款记录不属于该用户");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "该充值或扣款记录不存在");
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token校验失败2");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> queryInfos(String loginName, String type, Integer status) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            if (StringUtils.isBlank(type)) {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "type 值错误");
                return map;
            }
            if ((StringUtils.isBlank(type)) || (type.endsWith("1")) || (type.endsWith("0"))) {
                Query q = null;
                Session session = getCurrentSession();
                String hql = "from Members where loginName=:loginName";
                q = session.createQuery(hql);
                q.setString("loginName", loginName);
                List<Members> ml = q.list();
                if ((ml != null) && (ml.size() == 1)) {
                    Members u = ml.get(0);
                    if (!"0".equals(u.getRoleId())) {
                        hql = "from Members where 1=1 ";
                        if (!StringUtils.isBlank(type)) {
                            hql = hql + " and roleId=:type ";
                        }
                        if (status != null) {
                            hql = hql + " and status=:status ";
                        }
                        hql = hql + " order by id ";
                        q = session.createQuery(hql);
                        if (!StringUtils.isBlank(type)) {
                            q.setString("type", type);
                        }
                        if (status != null) {
                            q.setInteger("status", status.intValue());
                        }
                        List<Members> l = q.list();
                        if (((l != null ? 1 : 0) & (l.size() > 0 ? 1 : 0)) != 0) {
                            for (Members m : l) {
                                m.setPassword(null);
                            }
                        }
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "");
                        map.put("list", l);
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "用户是普通客户");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户查询出错");
                }
                return map;
            }
            map.put("success", Boolean.valueOf(false));
            map.put("message", "type 值错误");
            return map;
        }
        map.put("success", Boolean.valueOf(false));
        map.put("message", "用户名不为空");

        return map;
    }

    public Map<String, Object> updateIgnoreMoney(String loginName, Long id) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            Deductions d = (Deductions) session.get(Deductions.class, id);
            if (d != null) {
                if (d.getStatus().intValue() != 0) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "该充值或扣款记录已经处理");
                } else if (StringUtils.isBlank(d.getUsername())) {
                    d.setStatus(Integer.valueOf(2));
                    d.setUsername(loginName);
                    d.setCompleteTime(DateUtils.getCurrDate(null));
                    session.update(d);
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "成功忽略");
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "该充值或扣款记录不属于该用户");
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "该充值或扣款记录不存在");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名不为空");
        }
        return map;
    }

    public Map<String, Object> queryTypeExs(String type, String username) {
        Map<String, Object> map = new HashMap();
        Session session = getCurrentSession();
        map.put("success", Boolean.valueOf(false));
        map.put("message", "该类型用户不存在");
        map.put("exist", Boolean.valueOf(false));
        String hql = "from Members where roleId=:roleId";
        if (!StringUtils.isBlank(username)) {
            hql = hql + " and loginName=:loginName ";
        }
        Query q = session.createQuery(hql);
        q.setString("roleId", type);
        if (!StringUtils.isBlank(username)) {
            q.setString("loginName", username);
        }
        List l = q.list();
        map.put("list", l);
        if ((l != null) && (l.size() > 0)) {
            map.put("exist", Boolean.valueOf(true));
            map.put("success", Boolean.valueOf(true));
            if ("1".equals(type)) {
                map.put("message", "管理员存在");
            } else if ("2".equals(type)) {
                map.put("message", "操作员存在");
            } else if ("3".equals(type)) {
                map.put("message", "审计员存在");
            } else {
                map.put("message", "普通用户存在");
            }
        }
        if (l.size() == 0) {
            map.put("success", Boolean.valueOf(true));
            map.put("exist", Boolean.valueOf(false));
            map.put("message", "该类型用户不存在");
        }
        return map;
    }

    public Map<String, Object> queryUserDetail(String loginName, String date, String type) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            map.put("success", Boolean.valueOf(true));
            map.put("message", "");
            Query q = null;
            Session session = getCurrentSession();

            String hql = "from Deductions where username=:loginName and status in(1,2)";
            if (!StringUtils.isBlank(date)) {
                hql = hql + " and completeTime like :date ";
            } else {
                hql = hql + " and completeTime >=:sdate and completeTime <:edate ";
            }
            if (!StringUtils.isBlank(type)) {
                hql = hql + " and type=:type ";
            }
            hql = hql + " order by id desc ";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            if (!StringUtils.isBlank(date)) {
                q.setString("date", date + "%");
            } else {
                String sdate = DateUtils.addMonth(-12, "yyyy-MM-dd HH:mm:ss");
                String edate = DateUtils.getCurrDate(null);
                q.setString("sdate", sdate);
                q.setString("edate", edate);
            }
            if (!StringUtils.isBlank(type)) {
                q.setString("type", type);
            }
            List<Deductions> l = q.list();
            if (l != null) {
                map.put("result", l);
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "查询用户帐户出错");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户名不为空");
        }
        return map;
    }

    public Map<String, Object> queryByAdminDetail(String loginName, String username, String date, String type) {
        this.log.debug("参数信息：loginName is " + loginName + " ,username=" + username + ",date is " + date + ",type is " + type);
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if ((u.getRoleId() != null) && (!u.getRoleId().equals("0"))) {
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                    q = null;

                    hql = "from Deductions where  status in(1)";
                    if (!StringUtils.isBlank(username)) {
                        hql = hql + " and username=:username  ";
                    }
                    if (!StringUtils.isBlank(date)) {
                        hql = hql + " and completeTime like :date";
                    } else {
                        hql = hql + " and createTime >=:sdate and createTime <:edate ";
                    }
                    if (!StringUtils.isBlank(type)) {
                        hql = hql + " and type=:type ";
                    }
                    this.log.debug("查询HQL is " + hql);

                    q = session.createQuery(hql);
                    if (!StringUtils.isBlank(username)) {
                        q.setString("username", username);
                    }
                    if (!StringUtils.isBlank(date)) {
                        q.setString("date", date + "%");
                    } else {
                        String sdate = DateUtils.addMonth(-12, "yyyy-MM-dd HH:mm:ss");
                        String edate = DateUtils.getCurrDate(null);
                        q.setString("sdate", sdate);
                        q.setString("edate", edate);
                    }
                    if (!StringUtils.isBlank(type)) {
                        q.setString("type", type);
                    }
                    List<Deductions> l = q.list();
                    if (l != null) {
                        map.put("result", l);
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "查询用户帐户出错");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "token2校验失败");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> queryRecordsAdm(String loginName, String username, String date, String type, String status) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (StringUtils.isBlank(u.getRoleId())) {
                    this.log.debug("该用户角色不存在。。。。。");
                    u.setRoleId("0");
                }
                if (u.getRoleId().equals("0")) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                if (((u.getQx1() != null) && (1 == u.getQx1().intValue())) || (u.getRoleId().equals("1"))) {
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                    q = null;
                    hql = "from Deductions where  1=1 ";
                    if (!StringUtils.isBlank(status)) {
                        hql = hql + " and status=:status  ";
                    }
                    if (!StringUtils.isBlank(username)) {
                        hql = hql + " and username=:username  ";
                    }
                    if (!StringUtils.isBlank(date)) {
                        hql = hql + " and completeTime like :date";
                    } else {
                        hql = hql + " and createTime >=:sdate and createTime <:edate ";
                    }
                    if (!StringUtils.isBlank(type)) {
                        hql = hql + " and type=:type ";
                    }
                    hql = hql + " order by id desc ";
                    q = session.createQuery(hql);
                    if (!StringUtils.isBlank(username)) {
                        q.setString("username", username);
                    }
                    if (!StringUtils.isBlank(date)) {
                        q.setString("date", date + "%");
                    } else {
                        String sdate = DateUtils.addMonth(-12, "yyyy-MM-dd HH:mm:ss");
                        String edate = DateUtils.getCurrDate(null);
                        q.setString("sdate", sdate);
                        q.setString("edate", edate);
                    }
                    if (!StringUtils.isBlank(status)) {
                        q.setString("status", status);
                    }
                    if (!StringUtils.isBlank(type)) {
                        q.setString("type", type);
                    }
                    List<Deductions> l = q.list();
                    if (l != null) {
                        map.put("result", l);
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "查询用户帐户出错");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户无权限");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> updateLimit(String loginName, Double minusLimit) {
        Map<String, Object> map = new HashMap();
        map.put("success", Boolean.valueOf(false));
        if (!StringUtils.isBlank(loginName)) {
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from MemberMoney where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<MemberMoney> l = q.list();
            if (l != null) {
                if (l.size() > 0) {
                    MemberMoney m = l.get(0);
                    m.setMinusLimit(minusLimit);
                    session.update(m);
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "限额设置成功");
                } else {
                    map.put("message", "系统查询出错");
                }
            } else {
                map.put("message", "系统查询出错");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
            return map;
        }
        return map;
    }

    public Map<String, Object> shenjiByAdmin(String loginName, String name, String sdate, String edate, String querydate, String username) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (StringUtils.isBlank(u.getRoleId())) {
                    u.setRoleId("0");
                }
                if (u.getRoleId().equals("0")) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                if (((u.getQx2() != null) && (1 == u.getQx2().intValue())) || (u.getRoleId().equals("1"))) {
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                    hql = "from GymLog where 1=1 ";
                    if (!StringUtils.isBlank(username)) {
                        hql = hql + " and username=:username ";
                    }
                    if (!StringUtils.isBlank(name)) {
                        hql = hql + " and name=:name ";
                    }
                    if (!StringUtils.isBlank(querydate)) {
                        hql = hql + " and createTime like :querydate ";
                    } else {
                        if (!StringUtils.isBlank(sdate)) {
                            hql = hql + " and createTime >=:sdate ";
                        }
                        if (!StringUtils.isBlank(edate)) {
                            hql = hql + " and createTime <= :edate ";
                        }
                    }
                    q = session.createQuery(hql);
                    if (!StringUtils.isBlank(username)) {
                        q.setString("username", username);
                    }
                    if (!StringUtils.isBlank(name)) {
                        q.setString("name", name);
                    }
                    if (!StringUtils.isBlank(querydate)) {
                        q.setString("querydate", querydate + "%");
                    } else {
                        if (!StringUtils.isBlank(sdate)) {
                            q.setString("sdate", sdate);
                        }
                        if (!StringUtils.isBlank(edate)) {
                            q.setString("sdate", edate);
                        }
                    }
                    List<GymLog> l = q.list();
                    map.put("result", l);
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户无权限");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> shezhiByAdmin(String loginName, String username, Integer qx1, Integer qx2, Integer qx3, Integer qx4, Integer qx5) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isBlank(username)) {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "必传参数为空");
            return map;
        }
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (u.getRoleId() == null) {
                    this.log.debug("说明注册用户时候有问题,user is " + u);
                    u.setRoleId("0");
                }
                if (u.getRoleId().equals("0")) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                if (loginName.equals(username)) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "不能设置自己的权限");
                    return map;
                }
                if ((u.getRoleId().equals("1")) || ((u.getQx4() != null) && (1 == u.getQx4().intValue()))) {
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                    q.setString("loginName", username);
                    List<Members> ml2 = q.list();
                    if ((ml2 != null) && (ml2.size() == 1)) {
                        Members u2 = ml2.get(0);
                        if (StringUtils.isBlank(u2.getRoleId())) {
                            this.log.debug("要设置权限的用户角色不存在，默认设置为2");
                            u2.setRoleId("2");
                        }
                        if ("1".equals(u2.getRoleId())) {
                            map.put("success", Boolean.valueOf(false));
                            map.put("message", "您不可以设置管理员");
                            return map;
                        }
                        if (!"0".equals(u2.getRoleId())) {
                            if ("3".equals(u2.getRoleId())) {
                                u2.setQx2(qx2);
                            } else {
                                u2.setQx1(qx1);
                                u2.setQx2(qx2);
                                u2.setQx3(qx3);
                                u2.setQx4(qx4);
                                u2.setQx5(qx5);
                            }
                            u2.setLocked(Boolean.valueOf(false));
                            u2.setPassed(true);
                            session.update(u2);
                            map.put("success", Boolean.valueOf(true));
                            map.put("message", "设置权限成功");
                        } else {
                            map.put("success", Boolean.valueOf(false));
                            map.put("message", "不可以设置给普通用户设置");
                        }
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "要设置权限的用户不存在");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "没有权限");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> updateEditByAdmin(String loginName, Long id, String username, String type, Double money) {
        this.log.debug("editByAdmin  loginName is " + loginName + ",id is " + id + ",username is " + username + ",type is " + type + ",money is " + money);

        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (StringUtils.isBlank(u.getRoleId())) {
                    this.log.debug("用户角色不存在。。。");
                    u.setRoleId("0");
                }
                if (u.getRoleId().equals("0")) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                if (((u.getQx3() != null) && (1 == u.getQx3().intValue())) || (u.getRoleId().equals("1"))) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "修改错误，该信息不是完成状态");

                    Deductions d = (Deductions) session.get(Deductions.class, id);
                    if ((d != null) && (1 == d.getStatus().intValue())) {
                        map.put("message", "");
                        d.setCompleteTime(DateUtils.getCurrDate(null));

                        String yu = d.getUsername();
                        String s = "from  MemberMoney where loginName=:loginName";
                        q = session.createQuery(s);
                        q.setString("loginName", yu);
                        List mm = q.list();
                        if ((mm != null) && (1 == mm.size())) {
                            MemberMoney um = (MemberMoney) mm.get(0);
                            if ((StringUtils.isBlank(username)) || (username.equals(d.getUsername()))) {
                                if ("1".equals(d.getType())) {
                                    if ("1".equals(type)) {
                                        this.log.debug("同一个用户，原来缴费，现在也缴费");
                                        this.log.debug("当前帐户：" + um.getMoney() + "原来缴费：" + d.getMoney() + ",现在缴费：" + money);
                                        if (um.getMinusLimit().doubleValue() < money.doubleValue()) {
                                            this.log.debug("缴费大于限额");
                                            map.put("message", "缴费大于限额");
                                            return map;
                                        }
                                        if (Const.add(um.getMoney().doubleValue(), d.getMoney().doubleValue()) > money.doubleValue()) {
                                            um.setMoney(Double.valueOf(Const.sub(Const.add(um.getMoney().doubleValue(), d.getMoney().doubleValue()), money.doubleValue())));
                                            d.setMoney(money);
                                            d.setType(type);

                                            session.update(d);
                                            um.setUpdateTime(DateUtils.getCurrDate(null));
                                            session.update(um);

                                            map.put("success", Boolean.valueOf(true));
                                            this.log.debug("成功");
                                            return map;
                                        }
                                        this.log.debug("失败");
                                        map.put("message", um.getLoginName() + "余额不足");
                                    } else {
                                        this.log.debug("同一个用户，原来缴费，现在也充值");
                                        this.log.debug("当前帐户：" + um.getMoney() + "原来缴费：" + d.getMoney() + ",现在充值：" + money);

                                        um.setMoney(Double.valueOf(Const.add(Const.add(um.getMoney().doubleValue(), d.getMoney().doubleValue()), money.doubleValue())));

                                        d.setMoney(money);
                                        d.setType(type);
                                        session.update(d);
                                        um.setUpdateTime(DateUtils.getCurrDate(null));
                                        session.update(um);

                                        map.put("success", Boolean.valueOf(true));
                                        this.log.debug("必定成功");
                                        return map;
                                    }
                                } else if ("1".equals(type)) {
                                    this.log.debug("同一个用户，原来充值，现在也缴费");
                                    this.log.debug("当前帐户：" + um.getMoney() + "原来充值：" + d.getMoney() + ",现在缴费：" + money);
                                    if (um.getMinusLimit().doubleValue() < money.doubleValue()) {
                                        this.log.debug("缴费大于限额");
                                        map.put("message", "缴费大于限额");
                                        return map;
                                    }
                                    if (um.getMoney().doubleValue() > Const.add(d.getMoney().doubleValue(), money.doubleValue())) {
                                        um.setMoney(Double.valueOf(Const.sub(um.getMoney().doubleValue(), Const.add(d.getMoney().doubleValue(), money.doubleValue()))));

                                        d.setMoney(money);
                                        d.setType(type);
                                        session.update(d);
                                        um.setUpdateTime(DateUtils.getCurrDate(null));

                                        map.put("success", Boolean.valueOf(true));
                                        this.log.debug("成功");
                                        return map;
                                    }
                                    this.log.debug("失败");
                                    map.put("message", um.getLoginName() + "余额不足");
                                } else {
                                    this.log.debug("同一个用户，原来充值，现在也充值");
                                    this.log.debug("当前帐户：" + um.getMoney() + "原来充值：" + d.getMoney() + ",现在充值：" + money);
                                    if (Const.add(um.getMoney().doubleValue(), money.doubleValue()) > d.getMoney().doubleValue()) {
                                        um.setMoney(Double.valueOf(Const.sub(Const.add(um.getMoney().doubleValue(), money.doubleValue()), d.getMoney().doubleValue())));

                                        d.setMoney(money);
                                        d.setType(type);
                                        session.update(d);
                                        um.setUpdateTime(DateUtils.getCurrDate(null));

                                        map.put("success", Boolean.valueOf(true));
                                        this.log.debug("成功");
                                        return map;
                                    }
                                    this.log.debug("失败");
                                    map.put("message", "余额不足");
                                }
                            } else {
                                hql = "from MemberMoney where loginName=:loginName";
                                q = session.createQuery(hql);
                                q.setString("loginName", username);
                                List<MemberMoney> nowList = q.list();
                                if ((nowList == null) || (nowList.size() == 0)) {
                                    map.put("message", username + "帐户不存在");
                                    return map;
                                }
                                MemberMoney nowusermoney = nowList.get(0);
                                if ("1".equals(d.getType())) {
                                    if ("1".equals(type)) {
                                        this.log.debug("不同用户，原来缴费，现在也缴费");
                                        this.log.debug("当前帐户：" + um.getMoney() + "原来缴费：" + d.getMoney() + ",现在缴费：" + money);
                                        if (um.getMinusLimit().doubleValue() < money.doubleValue()) {
                                            this.log.debug("缴费大于限额");
                                            map.put("message", "缴费大于限额");
                                            return map;
                                        }
                                        if (nowusermoney.getMoney().doubleValue() >= money.doubleValue()) {
                                            nowusermoney.setMoney(Double.valueOf(Const.sub(nowusermoney.getMoney().doubleValue(), money.doubleValue())));
                                            um.setMoney(Double.valueOf(Const.add(d.getMoney().doubleValue(), um.getMoney().doubleValue())));
                                            session.update(nowusermoney);
                                            um.setUpdateTime(DateUtils.getCurrDate(null));
                                            session.update(um);

                                            d.setMoney(money);
                                            d.setType(type);
                                            d.setUsername(username);
                                            session.update(d);

                                            map.put("success", Boolean.valueOf(true));
                                            this.log.debug("ok");
                                            return map;
                                        }
                                        map.put("message", username + "余额不足");
                                        this.log.debug("失败");
                                    } else {
                                        this.log.debug("不同用户，原来缴费，现在充值");
                                        this.log.debug("当前帐户：" + um.getMoney() + "原来缴费：" + d.getMoney() + ",现在充值：" + money);
                                        nowusermoney.setMoney(Double.valueOf(Const.add(nowusermoney.getMoney().doubleValue(), money.doubleValue())));
                                        um.setMoney(Double.valueOf(Const.add(d.getMoney().doubleValue(), um.getMoney().doubleValue())));
                                        session.update(nowusermoney);

                                        um.setUpdateTime(DateUtils.getCurrDate(null));
                                        session.update(um);
                                        d.setMoney(money);
                                        d.setType(type);
                                        d.setUsername(username);
                                        session.update(d);

                                        map.put("success", Boolean.valueOf(true));
                                        this.log.debug("ok");
                                        return map;
                                    }
                                } else if ("0".equals(d.getType())) {
                                    this.log.debug("不同用户，原来充值，现在？？");
                                    this.log.debug("当前帐户：" + um.getMoney() + "原来充值：" + d.getMoney() + ",现在？？：" + money);
                                    if (d.getMoney().doubleValue() > um.getMoney().doubleValue()) {
                                        map.put("message", um.getLoginName() + "余额不足");
                                        this.log.debug("余额不足失败");
                                    } else {
                                        this.log.debug("不同用户，原来充值，现在缴费");
                                        this.log.debug("当前帐户：" + um.getMoney() + "原来充值：" + d.getMoney() + ",现在缴费：" + money);
                                        if ("1".equals(type)) {
                                            if (um.getMinusLimit().doubleValue() < money.doubleValue()) {
                                                this.log.debug("缴费大于限额");
                                                map.put("message", "缴费大于限额");
                                                return map;
                                            }
                                            if (nowusermoney.getMoney().doubleValue() >= money.doubleValue()) {
                                                nowusermoney.setMoney(Double.valueOf(Const.sub(nowusermoney.getMoney().doubleValue(), money.doubleValue())));
                                                um.setMoney(Double.valueOf(Const.sub(um.getMoney().doubleValue(), d.getMoney().doubleValue())));
                                                session.update(nowusermoney);
                                                um.setUpdateTime(DateUtils.getCurrDate(null));
                                                session.update(um);
                                                d.setMoney(money);
                                                d.setType(type);
                                                d.setUsername(username);
                                                session.update(d);

                                                map.put("success", Boolean.valueOf(true));
                                                this.log.debug("成功");
                                                return map;
                                            }
                                            map.put("message", username + "余额不足");
                                        } else {
                                            this.log.debug("不同用户，原来充值，现在充值");
                                            this.log.debug("当前帐户：" + um.getMoney() + "原来充值：" + d.getMoney() + ",现在充值：" + money);
                                            nowusermoney.setMoney(Double.valueOf(Const.add(nowusermoney.getMoney().doubleValue(), money.doubleValue())));
                                            um.setMoney(Double.valueOf(Const.sub(um.getMoney().doubleValue(), d.getMoney().doubleValue())));
                                            session.update(nowusermoney);
                                            um.setUpdateTime(DateUtils.getCurrDate(null));
                                            session.update(um);
                                            d.setMoney(money);
                                            d.setType(type);
                                            d.setUsername(username);
                                            session.update(d);
                                            this.log.debug("成功");

                                            map.put("success", Boolean.valueOf(true));
                                            return map;
                                        }
                                    }
                                }
                            }
                        } else {
                            map.put("message", "ERROR帐户异常");
                        }
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "不是管理员或操作员");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败,或传值失败");
        }
        return map;
    }

    public void saveLog(GymLog glog) {
        Session session = getCurrentSession();
        session.save(glog);
    }

    public Map<String, Object> updateIgnoreByAdmin(String loginName, Long id) {
        Map<String, Object> map = new HashMap();
        if (id == null) {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "必传参数不能为空");
            return map;
        }
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() > 0)) {
                Members u = ml.get(0);
                if ("0".equals(u.getRoleId())) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                if (("1".equals(u.getRoleId())) || ((u.getQx1() != null) && (1 == u.getQx1().intValue()))) {
                    Deductions d = (Deductions) session.get(Deductions.class, id);
                    if (d.getStatus().intValue() != 0) {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "该充值或扣款记录已经处理");
                    } else {
                        d.setStatus(Integer.valueOf(2));
                        d.setUsername(null);
                        d.setCompleteTime(DateUtils.getCurrDate(null));
                        session.update(d);
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "操作成功");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户无权限");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "用户校验失败");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> queryLockedUsers(String loginName, String type) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (StringUtils.isBlank(u.getRoleId())) {
                    this.log.debug("ERROR,用户角色为空。。");
                    u.setRoleId("0");
                }
                if ((!StringUtils.isBlank(type)) && ("0".equals(type))) {
                    if (((u.getQx5() == null) || (1 != u.getQx5().intValue())) && (!"1".equals(u.getRoleId()))) {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "用户无权限");
                        return map;
                    }
                }
                hql = "from Members  where passed=:passed  ";
                if (!StringUtils.isBlank(type)) {
                    hql = hql + " and roleId=:type ";
                } else {
                    hql = hql + " and (roleId=:type2 or  roleId=:type3) ";
                }
                hql = hql + "  order by id desc ";
                q = session.createQuery(hql);

                q.setBoolean("passed", false);
                if (!StringUtils.isBlank(type)) {
                    q.setString("type", type);
                } else {
                    q.setString("type2", "2");
                    q.setString("type3", "3");
                }
                List<Members> lockeusers = q.list();
                map.put("success", Boolean.valueOf(true));
                map.put("list", lockeusers);
                map.put("message", "");
                if ((lockeusers != null) && (lockeusers.size() == 0)) {
                    map.put("message", "没有查询到锁定用户");
                }
                return map;
            }
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token2校验失败");
            return map;
        }
        map.put("success", Boolean.valueOf(false));
        map.put("message", "token校验失败,或传值失败");

        return map;
    }

    public Map<String, Object> updateUnLocked(String loginName, String username, Long id) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();

            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (StringUtils.isBlank(u.getRoleId())) {
                    this.log.debug("ERROR用户角色为空。。。");
                    u.setRoleId("0");
                }
                if (u.getRoleId().equals("0")) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                hql = " from  Members   where 1=1 ";
                if (id != null) {
                    hql = hql + " and id=:id ";
                } else if (!StringUtils.isBlank(username)) {
                    hql = hql + " and loginName=:loginName ";
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "请选择要激活的用户");
                    return map;
                }
                q = session.createQuery(hql);
                if (id != null) {
                    q.setLong("id", id.longValue());
                } else if (!StringUtils.isBlank(username)) {
                    q.setString("loginName", username);
                }
                List<Members> targetList = q.list();
                if ((targetList != null) && (targetList.size() > 0)) {
                    Members taru = targetList.get(0);
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "该用户无权限");
                    if ((taru.getRoleId() == null) || ("0".equals(taru.getRoleId()))) {
                        if (((u.getQx5() != null) && (1 == u.getQx5().intValue())) || (u.getRoleId().equals("1"))) {
                            taru.setLocked(Boolean.valueOf(false));
                            taru.setPassed(true);

                            session.update(taru);
                            map.put("success", Boolean.valueOf(true));
                            map.put("message", "操作成功");
                        }
                    } else if (((u.getQx4() != null) && (1 == u.getQx4().intValue())) || (u.getRoleId().equals("1"))) {
                        taru.setLocked(Boolean.valueOf(false));
                        taru.setLocked(Boolean.valueOf(false));
                        taru.setPassed(true);
                        session.update(taru);
                        map.put("success", Boolean.valueOf(true));
                        map.put("message", "操作成功");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "请选择要激活的用户");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败,或传值失败");
        }
        return map;
    }

    public Map<String, Object> queryMembers(String loginName, String[] types, Boolean locked) {
        Map<String, Object> map = new HashMap();
        if ((types != null) && (types.length > 0)) {
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (!"0".equals(u.getRoleId())) {
                    hql = "from Members where 1=1 ";
                    String types2 = "";
                    for (int i = 0; i < types.length; i++) {
                        types2 = types2 + types[i] + ",";
                    }
                    types2 = types2 + "-1";
                    if (!StringUtils.isBlank(types2)) {
                        hql = hql + " and roleId in  (" + types2 + ")";
                    }
                    if (locked != null) {
                        hql = hql + " and locked=:locked ";
                    }
                    hql = hql + " order by id desc ";
                    q = session.createQuery(hql);
                    if (locked != null) {
                        q.setBoolean("locked", locked.booleanValue());
                    }
                    List<Members> l = q.list();
                    if (((l != null ? 1 : 0) & (l.size() > 0 ? 1 : 0)) != 0) {
                        for (Members m : l) {
                            m.setPassword(null);
                        }
                    }
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                    map.put("list", l);
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户是普通客户");
                }
                return map;
            }
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户不存在");
            return map;
        }
        map.put("success", Boolean.valueOf(false));
        map.put("message", "用户名不为空");

        return map;
    }

    public Map<String, Object> addBackUpDb(String loginName) {
        Map<String, Object> map = new HashMap();
        Query q = null;
        Session session = getCurrentSession();
        String hql = "from Members where loginName=:loginName";
        q = session.createQuery(hql);
        q.setString("loginName", loginName);
        List<Members> ml = q.list();
        if ((ml != null) && (ml.size() == 1)) {
            Members u = ml.get(0);
            if (StringUtils.isBlank(u.getRoleId())) {
                this.log.debug("用户角色不能为空。。。。");
                u.setRoleId("0");
            }
            if ("1".equals(u.getRoleId())) {
                map.put("success", Boolean.valueOf(true));
                map.put("message", "开始执行备份");
                try {
                    String s = "gym_" + DateUtils.getCurrDate("yyyy-MM-ddHHmmss");
                    backup(s + ".sql");
                    BackDbHis m = new BackDbHis();
                    m.setCreateTime(DateUtils.getCurrDate("yyyy-MM-dd HH:mm:ss"));
                    m.setName(ConfigUtil.getProperty("sqlPath") + s + ".sql");

                    m.setUsername(loginName);
                    session.save(m);
                    map.put("message", "备份完成");
                } catch (IOException e) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "备份异常");
                    e.printStackTrace();
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "只有管理员有该权限");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户不存在");
        }
        return map;
    }

    public Map<String, Object> queryBackHis(String loginName) {
        Map<String, Object> map = new HashMap();
        Query q = null;
        Session session = getCurrentSession();
        String hql = "from Members where loginName=:loginName";
        q = session.createQuery(hql);
        q.setString("loginName", loginName);
        List<Members> ml = q.list();
        if ((ml != null) && (ml.size() == 1)) {
            Members u = ml.get(0);
            if (!"0".equals(u.getRoleId())) {
                map.put("success", Boolean.valueOf(true));
                map.put("message", "");
                hql = "from BackDbHis order by id desc ";
                q = session.createQuery(hql);
                q.setMaxResults(1000);
                List<?> l = q.list();
                map.put("list", l);
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "用户是普通客户");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "用户不存在");
        }
        return map;
    }

    public void backup(String sql)
            throws IOException {
        String username = ConfigUtil.getProperty("username");
        String password = ConfigUtil.getProperty("password");
        String mysqlpaths = ConfigUtil.getProperty("mysqlpath");
        String address = ConfigUtil.getProperty("address");
        String databaseName = ConfigUtil.getProperty("databaseName");
        String tableName = ConfigUtil.getProperty("backTable");
        String sqlpath = ConfigUtil.getProperty("sqlPath");

        File backupath = new File(sqlpath);
        if (!backupath.exists()) {
            backupath.mkdir();
        }
        StringBuffer sb = new StringBuffer();

        sb.append(mysqlpaths);
        sb.append("mysqldump ");
        sb.append("--opt ");
        sb.append("-h ");
        sb.append(address);
        sb.append(" ");
        sb.append("--user=");
        sb.append(username);
        sb.append(" ");
        sb.append("--password=");
        sb.append(password);
        sb.append(" ");
        sb.append("--lock-all-tables=true ");
        sb.append("--result-file=");
        sb.append(sqlpath);
        sb.append(sql);
        sb.append(" ");
        sb.append("--default-character-set=utf8 ");
        sb.append(databaseName);

        System.out.println(sb);
        Runtime cmd = Runtime.getRuntime();
        Process p = cmd.exec(sb.toString());
    }

    public Map<String, Object> queryOneUser(String loginName) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Query q = null;
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<?> l = q.list();
            if ((l != null) && (l.size() == 1)) {
                map.put("success", Boolean.valueOf(true));
                map.put("message", "");
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "用户名不存在");
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }

    public Map<String, Object> updateAfterQuery(String loginName) {
        Map<String, Object> map = new HashMap();
        if (!StringUtils.isBlank(loginName)) {
            Session session = getCurrentSession();
            String hql = "from Members where loginName=:loginName";
            Query q = session.createQuery(hql);
            q.setString("loginName", loginName);
            List<Members> ml = q.list();
            if ((ml != null) && (ml.size() == 1)) {
                Members u = ml.get(0);
                if (StringUtils.isBlank(u.getRoleId())) {
                    this.log.debug("该用户角色不存在。。。。。");
                    u.setRoleId("0");
                }
                if (u.getRoleId().equals("0")) {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "普通用户无权限");
                    return map;
                }
                if (((u.getQx1() != null) && (1 == u.getQx1().intValue())) || (u.getRoleId().equals("1"))) {
                    map.put("success", Boolean.valueOf(true));
                    map.put("message", "");
                    hql = "from Deductions where  exeUser=:exeUser and completeTime>:completeTime  and status=:status and  remind=:remind  ";
                    q = session.createQuery(hql);
                    q.setString("status", "1");
                    q.setString("exeUser", loginName);
                    q.setString("completeTime", DateUtils.getDateStringPlusMinus(-10));
                    q.setBoolean("remind", false);

                    List<Deductions> l = q.list();
                    if (l != null) {
                        if (l.size() > 0) {
                            Deductions m = l.get(0);
                            m.setRemind(true);
                            session.update(m);
                            map.put("model", l.get(0));
                        } else {
                            map.put("model", null);

                            hql = "from Deductions where  exeUser=:exeUser  and status=:status   ";
                            q = session.createQuery(hql);
                            q.setString("status", "0");
                            q.setString("exeUser", loginName);
                            List<Deductions> oll = q.list();
                            if ((oll != null) && (oll.size() > 0)) {
                                map.put("success", Boolean.valueOf(true));
                                map.put("message", "还有用户没有处理的数据");
                            } else {
                                map.put("success", Boolean.valueOf(false));
                                map.put("message", "没有需要处理的数据");
                            }
                        }
                    } else {
                        map.put("success", Boolean.valueOf(false));
                        map.put("message", "没有查询到数据");
                    }
                } else {
                    map.put("success", Boolean.valueOf(false));
                    map.put("message", "用户无权限");
                    return map;
                }
            } else {
                map.put("success", Boolean.valueOf(false));
                map.put("message", "token2校验失败");
                return map;
            }
        } else {
            map.put("success", Boolean.valueOf(false));
            map.put("message", "token校验失败");
        }
        return map;
    }
}
