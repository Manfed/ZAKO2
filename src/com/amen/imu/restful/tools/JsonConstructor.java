/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.restful.tools;

import com.amen.imu.persistance.PersistanceStore;
import java.util.HashMap;

/**
 *
 * @author AmeN
 */
public class JsonConstructor {

    public static String[] getAllDatabases(HashMap<String, PersistanceStore> mPersistances) {
        return new String[]{"baza"};
    }

}
