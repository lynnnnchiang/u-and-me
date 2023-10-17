package tw.idv.cha102.g7.group.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.idv.cha102.g7.group.entity.Group;
import tw.idv.cha102.g7.group.interceptor.GroupOwnnerInterceptor;

@Configuration
public class GroupOwnnerConfig implements WebMvcConfigurer {

    @Autowired
    private GroupOwnnerInterceptor groupOwnnerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(groupOwnnerInterceptor).addPathPatterns("/group");
    }
}
