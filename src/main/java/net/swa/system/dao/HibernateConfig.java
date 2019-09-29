//package net.swa.system.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//public class HibernateConfig {
//
//    @Value("${spring.jpa.properties.hibernate.current_session_context_class:org.springframework.orm.hibernate5.SpringSessionContext}")
//    public String current_session_context_class;
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactoryBean() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setPackagesToScan("net.swa");//dao和entity的公共包
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.current_session_context_class", current_session_context_class);
//
//        sessionFactoryBean.setHibernateProperties(properties);
//        return sessionFactoryBean;
//    }
//}
