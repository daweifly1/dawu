package net.swa.system.service.impl;

import net.swa.system.beans.entity.Dict;
import net.swa.system.dao.HibernateDaoSupport;
import net.swa.system.service.IDictService;
import net.swa.util.JsonResult;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dictService")
public class DictServiceImpl
        extends HibernateDaoSupport
        implements IDictService {
    public List<Dict> getDictType()
            throws Exception {
        List<Dict> dicType = new ArrayList();
        Query query = getCurrentSession().createQuery("from Dict  ");
        dicType = query.list();
        return dicType;
    }

    public void updateDicNum(Long[] ids, Long[] num) {
        for (int i = 0; i < ids.length; i++) {
            Query query = getCurrentSession().createQuery("update Dict set dictPaixu=:num where id=:id");
            query.setLong("num", num[i].longValue());
            query.setLong("id", ids[i].longValue());
            query.executeUpdate();
        }
    }

    public List<Dict> getDictType2() {
        List<Dict> dicType = new ArrayList();
        Query query = getCurrentSession().createQuery("from Dict d where title in ('电池型号','电池品牌') group by d.title");
        dicType = query.list();
        return dicType;
    }

    public JsonResult<String> openSessiondelete(Long[] ids) {
        Session session = getOpenSession();
        Transaction tx = session.beginTransaction();
        Query query = null;
        String hql = "from Battery  where pinPai=:pinPai or xingHao=:xingHao";
        query = session.createQuery(hql);
        JsonResult<String> json = new JsonResult();
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] != null) {
                Dict d = (Dict) session.get(Dict.class, ids[i]);
                if (d != null) {
                    query.setString("pinPai", d.getKey());
                    query.setString("xingHao", d.getKey());
                    List l = query.list();
                    if ((l != null) && (l.size() > 0)) {
                        json.setMessage("字典数据正在使用不可以删除");
                        json.setSuccess(false);
                        tx.rollback();
                        session.close();
                        break;
                    }
                    session.delete(d);
                }
            }
        }
        if (session.isOpen()) {
            json.setMessage("删除成功");
            json.setSuccess(true);
            tx.commit();
            session.close();
        }
        return json;
    }
}
