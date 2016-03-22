/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.restful;

import com.amen.imu.persistance.PersistanceManager;
import java.util.HashMap;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.amen.imu.persistance.PersistanceStore;
import com.amen.imu.persistance.model.Device;
import com.amen.imu.persistance.model.DeviceSensor;
import com.amen.imu.persistance.model.PersistanceRoot;
import com.amen.imu.persistance.model.Probe;
import com.amen.imu.restful.tools.JsonConstructor;
import com.amen.imu.restful.tools.JsonResponder;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author netaro
 */
@Path("/imu/{database}")
@RequestScoped
public class RestResource {

    @Context
    private UriInfo context;

    private final HashMap<String, PersistanceStore> mPersistances;

    public RestResource(UriInfo context) {
        this.context = context;
        this.mPersistances = new HashMap<>();
    }

    public RestResource() {
        this.mPersistances = new HashMap<>();
    }

    @GET
    @Produces("application/json")
    public String otherOperations(@PathParam("database") String pOperation) {
        if (pOperation.equalsIgnoreCase("listdatabases")) {
            String[] databases = JsonConstructor.getAllDatabases(mPersistances);

            return JsonResponder.getListDatabases(databases);
        } else if (pOperation.equalsIgnoreCase("ping")) {
            return "{\"response\":\"pong\"}";
        }

        return "{\"response\":\"" + pOperation + "\"}";
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String otherPOSTOperations(@PathParam("database") String pOperation, String pJson) {
        if (pOperation.equalsIgnoreCase("db_create")) {
            JSONObject lJson = (JSONObject) JSONValue.parse(pJson);
            String pDBName = lJson.get("pStoreName").toString();
            if (mPersistances.containsKey(pDBName)) {
                return "{\"response\":\"already_exists\"\"}";
            }
            mPersistances.put(pDBName, PersistanceManager.createNewPersistance(pDBName));

            return "{\"" + pOperation + "\":\"OK\",\"response\":\"" + pDBName + "\"}";
        }
        return "{\"response\":\"" + pOperation + "\"}";
    }

    @GET
    @Produces("application/json")
    @Path("/ping")
    public String ping2(@PathParam("database") String pDatabase) {
        return "{\"response\":\"" + pDatabase + "\"}";
    }
    
    @GET
    @Produces("application/json")
    @Path("/getAll")
    public Response getAll(@PathParam("database") String pDatabase) {
        if (!mPersistances.containsKey(pDatabase)) {
            mPersistances.put(pDatabase, PersistanceManager.createNewPersistance(pDatabase));
        }
        PersistanceStore lStore = mPersistances.get(pDatabase);
        
        return Response.ok().entity(JsonResponder.getAllData(lStore)).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
    }

//    @GET
//    @Produces("application/json")
//    @Path("/giveProbes")
//    public String getProbes(@PathParam("database") String pDatabase) {
//        ArrayList<String> probes = databaseOperations.getAllProbes(pDatabase + ".db");
//        JSONObject allDatesJson = new JSONObject();
//        JSONArray allDatesArray = new JSONArray();
//
//        for (String s : probes) {
//            allDatesArray.add(s);
//        }
//        allDatesJson.put("allDates", allDatesArray);
//
//        return allDatesJson.toJSONString();
//    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/put")
    public String put(@PathParam("database") String pDatabase, String pObject) {
        if (!mPersistances.containsKey(pDatabase)) {
            mPersistances.put(pDatabase, PersistanceManager.createNewPersistance(pDatabase));
        }
        PersistanceStore lStore = mPersistances.get(pDatabase);
        if (lStore == null) {
            return "{\"response\":\"Not ok\"}";
        }
        JSONObject offer = (JSONObject) JSONValue.parse(pObject);

        String lType = (String) offer.get("objType");
        JSONObject lObject = (JSONObject) offer.get("obj");
        JSONObject requestResponse = new JSONObject();

        if (lType.equals(PersistanceRoot.INSTANCE_TYPE.PROBE.toString())) {
            Probe tmpProbe = Probe.parse(lStore, lObject);

            lStore.put(tmpProbe);
        } else if (lType.equals(PersistanceRoot.INSTANCE_TYPE.DEVICE.toString())) {
            Device tmpDevice = Device.parse(lObject);

        } else if (lType.equals(PersistanceRoot.INSTANCE_TYPE.SENSOR.toString())) {
            DeviceSensor tmpDeviceSensor = DeviceSensor.parse(lObject);
        }

        return "{\"response\":\"" + pDatabase + "\"}";//requestResponse.toJSONString();
    }

}
