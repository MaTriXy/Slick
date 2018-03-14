package com.mrezanasirloo.slick.middleware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-13
 */

public class BundleSlick {

    private Map<String, Object> map = new HashMap<>();

    public BundleSlick putInteger(String key, Integer i) {
        map.put(key, i);
        return this;
    }

    public BundleSlick putString(String key, String s) {
        map.put(key, s);
        return this;
    }

    public BundleSlick putObject(String key, Object o) {
        map.put(key, o);
        return this;
    }

    public Integer getInteger(String key, Integer def) {
        Object value;
        if ((value = map.get(key)) != null) {
            return ((Integer) value);
        }
        return def;
    }

    public String getSting(String key, String def) {
        Object value;
        if ((value = map.get(key)) != null) {
            return ((String) value);
        }
        return def;
    }

    public <RETURN> RETURN getObject(String key, RETURN def, Class<RETURN> type) throws ClassCastException {
        Object value;
        if ((value = map.get(key)) != null) {
            return type.cast(value);
        }
        return def;
    }

    public <RETURN> RETURN getParameter(RETURN def, Class<RETURN> type) throws ClassCastException {
        Object value;
        if ((value = map.get("$parameter$")) != null) {
            return type.cast(value);
        }
        return def;
    }

    public BundleSlick putParameter(Object o) {
        map.put("$parameter$", o);
        return this;
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Object remove(String key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }
}