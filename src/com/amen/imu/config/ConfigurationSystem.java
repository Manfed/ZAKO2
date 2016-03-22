package com.amen.imu.config;

import com.amen.imu.log.Log;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AmeN
 */
public class ConfigurationSystem {

    public static enum OS_TYPE {

        Windows, Linux, iOS
    }
    // os settings
    private static final String SYS_NAME;
    private static final String SYS_VERSION;
    private static final String SYS_ARCHITECTURE;
    private static OS_TYPE operatingSystemType;
    // other system settings 
    private static final String SYS_USER;
    private static final String SYS_USER_DIR;

    //
    static {
        SYS_NAME = System.getProperty("os.name").toLowerCase();
        SYS_VERSION = System.getProperty("os.version");
        SYS_ARCHITECTURE = System.getProperty("os.arch");

        SYS_USER_DIR = System.getProperty("user.dir");
        SYS_USER = System.getProperty("user.name");

        System.out.println("Config is : " + SYS_NAME + " / " + SYS_VERSION + " / " + SYS_ARCHITECTURE);
        setOsType();
    }

    public String getOsName() {
        return SYS_NAME;
    }

    private static void setOsType() {
        if (SYS_NAME.toLowerCase().contains("windows")) {
            System.out.println("OS type is Windows...");
            operatingSystemType = OS_TYPE.Windows;
        } else {
            System.out.println("OS type is Linux... -ish... [POSIX]");
            operatingSystemType = OS_TYPE.Linux;
        }
    }

    public OS_TYPE getOsType() {
        return operatingSystemType;
    }

    public String getUserDirectory() {
        return SYS_USER_DIR;
    }

    public String getWorkingDirectory() {
        String tmpVar = null;
        Path tmpPath;

        if (null != getOsType()) {
            switch (getOsType()) {
                case Linux:
                    tmpVar = getUserDirectory() + "/Data/";
                    break;
                case Windows:
                    tmpVar = getUserDirectory() + "\\Data\\";
                    break;
                default:
                    tmpVar = getUserDirectory() + "/Data/";
                    break;
            }
        }
        try {
            tmpPath = Paths.get(tmpVar);
            if (!Files.exists(tmpPath, NOFOLLOW_LINKS)) {
                Files.createDirectory(tmpPath);
            }
        } catch (IOException ex) {
            Log.Error(getClass(), "Working directory is somehow unavailable. Can't create directory. -> path is : " + tmpVar);
            System.exit(2);
        }

        return tmpVar;
    }
}
