/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.config;

import com.amen.imu.util.ApplicationRuntime;
import com.amen.imu.log.Log;
import com.typesafe.config.ConfigFactory;
import java.io.File;

/**
 *
 * @author AmeN
 */
public class ConfigurationFile {

    private static final com.typesafe.config.Config configuration;
    private static final String CONF_FILE = "config.properties";
    private static final String CONF_PATH;
    private static final String CONF_SETTINGS = "config";
    private static final String CONF_DIR;

    public static String getConfigPath() {
        return CONF_DIR;
    }

    static {
        CONF_DIR = DirectoryWalker.appendDirectory(ApplicationRuntime.getAppDirectory(), CONF_SETTINGS);
        com.typesafe.config.Config configurationAlternative = null;
        CONF_PATH = DirectoryWalker.appendFilename(ApplicationRuntime.getAppDirectory(), CONF_FILE);
        Log.Info(ConfigurationFile.class, "Primary path: " + CONF_PATH);

        configurationAlternative = ConfigFactory.parseFile(new File(CONF_PATH));
        com.typesafe.config.Config fallback = ConfigFactory.parseResources(CONF_FILE);
        configuration = configurationAlternative.withFallback(fallback);
        Log.Info(ConfigurationFile.class, "Backup path: " + CONF_FILE);
    }

    public static boolean hasPath(String path) {
        return configuration.hasPath(path);
    }

    public static boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    public static int getInt(String path) {
        return configuration.getInt(path);
    }

    public static long getLong(String path) {
        return configuration.getLong(path);
    }

    public static double getDouble(String path) {
        return configuration.getDouble(path);
    }

    public static String getString(String path) {
        return configuration.getString(path);
    }

}
