package org.pt.flightbooking.entities.gateway;

public interface LoggerGateway {
    void info(String message);
    void info(String message, String data);
    void warn(String message);
    void warn(String message, String data);
    void error(String message, Throwable throwable);
}
