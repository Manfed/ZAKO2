/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.util;

/**
 *
 * @author AmeN
 */
public class ApplicationParameter extends Property {

    public ApplicationParameter(String key) {
        super(key, null);
    }

    public boolean isForKey(String keyGiven) {
        return this.getKey().equals(keyGiven);
    }

    @Override
    public String getKey() {
        try {
            return super.getKey().toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getValue() {
        try {
            return super.getValue().toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Object setValue(String v) {
        return super.setValue(v); //To change body of generated methods, choose Tools | Templates.
    }
}
