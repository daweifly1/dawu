package net.swa.system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

public class HibernateDaoSupport {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    protected EntityManager em;

    protected Session getCurrentSession() {
        Session session = em.unwrap(Session.class);
        return session;
    }
//
//    @Transactional
//    protected Session getCurrentSession() {
//        Session ss = entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
//        return ss;
//    }


    /**
     * 获取session
     */
    @Transactional
    protected Session getOpenSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();//这种方式需要手动关闭session
        // 这种方式会自动关闭session，但是要配置current_session_context_class，并且需要使用事务
        //return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }
}
