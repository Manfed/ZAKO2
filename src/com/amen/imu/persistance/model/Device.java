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
public class Device extends Persistent {

    public Integer mID;

    public String mSerialNumber;
    public String mDeviceName;
    public String mDeviceModel;
    public String mDeviceManufacturer;
    public String mOS;
    public String mOSVersion;
    public String mProcessorName;
    public String mProcessorCores;
    public String mProcessorInstructionSet;
    public String mProcessorInstructionSetSIMD;

    public ArrayList<DeviceSensor> mSensors;

    /**
     * Writes content of this object to an external output.
     *
     * @param out - output to write to.
     */
    @Override
    public void writeExternal(ObjectOutput out) {
        try {
            // own fields.
            out.writeInt(mID);

            out.writeChars(mSerialNumber);
            out.writeChars(mDeviceName);
            out.writeChars(mDeviceModel);
            out.writeChars(mDeviceManufacturer);
            out.writeChars(mOS);
            out.writeChars(mOSVersion);
            out.writeChars(mProcessorName);
            out.writeChars(mProcessorCores);
            out.writeChars(mProcessorInstructionSet);
            out.writeChars(mProcessorInstructionSetSIMD);

            out.writeObject(mSensors);
        } catch (IOException e) {
            Log.Error(getClass(), "Error ocurred writing Device instance to an external Output.", e);
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            // own fields.
            mID = in.readInt();

            mSerialNumber = in.readObject().toString();
            mDeviceName = in.readObject().toString();
            mDeviceModel = in.readObject().toString();
            mDeviceManufacturer = in.readObject().toString();
            mOS = in.readObject().toString();
            mOSVersion = in.readObject().toString();
            mProcessorName = in.readObject().toString();
            mProcessorCores = in.readObject().toString();
            mProcessorInstructionSet = in.readObject().toString();
            mProcessorInstructionSetSIMD = in.readObject().toString();

            mSensors = (ArrayList<DeviceSensor>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.Error(getClass(), "Error ocurred reading Device instance from an external Input.", e);
        }
    }

    public static Device parse(JSONObject lObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
