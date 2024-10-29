package org.pt.flightbooking.adapters.infrastructure.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.KeyGenerator;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean("customKeyGenerator")
    public KeyGenerator customKeyGenerator() {
        return (o, method, objects) -> {
            final StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (final Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}