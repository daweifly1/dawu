package net.swa.system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;

public class HibernateDaoSupport {
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        if (this.sessionFactory == null) {
            return null;
        }
        return this.sessionFactory.getCurrentSession();
    }

    protected Session getOpenSession() {
        if (this.sessionFactory == null) {
            return null;
        }
        Session session = this.sessionFactory.openSession();
        return session;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Required
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
