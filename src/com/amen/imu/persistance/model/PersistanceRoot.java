/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.persistance.model;

import com.amen.imu.log.Log;
import org.garret.perst.FieldIndex;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;

/**
 *
 * @author AmeN
 */
public class PersistanceRoot extends Persistent {

    public static enum INSTANCE_TYPE {
        PROBE,
        SENSOR,
        DEVICE
    }
    public FieldIndex indexSensors;
    public FieldIndex indexDevices;
    public FieldIndex indexProbes;

    public PersistanceRoot() {
    }

    public PersistanceRoot(Storage db) {
        super(db);
        // Create unique index for sensors
        indexSensors = db.createFieldIndex(DeviceSensor.class, "mID", true);
        // Create unique index for devices
        indexDevices = db.createFieldIndex(Device.class, "mID", true);
        // Create unique index for probes
        indexProbes = db.createFieldIndex(Probe.class, "mTimestamp", true);
    }

    public void put(Persistent pObj) {
        Log.Debug(getClass(), "Putting obj into index: " + pObj);
        if (pObj instanceof Probe) {
            indexProbes.put(pObj);
        } else if (pObj instanceof Device) {
            indexDevices.put(pObj);
        } else if (pObj instanceof DeviceSensor) {
            indexSensors.put(pObj);
        }
    }
}
