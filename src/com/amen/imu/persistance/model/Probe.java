/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.persistance.model;

import com.amen.imu.log.Log;
import com.amen.imu.persistance.PersistanceStore;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import org.garret.perst.Link;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.json.simple.JSONObject;

/**
 *
 * @author AmeN
 */
public class Probe extends Persistent {

    public enum PROBE_TYPE {
        TYPE_GYROSCOPE,
        TYPE_ACCELEROMETER,
        UNKNOWN;

    }

    public Long mTimestamp;
    public Link mValues;
    public PROBE_TYPE mType = PROBE_TYPE.UNKNOWN;

    public DeviceSensor mSensor;

    @Override
    public void writeExternal(ObjectOutput out) {
        try {
            // own fields.
            out.writeLong(mTimestamp);

            out.writeObject(mValues);
            out.writeObject(mType);

            out.writeObject(mSensor);
        } catch (IOException e) {
            Log.Error(getClass(), "Error ocurred writing ProbeAccelerometer instance to an external Output.", e);
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            // own fields.
            mTimestamp = in.readLong();

            mValues = (Link) in.readObject();
            mType = (PROBE_TYPE) in.readObject();

            mSensor = (DeviceSensor) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            Log.Error(getClass(), "Error ocurred reading ProbeAccelerometer instance from an external Input.", e);
        }
    }

    public static Probe parse(PersistanceStore pStore, JSONObject lObject) {
        Probe newProbe = new Probe();

        newProbe.mTimestamp = Long.parseLong(lObject.get("mTimestamp").toString());
        newProbe.mType = PROBE_TYPE.valueOf(lObject.get("mType").toString());
        newProbe.mValues = pStore.getStorage().createLink();

        String pValues = lObject.get("mValues").toString();
        String[] pVals = pValues.split(":");
        for (String pSingle : pVals) {
            Double pDouble = Double.parseDouble(pSingle);
            newProbe.mValues.add(pDouble);
        }

        newProbe.mSensor = new DeviceSensor();

        return newProbe;
    }

    public static JSONObject toJSON(Probe pProbe) {
        JSONObject pObject = new JSONObject();
        pObject.put("mTimestamp", pProbe.mTimestamp);
        pObject.put("mType", pProbe.mType);
        String values = new String();
        for (Object d : pProbe.mValues.toArray()) {
            values += "" + d + ":";
        }
        if (!values.isEmpty()) {
            values = values.substring(0, values.length() - 1);
        }
        pObject.put("mValues", values);
        pObject.put("mSensor", pProbe.mSensor.mID);

        return pObject;
    }
}
