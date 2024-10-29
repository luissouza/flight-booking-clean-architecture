package org.pt.flightbooking.application.utils;

import java.util.Map;

public class WebClientUtilsConfig {

  public static String generateUrlParams(final Map<String, Object> mapParams) {
    final StringBuffer stringBuffer = new StringBuffer("?");
    mapParams.forEach((key, value) -> stringBuffer.append(key).append("=").append(value).append("&"));
    return stringBuffer.toString();
  }
}
