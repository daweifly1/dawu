package net.swa.system.service.impl;

import net.swa.system.dao.HibernateDaoSupport;
import net.swa.system.service.ICommonService;
import net.swa.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service("commonService")
public class CommonServiceImpl
        extends HibernateDaoSupport
        implements ICommonService {
    @Transactional
    public <T> JsonResult<T> search(String[] attrNames, Object[] attrValues, String[] operators, Class<T> type, int currentPage, int pageSize, String orderBy, String orderType)
            throws Exception {
        JsonResult<T> json = new JsonResult();
        List<T> list = new ArrayList();
        StringBuilder hql = new StringBuilder();
        hql.append("from " + type.getSimpleName() + " where 1=1 ");
        for (int i = 0; i < attrNames.length; i++) {
            String operator = operators[i];
            if (!StringUtils.isEmpty(operator)) {
                operator = operator.replace("'", "");
                String val = attrValues[i] == null ? "" : attrValues[i]
                        .toString();
                if (val.indexOf("||") > 0) {
                    String[] vals = val.split("\\|\\|");
                    hql.append(" and (");
                    for (int j = 0; j < vals.length; j++) {
                        hql.append((j == 0 ? "" : " or ") + attrNames[i] +
                                operator + " :param" + i + j);
                    }
                    hql.append(") ");
                } else if ((attrValues[i] != null) &&
                        (!StringUtils.isBlank(attrValues[i].toString()))) {
                    hql.append(" and " + attrNames[i] + " " + operator +
                            " :param" + i);
                }
            }
        }
        if (!StringUtils.isEmpty(orderBy)) {
            hql.append(" order by " + orderBy + " " + orderType);
        }
        Query query = getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < attrValues.length; i++) {
            String val = attrValues[i] == null ? "" : attrValues[i].toString();
            if (val.indexOf("||") > 0) {
                String[] vals = val.split("\\|\\|");
                for (int j = 0; j < vals.length; j++) {
                    query.setString("param" + i + j, vals[j]);
                }
            } else if ((attrValues[i] != null) &&
                    (!StringUtils.isBlank(attrValues[i].toString()))) {
                String str = attrValues[i].toString();
                if ("like".equalsIgnoreCase(operators[i])) {
                    str = "%" + str.trim() + "%";
                }
                query.setString("param" + i, str);
            }
        }
        if (pageSize > 0) {
            int from = (currentPage - 1) * pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
        }
        list = query.list();

        query = getCurrentSession().createQuery("select count(*) " + hql);
        for (int i = 0; i < attrValues.length; i++) {
            String val = attrValues[i] == null ? "" : attrValues[i].toString();
            if (val.indexOf("||") > 0) {
                String[] vals = val.split("\\|\\|");
                for (int j = 0; j < vals.length; j++) {
                    query.setString("param" + i + j, vals[j]);
                }
            } else if ((attrValues[i] != null) &&
                    (!StringUtils.isBlank(attrValues[i].toString()))) {
                String str = attrValues[i].toString();
                if ("like".equalsIgnoreCase(operators[i])) {
                    str = "%" + str.trim() + "%";
                }
                query.setString("param" + i, str);
            }
        }
        if (pageSize > 0) {
            int totalCount = ((Number) query.iterate().next()).intValue();
            int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :
                    totalCount / pageSize + 1;
            json.setTotalSize(totalCount);
            json.setTotalPage(totalPage);
            json.setPageSize(pageSize);
            json.setCurrentPage(currentPage);
        }
        json.setResult(list);

        return json;
    }

    @Transactional
    public <T> T findByAttribute(Class<T> type, String attrName, Object val) {
        Query query = getCurrentSession()
                .createQuery(
                        "from " + type.getSimpleName() + " where " + attrName +
                                "=:val");
        query.setString("val", val.toString());
        List<T> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Transactional
    public <T> List<T> search(String attr, Object value, Class<T> type, Integer count, String orderBy, String orderType) {
        List<T> list = new ArrayList();
        String hql = "from " + type.getSimpleName();
        if (attr != null) {
            if (value != null) {
                hql = hql + " where " + attr + "=:param1";
            } else {
                hql = hql + " where " + attr + " is null";
            }
        }
        if (!StringUtils.isBlank(orderBy)) {
            hql = hql + " order by " + orderBy;
            if (!StringUtils.isBlank(orderType)) {
                hql = hql + "  " + orderType;
            }
        }
        Query query = getCurrentSession().createQuery(hql);
        if ((attr != null) && (value != null)) {
            query.setString("param1", value.toString());
        }
        if (count != null) {
            query.setMaxResults(count.intValue());
        }
        list = query.list();
        return list;
    }

    @Transactional
    public boolean commonUpdateStatus(String type, Long[] ids, int status)
            throws Exception {
        for (int i = 0; i < ids.length; i++) {
            Query query = getCurrentSession().createQuery(
                    "update " + type + " set status=:state where id=:id");
            query.setInteger("state", status);
            query.setLong("id", ids[i].longValue());
            query.executeUpdate();
        }
        return true;
    }

    @Transactional
    public boolean commonDelete(String type, Long... ids)
            throws Exception {
        for (int i = 0; i < ids.length; i++) {
            Query query = getCurrentSession().createQuery(
                    "from " + type + " where id=:id");
            query.setLong("id", ids[i].longValue());
            Object obj = query.uniqueResult();
            if (obj != null) {
                getCurrentSession().delete(obj);
            }
        }
        return true;
    }

    @Transactional
    public boolean commonDelHisStatus(String type, String stateName, Long[] ids, String status)
            throws Exception {
        for (int i = 0; i < ids.length; i++) {
            Query query = getCurrentSession().createQuery(
                    "update " + type + " set " + stateName +
                            "=:state where id=:id");
            query.setString("state", status);
            query.setLong("id", ids[i].longValue());
            query.executeUpdate();
        }
        return true;
    }

    @Transactional
    public void commonAdd(Object obj) {
        Long id = (Long) getCurrentSession().save(obj);
        Class<? extends Object> clz = obj.getClass();
        Method[] methods = clz.getDeclaredMethods();
        Method[] arrayOfMethod1;
        int j = (arrayOfMethod1 = methods).length;
        for (int i = 0; i < j; i++) {
            Method method = arrayOfMethod1[i];
            if (method.isAnnotationPresent(Id.class)) {
                try {
                    Class<?> returnType = method.getReturnType();
                    Method setMethod = clz.getDeclaredMethod(
                            "s" +
                                    method.getName().substring(1
                                    ), returnType);
                    setMethod.invoke(obj, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Transactional
    public <T> T commonFind(Class<T> type, long id) {
        T t = (T) getCurrentSession().get(type, Long.valueOf(id));
        return t;
    }

    @Transactional
    public void commonUpdate(Object obj)
            throws Exception {
        getCurrentSession().update(obj);
    }

    @Transactional
    public <T> List<T> search(String attr, Object value, Class<T> type) {


        List<T> list = new ArrayList();
        String hql = "from " + type.getSimpleName();
        if (attr != null) {
            hql = hql + " where " + attr + "=:param1";
        }
        Query query = getCurrentSession().createQuery(hql);
        if (attr != null) {
            query.setParameter("param1", value.toString());
        }
        list = query.list();
        return list;
    }

    @Transactional
    public <T> List<T> search(Class<T> type, String[] attrNames, Object[] attrValues) {
        List<T> list = new ArrayList();
        StringBuilder hql = new StringBuilder();
        hql.append("from " + type.getSimpleName() + " where 1=1 ");
        for (int i = 0; i < attrNames.length; i++) {
            if (attrNames[i] != null) {
                String val = attrValues[i] == null ? "" : attrValues[i]
                        .toString();
                if (val.indexOf("||") > 0) {
                    String[] vals = val.split("\\|\\|");
                    hql.append(" and (");
                    for (int j = 0; j < vals.length; j++) {
                        hql.append((j == 0 ? "" : " or ") + attrNames[i] +
                                "= :param" + i + j);
                    }
                    hql.append(") ");
                } else if (("title".equals(attrNames[i])) ||
                        (attrNames[i].contains(".title")) ||
                        ("realName".equals(attrNames[i]))) {
                    hql.append(" and " + attrNames[i] + " like :param" + i);
                } else if (attrValues[i] != null) {
                    if (!StringUtils.isBlank(attrValues[i].toString())) {
                        hql.append(" and " + attrNames[i] + " = :param" + i);
                    }
                }
            }
        }
        Query query = getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < attrValues.length; i++) {
            if (attrNames[i] != null) {
                String val = attrValues[i] == null ? "" : attrValues[i]
                        .toString();
                if (val.indexOf("||") > 0) {
                    String[] vals = val.split("\\|\\|");
                    for (int j = 0; j < vals.length; j++) {
                        query.setString("param" + i + j, vals[j]);
                    }
                } else if (("title".equals(attrNames[i])) ||
                        (attrNames[i].contains(".title")) ||
                        ("realName".equals(attrNames[i]))) {
                    query.setString("param" + i, "%" + attrValues[i] + "%");
                } else if (attrValues[i] != null) {
                    if (!StringUtils.isBlank(attrValues[i].toString())) {
                        query.setString("param" + i,
                                attrValues[i].toString());
                    }
                }
            }
        }
        list = query.list();
        return list;
    }

    @Transactional
    public <T> JsonResult<T> search(String[] attrNames, Object[] attrValues, Class<T> type, int currentPage, int pageSize, String orderBy, String orderType)
            throws Exception {
        JsonResult<T> json = new JsonResult();
        List<T> list = new ArrayList();
        StringBuilder hql = new StringBuilder();
        hql.append("from " + type.getSimpleName() + " where 1=1 ");
        for (int i = 0; i < attrNames.length; i++) {
            String val = attrValues[i] == null ? "" : attrValues[i].toString();
            if (val.indexOf("||") > 0) {
                String[] vals = val.split("\\|\\|");
                hql.append(" and (");
                for (int j = 0; j < vals.length; j++) {
                    hql.append((j == 0 ? "" : " or ") + attrNames[i] +
                            "= :param" + i + j);
                }
                hql.append(") ");
            } else if (("title".equals(attrNames[i])) ||
                    (attrNames[i].contains(".title")) ||
                    (attrNames[i].contains("_name")) ||
                    (attrNames[i].contains("address")) ||
                    (attrNames[i].contains("_num")) ||
                    (attrNames[i].contains("_custom"))) {
                hql.append(" and " + attrNames[i] + " like :param" + i);
            } else if ((attrValues[i] != null) &&
                    (!StringUtils.isBlank(attrValues[i].toString()))) {
                hql.append(" and " + attrNames[i] + " = :param" + i);
            }
        }
        if (!StringUtils.isEmpty(orderBy)) {
            hql.append(" order by " + orderBy + " " + orderType);
        }
        System.out.println(hql.toString());
        Query query = getCurrentSession().createQuery(hql.toString());
        for (int i = 0; i < attrValues.length; i++) {
            String val = attrValues[i] == null ? "" : attrValues[i].toString();
            if (val.indexOf("||") > 0) {
                String[] vals = val.split("\\|\\|");
                for (int j = 0; j < vals.length; j++) {
                    query.setString("param" + i + j, vals[j]);
                }
            } else if (("title".equals(attrNames[i])) ||
                    (attrNames[i].contains(".title")) ||
                    (attrNames[i].contains("_name")) ||
                    (attrNames[i].contains("address")) ||
                    (attrNames[i].contains("_num")) ||
                    (attrNames[i].contains("_custom"))) {
                query.setString("param" + i, "%" + attrValues[i] + "%");
            } else if ((attrValues[i] != null) &&
                    (!StringUtils.isBlank(attrValues[i].toString()))) {
                query.setString("param" + i, attrValues[i].toString());
            }
        }
        if (pageSize > 0) {
            int from = (currentPage - 1) * pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
        }
        list = query.list();
        if (pageSize > 0) {
            query = getCurrentSession().createQuery("select count(*) " + hql);
            for (int i = 0; i < attrValues.length; i++) {
                String val = attrValues[i] == null ? "" : attrValues[i]
                        .toString();
                if (val.indexOf("||") > 0) {
                    String[] vals = val.split("\\|\\|");
                    for (int j = 0; j < vals.length; j++) {
                        query.setString("param" + i + j, vals[j]);
                    }
                } else if (("title".equals(attrNames[i])) ||
                        (attrNames[i].contains(".title")) ||
                        (attrNames[i].contains("_name")) ||
                        (attrNames[i].contains("address")) ||
                        (attrNames[i].contains("_num")) ||
                        (attrNames[i].contains("_custom"))) {
                    query.setString("param" + i, "%" + attrValues[i] + "%");
                } else if (attrValues[i] != null) {
                    if (!StringUtils.isBlank(attrValues[i].toString())) {
                        query.setString("param" + i,
                                attrValues[i].toString());
                    }
                }
            }
            int totalCount = ((Number) query.iterate().next()).intValue();

            int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :
                    totalCount / pageSize + 1;

            json.setTotalSize(totalCount);
            json.setTotalPage(totalPage);
            json.setPageSize(pageSize);
        }
        json.setResult(list);

        return json;
    }

    @Transactional
    public boolean batchDelete(List<?> list)
            throws Exception {
        for (Object obj : list) {
            getCurrentSession().delete(obj);
        }
        return true;
    }

    @Transactional
    public <T> JsonResult<T> searchBean(String attrName, Object attrValue, Class<T> type)
            throws Exception {
        JsonResult<T> json = new JsonResult();
        List<T> list = new ArrayList();
        StringBuilder hql = new StringBuilder();
        hql.append("from " + type.getSimpleName() + " where 1=1 ");
        if (!StringUtils.isEmpty(attrName)) {
            hql.append(" and  " + attrName + "=:param1");
        }
        Query query = getCurrentSession().createQuery(hql.toString());
        if (attrValue != null) {
            query.setString("param1", attrValue.toString());
        }
        list = query.list();

        json.setResult(list);

        return json;
    }

    private String genColum(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}
