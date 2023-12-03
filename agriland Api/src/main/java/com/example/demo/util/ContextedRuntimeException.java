package com.example.demo.util;

import java.util.Map;
import java.util.HashMap;

public class ContextedRuntimeException extends RuntimeException {
    private Map<String, Object> contextValues = new HashMap<>();


    public ContextedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextedRuntimeException addContextValue(String key, Object value) {
        contextValues.put(key, value);
        return this;
    }

    public Map<String, Object> getContextValues() {
        return contextValues;
    }
}