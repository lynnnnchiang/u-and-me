package tw.idv.cha102.g7.common.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tw.idv.cha102.g7.common.filter.GrouperFilter;
import tw.idv.cha102.g7.common.filter.HostFilter;
import tw.idv.cha102.g7.common.filter.MemberFilter;


@Configuration
public class UandmeConfiguration {

    @Bean
    public FilterRegistrationBean memFilter(){
        FilterRegistrationBean<MemberFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new MemberFilter());
        //要通過此filter的入口
        bean.addUrlPatterns("/member/*");
        bean.setName("memFilter");

        //設定此filter再filterChain的執行順序 數字越小 越早擷取請求 越晚擷取回應
        bean.setOrder(0);
        System.out.println("會員FILTER開啟!!");


        return bean;
    }
    @Bean
    public FilterRegistrationBean hostFilter(){
        FilterRegistrationBean<HostFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HostFilter());
        //要通過此filter的入口
        bean.addUrlPatterns("/host/*");
//        bean.addUrlPatterns("/member/*");
        bean.setName("hostFilter");

        //設定此filter再filterChain的執行順序 數字越小 越早擷取請求 越晚擷取回應
        bean.setOrder(1);
        System.out.println("管理員FILTER開啟!!");


        return bean;
    }
    @Bean
    public FilterRegistrationBean groupFilter(){
        FilterRegistrationBean<GrouperFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new GrouperFilter());
        //要通過此filter的入口
        bean.addUrlPatterns("/member/grouper/*");
//        bean.addUrlPatterns("/grouper/*");
        bean.setName("groupFilter");

        //設定此filter再filterChain的執行順序 數字越小 越早擷取請求 越晚擷取回應
        bean.setOrder(2);
        System.out.println("團主FILTER開啟!!");


        return bean;
    }
}
