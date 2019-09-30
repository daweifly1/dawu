package top.chendawei.interceptor;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "xgxx.customs")
@Data
public class CustomsSecurityProperties {

    //不要认证就可以访问的链接
    private String[] permitAllUrls;
    //认证后就可以访问的链接
    private String[] permitAccessUrls;
    //超级管理员
    private String[] superAdmins;

    private String validateCodePub;
}
