package org.pt.flightbooking.adapters.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "org.pt.flightbooking")
public class AspectConfig {



}
