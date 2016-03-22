/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.persistance;

import com.amen.imu.log.Log;
import com.amen.imu.persistance.model.PersistanceRoot;
import com.amen.imu.util.ApplicationRuntime;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

/**
 *
 * @author AmeN
 */
public class PersistanceManager {

    public static PersistanceStore createNewPersistance(String pStoreName) {
        PersistanceStore lNewStore = new PersistanceStore(createStorage(pStoreName));
        lNewStore.setStoreName(pStoreName);

        return lNewStore;
    }

    private static Storage createStorage(String pPath) {
        Storage lStorage = StorageFactory.getInstance().createStorage();

        lStorage.open(ApplicationRuntime.getAppDirectory() + "\\" + pPath + ".dbs");
        PersistanceRoot root = (PersistanceRoot) lStorage.getRoot();
        if (root == null) {
            // if root object was not specified, then storage is not yet
            // initialized
            // Perform initialization:
            // ... create root object
            root = new PersistanceRoot(lStorage);
            // ... register new root object
            lStorage.setRoot(root);
            // ... and import data from resource files
        }

        root.load();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Log.Debug(getClass(), "Shutdown hook...");
                lStorage.commit();
                lStorage.close();
                Log.Debug(getClass(), "Executed.");

            }
        });

        return lStorage;
    }
}
