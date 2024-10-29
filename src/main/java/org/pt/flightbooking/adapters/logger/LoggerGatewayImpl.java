package org.pt.flightbooking.adapters.logger;

import org.pt.flightbooking.entities.gateway.LoggerGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerGatewayImpl implements LoggerGateway {
    private static final Logger logger = LoggerFactory.getLogger(LoggerGatewayImpl.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String message, String data) {
        logger.info(message, data);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void warn(String message, String data) {
        logger.warn(message, data);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

}
