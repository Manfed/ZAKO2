/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.config;

import com.amen.imu.util.ApplicationRuntime;

/**
 *
 * @author AmeN
 */
public class DirectoryWalker {

    private static final ConfigurationSystem.OS_TYPE OPERATING_SYSTEM;

    static {
        OPERATING_SYSTEM = ApplicationRuntime.getSystemConfiguration().getOsType();
    }

    public static String appendDirectory(String path, String dirName) {
        if (OPERATING_SYSTEM == ConfigurationSystem.OS_TYPE.Windows) {
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            path += dirName + "\\";
        } else {
            if (!path.endsWith("/")) {
                path += "/";
            }
            path += dirName + "/";
        }
        return path;
    }

    public static String appendFilename(String path, String fileName) {
        if (OPERATING_SYSTEM == ConfigurationSystem.OS_TYPE.Windows) {
            if (!path.endsWith("\\")) {
                path += "\\";
            }
            path += fileName;
        } else {
            if (!path.endsWith("/")) {
                path += "/";
            }
            path += fileName;
        }
        return path;
    }
}
