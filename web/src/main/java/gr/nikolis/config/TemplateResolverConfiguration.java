package gr.nikolis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Component
public class TemplateResolverConfiguration {

    @Bean
    public SpringResourceTemplateResolver homeTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(0);
        templateResolver.setCheckExistence(true);
        templateResolver.setUseDecoupledLogic(true);

        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver customerTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/customers/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        templateResolver.setUseDecoupledLogic(true);

        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver gameTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/game/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(2);
        templateResolver.setCheckExistence(true);
        templateResolver.setUseDecoupledLogic(true);

        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver loginTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/login/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(3);
        templateResolver.setCheckExistence(true);
        templateResolver.setUseDecoupledLogic(true);

        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver registerTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/register/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(4);
        templateResolver.setCheckExistence(true);
        templateResolver.setUseDecoupledLogic(true);

        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver errorAccessTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/error/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(5);
        templateResolver.setCheckExistence(true);
        templateResolver.setUseDecoupledLogic(true);

        return templateResolver;
    }
}
