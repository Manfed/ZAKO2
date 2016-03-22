/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.util;

import java.util.Map;

/**
 *
 * @author AmeN
 */
public class Property implements Map.Entry<Object, Object>{
    private Object key;
    private Object value;

    public Property() {
        key = null;
        value = null;
    }

    public Property(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getKey() {
        return key;
    }

    @Override
    public Object setValue(Object v) {
        value = v;
        return value;
    }
}
