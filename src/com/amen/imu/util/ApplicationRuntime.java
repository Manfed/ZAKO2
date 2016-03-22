/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.util;

import com.amen.imu.util.ApplicationParameter;
import com.amen.imu.config.ConfigurationSystem;
import com.amen.imu.config.DirectoryWalker;
import com.amen.imu.log.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AmeN
 */
public class ApplicationRuntime {

    private static Boolean IS_WORKING;
    private static final ConfigurationSystem SYS_CONFIG;
    private static final Map<String, ApplicationParameter> PARAMETERS;

    private static final String APPLICATION_DIRECTORY;

    static {
        IS_WORKING = true;
        SYS_CONFIG = new ConfigurationSystem();
        PARAMETERS = new HashMap<>();
        PARAMETERS.put("--configFile", new ApplicationParameter("--configFile"));

        APPLICATION_DIRECTORY = DirectoryWalker.appendDirectory(SYS_CONFIG.getUserDirectory(), "Data");
        try {
            if (Files.notExists(Paths.get(APPLICATION_DIRECTORY), LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectory(Paths.get(APPLICATION_DIRECTORY));
            }
        } catch (IOException ex) {
            Log.Fatal(ApplicationRuntime.class, "Application directory can not be created!", ex);
        }
    }

    public static ConfigurationSystem getSystemConfiguration() {
        return SYS_CONFIG;
    }

    public static void parseApplicationParameters(String[] args) {
        List<String> argsProvided = Arrays.asList(args);

        argsProvided.stream().forEach((parameter) -> {
            try {
                if (!parameter.contains("=")) {
                    throw new Exception(String.format("Argument \"%s\"unrecognized.", parameter));
                }
                if (PARAMETERS.containsKey(parameter.split("=")[0])) {
                    PARAMETERS.get(parameter.split("=")[0]).setValue(parameter.split("=")[1]);
                }
            } catch (Exception e) {
                Log.Error(ApplicationRuntime.class, e);
            }
        });
    }

    public static String getAlternativeConfig() {
        return PARAMETERS.get("--config").getValue();
    }

    public static String getAppDirectory() {
        return APPLICATION_DIRECTORY;
    }

    public static Boolean isRunning() {
        return IS_WORKING;
    }

    public static void stopRunning() {
        IS_WORKING = false;
    }
}
