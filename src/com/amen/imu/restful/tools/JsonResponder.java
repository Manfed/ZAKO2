/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.restful.tools;

import com.amen.imu.persistance.PersistanceStore;
import com.amen.imu.persistance.model.PersistanceRoot;
import com.amen.imu.persistance.model.Probe;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author netaro
 */
public class JsonResponder {

    public static String getOkResponse() {
        return "{\"response\":\"ok\"}";
    }

    public static String getErrorResponseWithMessage(String message) {
        return "{\"response\":\"" + message + "\"}";
    }

    public static String getListDatabases(String[] databases) {
        JSONObject response = new JSONObject();
        JSONArray array = new JSONArray();
        for (String database : databases) {
            array.add(database);
        }
        response.put("databases", array);
        return response.toJSONString();
    }

    public static String getExceptionMessage(Exception e) {
        JSONObject response = new JSONObject();
        response.put("error", "An error was found in databaseOperations.getBoundingRectangle");
        response.put("message", e.getMessage());
        return response.toJSONString();
    }

    public static String extractTstamp(String newNodes) {
        JSONObject newNodesContainer = (JSONObject) JSONValue.parse(newNodes);
        return (String) newNodesContainer.get("tstamp");
    }

    public static String getAllDates(ArrayList<String> allDates) {
        JSONObject allDatesJson = new JSONObject();
        JSONArray allDatesArray = new JSONArray();

        for (String s : allDates) {
            allDatesArray.add(s);
        }
        allDatesJson.put("allDates", allDatesArray);

        return allDatesJson.toJSONString();
    }

    public static ArrayList<String> getAllGivenDates(String givenDates) {
        JSONObject allGivenDates = (JSONObject) JSONValue.parse(givenDates);
        ArrayList<String> givenDatesArray = new ArrayList<>();
        JSONArray array = (JSONArray) allGivenDates.get("dates");

        for (Object o : array.toArray()) {
            givenDatesArray.add((String) o);
        }

        return givenDatesArray;
    }

    public static JSONObject getAllData(PersistanceStore lStore) {
        PersistanceRoot root = lStore.getStorage().getRoot();
        JSONObject pObject = new JSONObject();
        JSONArray pArray = new JSONArray();
        for (Object o : root.indexProbes.toArray()) {
            if (o instanceof Probe) {
                pArray.add(Probe.toJSON((Probe) o));
            }
        }

        pObject.put("result", pArray);
        return pObject;
    }

}
