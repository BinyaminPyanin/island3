package com.upgrade.island3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * VersionController
 *
 * @author Binyamin Pyanin
 * @since 20210212
 */
@Configuration
public class ControllerConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/openapi/").setViewName("redirect:/openapi/rapidoc/index.html");
        registry.addViewController("/openapi/rapidoc/").setViewName("forward:/openapi/rapidoc/index.html");
        registry.addViewController("/openapi/swagger-ui/").setViewName("forward:/openapi/swagger-ui/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/openapi/**")
                .addResourceLocations("classpath:/openapi/", "classpath:/openapi/rapidoc/", "classpath:/openapi/swagger-ui/")
                .setCachePeriod(600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

}
