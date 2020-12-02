package gr.nikolis.config;

import gr.nikolis.inteceptor.RequestInterceptor;
import gr.nikolis.mappings.home.HomeMappings;
import gr.nikolis.mappings.home.ViewNames;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Rooting the first open the url this direct us to home page
     * == used for home view ==
     * http://localhost:8888/
     *
     * @param registry The registry object
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(HomeMappings.HOME_DEFAULT).setViewName(ViewNames.HOME);
        registry.addViewController(HomeMappings.HOME).setViewName(ViewNames.HOME);
    }

    /**
     * Interceptor for auto change the language
     * we can change the language from URL by typing : http://localhost:8888?locale=el
     *
     * @param registry The registry object
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor());
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    /**
     * Init the locale properties to use,
     * and save the locale into a session
     * and holds it in hole app recycle life
     *
     * @return The LocaleResolver instance
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    /**
     * Create the instance of layout dialect
     * The dialect allow us to do folder mapping
     *
     * @return The layout dialect instance
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}