/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author rafa
 */
@Configuration
public class EmailConfiguration {

//    @Bean
//    public ResourceBundleMessageSource emailMessageSource() {
//        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("/mailMessages");
//        return messageSource;
//    }
//
//    @Bean
//    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);
//        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
//        return templateEngine;
//    }
}
