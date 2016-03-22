/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.persistance.model;

import com.amen.imu.log.Log;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import org.garret.perst.Persistent;
import org.json.simple.JSONObject;

/**
 *
 * @author AmeN
 */
public class DeviceSensor extends Persistent {

    public Integer mID;
    public String mName;

    public Float mPower;
    public Float mMaxRange;
    public Float mResolution;
    public Integer mSensorType;
    public Integer mMaxDelay;
    public Integer mReportingMode;
    public Integer mMinDelay;
    public Boolean isWakeUpSensor;

    public ArrayList<Probe> mProbes;
    public Device mDevice;

    @Override
    public void writeExternal(ObjectOutput out) {
        try {
            // own fields.
            out.writeInt(mID);
            out.writeChars(mName);

            out.writeFloat(mPower);
            out.writeFloat(mMaxRange);
            out.writeFloat(mResolution);
            out.writeInt(mSensorType);
            out.writeInt(mMaxDelay);
            out.writeInt(mReportingMode);
            out.writeInt(mMinDelay);
            out.writeBoolean(isWakeUpSensor);

            out.writeObject(mProbes);
        } catch (IOException e) {
            Log.Error(getClass(), "Error ocurred writing DeviceSensor instance to an external Output.", e);
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            // own fields.
            mID = in.readInt();
            mName = in.readObject().toString();
            mPower = in.readFloat();
            mMaxRange = in.readFloat();
            mResolution = in.readFloat();
            mSensorType = in.readInt();
            mMaxDelay = in.readInt();
            mReportingMode = in.readInt();
            mMinDelay = in.readInt();
            isWakeUpSensor = in.readBoolean();

            mProbes = (ArrayList<Probe>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.Error(getClass(), "Error ocurred reading DeviceSensor instance from an external Input.", e);
        }
    }

    public static DeviceSensor parse(JSONObject lObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
