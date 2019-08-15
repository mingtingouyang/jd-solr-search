package org.oza.jd.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc //<mvc:annotation-driver>
@ComponentScan(basePackages = "org.oza.jd") //<context:component-scan>
@PropertySource("classpath:solr.properties")
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    // <mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //配置视图解析器
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    //获取 Solr 客户端
    @Bean
    public HttpSolrClient getHttpSolrClient(@Value("${solr.url}") String url) {
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder(url);
        HttpSolrClient client = builder.build();
        return client;
    }

}
