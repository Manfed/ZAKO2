/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

/**
 *
 * @author AmeN
 */
public class IMUClient {

    public static void main(String[] args) {
        //postNewDB();
        getAll();
        //offer();
        // postHttpPost("http://localhost:1002/matszymaJetty/postdata/applicationOctetStream", new String("Jer Demko").getBytes());
    }

    private static void offer() {
        JSONObject obj = new JSONObject();
        JSONObject obj1 = new JSONObject();
        obj.put("objType", "PROBE");
        obj1.put("mTimestamp", String.valueOf(System.currentTimeMillis()));
        obj1.put("mType", "TYPE_ACCELEROMETER");
        obj1.put("mValues", new String("1234.5:12354.34:12324.354"));

        obj.put("obj", obj1);
        postHttpPost("http://localhost:1002/imu/database/put", obj.toJSONString().getBytes());
    }

    private static void getAll() {
        JSONObject obj = new JSONObject();
        postHttpPost("http://localhost:1002/imu/database/get", obj.toJSONString().getBytes());
    }

    private static void get() {
        JSONObject obj = new JSONObject();
        obj.put("name", "nowadb");
        obj.put("operation", "giveProbes");

        postHttpPost("http://localhost:1002/geogis/nowadb/giveProbes", obj.toJSONString().getBytes());
    }

    private static void postNewDB() {
        JSONObject obj = new JSONObject();
        obj.put("pOperation", "db_create");
        obj.put("pStoreName", "database");

        postHttpPost("http://localhost:1002/imu/db_create", obj.toJSONString().getBytes());
    }

    private static void postHttpPost(String url, byte[] message) {

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url);

            ByteArrayEntity entity = new ByteArrayEntity(message);
            entity.setContentType(MediaType.APPLICATION_JSON);
            postRequest.setEntity(entity);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != Response.Status.OK
                    .getStatusCode()) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
