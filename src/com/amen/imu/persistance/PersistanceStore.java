/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.persistance;

import com.amen.imu.log.Log;
import com.amen.imu.persistance.model.PersistanceRoot;
import com.amen.imu.persistance.model.Probe;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

/**
 *
 * @author AmeN
 */
public class PersistanceStore {

    private String mStorageName;
    private Storage mStorage;

    public PersistanceStore() {
    }

    public PersistanceStore(Storage storage) {
        this.mStorage = storage;
    }

    public void setStoreName(String pStoreName) {
        mStorageName = pStoreName;
    }

    public void put(Probe tmpProbe) {
        PersistanceRoot root = (PersistanceRoot) mStorage.getRoot();
        if (root == null) {
            // if root object was not specified, then storage is not yet
            // initialized
            // Perform initialization:
            // ... create root object
            root = new PersistanceRoot(mStorage);
            // ... register new root object
            mStorage.setRoot(root);
            // ... and import data from resource files
        }
        root.put(tmpProbe);
        mStorage.commit();
    }

    public Storage getStorage() {
        return mStorage;
    }
}
