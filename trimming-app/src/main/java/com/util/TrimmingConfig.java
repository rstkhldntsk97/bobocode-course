package com.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrimmingConfig {

    @Bean
    public TrimmedAnnotationBeanPostProcessor getTrimming() {
        return new TrimmedAnnotationBeanPostProcessor();
    }

}
