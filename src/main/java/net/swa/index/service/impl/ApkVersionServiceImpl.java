package net.swa.index.service.impl;

import net.swa.index.beans.entity.ApkVersion;
import net.swa.index.service.ApkVersionService;
import net.swa.system.dao.HibernateDaoSupport;
import net.swa.util.JsonResult;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("apkVersionService")
public class ApkVersionServiceImpl
        extends HibernateDaoSupport
        implements ApkVersionService {
    public Map<String, Object> add(ApkVersion model) {
        Map<String, Object> map = new HashMap();

        Session session = getCurrentSession();
        session.save(model);
        map.put("success", Boolean.valueOf(true));
        map.put("message", "true");
        return map;
    }

    public ApkVersion queryById(Long id) {
        return null;
    }

    public ApkVersion queryLastVersion() {
        ApkVersion m = null;
        Session session = getCurrentSession();
        String hql = "from ApkVersion order by id desc";
        Query query = session.createQuery(hql);
        query.setMaxResults(10);
        List<ApkVersion> l = query.list();
        if ((l != null) && (l.size() > 0)) {
            m = l.get(0);
        }
        return m;
    }

    public JsonResult<ApkVersion> queryPage(String name, int currentPage, int pageSize, String orderBy, String orderType) {
        return null;
    }

    public Map<String, Object> update(ApkVersion model) {
        Map<String, Object> map = new HashMap();

        Session session = getCurrentSession();
        session.update(model);
        map.put("success", Boolean.valueOf(true));
        map.put("message", "true");
        return map;
    }

    public Map<String, Object> save(ApkVersion model) {
        Map<String, Object> map = new HashMap();

        Session session = getCurrentSession();
        session.save(model);
        map.put("success", Boolean.valueOf(true));
        map.put("message", "true");
        return map;
    }

    public ApkVersion queryLastAdmVersion(boolean f) {
        ApkVersion m = null;
        Session session = getCurrentSession();
        String hql = "from ApkVersion where admin=:admin order by id desc";
        Query query = session.createQuery(hql);
        query.setBoolean("admin", f);
        query.setMaxResults(10);
        List<ApkVersion> l = query.list();
        if ((l != null) && (l.size() > 0)) {
            m = l.get(0);
        }
        return m;
    }
}
