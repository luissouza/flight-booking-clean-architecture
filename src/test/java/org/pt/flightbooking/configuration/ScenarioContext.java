package org.pt.flightbooking.configuration;

import org.pt.flightbooking.enums.Context;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ScenarioContext {

    private final Map<String, Object> scenarioContext = new HashMap<>();

    public void setContext(final Context key, final Object value) {
        scenarioContext.put(key.toString(), value);
    }

    public void clearContext() {
        scenarioContext.clear();
    }

    public Object getContext(final Context key){
        return scenarioContext.get(key.toString());
    }

}