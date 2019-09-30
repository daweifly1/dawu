package top.chendawei;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.chendawei.interceptor.AuthorizeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


//    /**
//     * 添加静态资源文件，外部可以直接访问地址
//     *
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("*.do");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }

    @Bean
    public AuthorizeInterceptor authenticationInterceptor() {
        return new AuthorizeInterceptor();
    }

}
