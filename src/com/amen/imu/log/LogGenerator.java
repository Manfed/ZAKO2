/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.imu.log;

import com.amen.imu.config.ConfigurationFile;
import com.amen.imu.config.ConfigurationSystem;
import com.amen.imu.config.DirectoryWalker;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Paweł Recław @AmeN <b>pawel.reclaw@gmail.com<b>
 */
public class LogGenerator {

    private static final String logCatalog = "log";
    private static final String logProperties = "log.properties";
    private static final String logSettingFile = "logGenerator.properties";
    private static final String generatorPropertiesFile = "generator.properties";
    //
    private final String workingDirectory;
    private final String logPropertiesPath;
    private final ConfigurationSystem.OS_TYPE operatingSystemType;
    //
    private String dirGenerator;
    //
    public String getLogConfigPath(){
        return logPropertiesPath;
    }
    public LogGenerator(ConfigurationSystem.OS_TYPE osType, String workingDir) {
        operatingSystemType = osType;
        workingDirectory = workingDir;
        
        logPropertiesPath = DirectoryWalker.appendFilename(ConfigurationFile.getConfigPath(), logProperties);
    }

    public void validateLoggerConfig() throws IOException {
        String tmpDirConfig = ConfigurationFile.getConfigPath();
        if (Files.notExists(Paths.get(tmpDirConfig), LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(Paths.get(tmpDirConfig));
        }

        dirGenerator = DirectoryWalker.appendDirectory(workingDirectory, logCatalog);
        if (Files.notExists(Paths.get(dirGenerator), LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(Paths.get(dirGenerator));
        }

        GenerateLogFile(tmpDirConfig);
    }

    private void GenerateLogFile(String dirConfig) throws IOException {
        System.out.println("Checking logger...");

        Path settingsFile
                = Paths.get(DirectoryWalker.appendFilename(dirConfig, logSettingFile)); //plik generatora
        Path settingsLog
                = Paths.get(DirectoryWalker.appendFilename(dirConfig, logProperties)); //plik log.properties 

        if (!Files.exists(settingsFile, LinkOption.NOFOLLOW_LINKS)) {
            System.out.println("Sorry, your log.properties-generator file does not exist. \n "
                    + "It will now be generated...");
            Files.copy(getClass().getResourceAsStream(
                    generatorPropertiesFile), settingsFile,
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Your file should be generated. The path is :"
                    + "\n   -- " + settingsFile.toString());
        }
        
        if (!Files.exists(settingsLog, LinkOption.NOFOLLOW_LINKS)) {
            System.out.println("I will now generate log.properties file...");

            generateToFile(settingsLog, settingsFile);

            System.out.println("Generation completed.");
        }
    }

    private void generateToFile(Path logPropertiesPath, Path configurationPath)
            throws IOException {
        Config configuration = ConfigFactory.parseFile(configurationPath.toFile());
        String l_loggers = configuration.getString("log.loggers");
        String l_lauout_ = configuration.getString("log.layout_");
        String l_layout_pattern = configuration.getString("log.layout.pattern");
        String l_type = configuration.getString("log.type");
        String l_max_backupindex = configuration
                .getString("log.max.backupindex");
        String l_max_filesize = configuration.getString("log.max.filesize");

        String ignore = configuration.getString("log.ignore_");
        String l_ignore_class = configuration.getString("log.ignore.class");
        String l_ignore_package = configuration.getString("log.ignore.package");
        String l_noconsole = configuration.getString("log.noconsole");
        ArrayList<String> t_noconsole = new ArrayList<>();
        t_noconsole.addAll(Arrays.asList(l_noconsole.split(";")));
        String[] t_loggers = l_loggers.split(";");

        if (Files.exists(logPropertiesPath, LinkOption.NOFOLLOW_LINKS)) {
            Files.delete(logPropertiesPath);
        }

        Files.createFile(logPropertiesPath);

        try (PrintWriter l_writer = new PrintWriter(logPropertiesPath.toFile())) {
            writeIntro(l_writer);

            l_writer.print("Configuration.Log.Ignored.Class=");
            if (ignore.toLowerCase().equals("true")) {
                l_writer.print(l_ignore_class + "\n");
            } else {
                l_writer.print("\n");
            }

            l_writer.print("Configuration.Log.Ignored.Class=");
            if (ignore.toLowerCase().equals("true")) {
                l_writer.print(l_ignore_package + "\n");
            } else {
                l_writer.print("\n");
            }
            l_writer.println("Configuration.Log.Level=" + configuration.getString("log.level")
            );
            writeLine(l_writer);
            l_writer.println();

            l_writer.println("log4j.rootLogger=DEBUG,ALL");
            l_writer.println("log4j.appender.ALL=" + l_type);
            l_writer.println("log4j.appender.ALL.layout=" + l_lauout_);
            l_writer.println("log4j.appender.ALL.maxFileSize=" + l_max_filesize);
            l_writer.println("log4j.appender.ALL.maxBackupIndex="
                    + l_max_backupindex);
            l_writer.println("log4j.appender.ALL.layout.ConversionPattern="
                    + l_layout_pattern);
            l_writer.println("log4j.appender.ALL.file=" + DirectoryWalker.appendFilename(logCatalog, "output.log"));
            appendConsole(l_writer);

            for (String s_singleLogger : t_loggers) {
                System.out.println("Assuming package is com.amen." + s_singleLogger);

                writeLine(l_writer);
                l_writer.write("\n\n\n");

                String s_singleSplitted = s_singleLogger.split("\\.")[s_singleLogger.split("\\.").length
                        - 1];
                /**
                 * Przepisanie konfiguracji loggera. dodatkowe opcje mozna
                 * dopisać po kazdym loggerze.
                 * <p>
                 */
                l_writer.println("log4j.logger." + s_singleLogger
                        + "=DEBUG," + (t_noconsole.contains(
                                s_singleSplitted)
                                ? "" : "CONSOLE,")
                        + s_singleSplitted.toUpperCase());
                l_writer.println("log4j.additivity."
                        + s_singleLogger + "=false");
                l_writer.println("log4j.appender." + s_singleSplitted
                        .toUpperCase()
                        + "=" + l_type);
                l_writer.println("log4j.appender." + s_singleSplitted
                        .toUpperCase()
                        + ".layout=" + l_lauout_);
                l_writer.println("log4j.appender." + s_singleSplitted
                        .toUpperCase()
                        + ".maxFileSize=" + l_max_filesize);
                l_writer.println("log4j.appender." + s_singleSplitted
                        .toUpperCase()
                        + ".maxBackupIndex="
                        + l_max_backupindex);
                l_writer.println("log4j.appender." + s_singleSplitted
                        .toUpperCase()
                        + ".layout.ConversionPattern="
                        + l_layout_pattern);
                l_writer.println("log4j.appender." + s_singleSplitted
                        .toUpperCase() + ".file=" + DirectoryWalker.appendFilename(
                                DirectoryWalker.appendDirectory(logCatalog, s_singleSplitted.toLowerCase()), "output.log"));
            }
        }
    }

    private void writeIntro(PrintWriter l_writer) {
        writeLine(l_writer);
        l_writer.write(s_date);
        writeLine(l_writer);
    }

    private void writeLine(PrintWriter l_writer) {
        for (int i = 0; i < 7; i++) {
            l_writer.print(s_line);
        }
        l_writer.println(s_line);
    }
    private final static String s_line = "**********";
    private final static String s_date = "**** DATE:"
            + new SimpleDateFormat(
                    "yyyy/MM/dd HH:mm:ss").format(new Date()) + " ****\n";

    private void appendConsole(PrintWriter l_writer) {
        l_writer.println("log4j.appender.CONSOLE="
                + "org.apache.log4j.ConsoleAppender\n"
                + "log4j.appender.CONSOLE.layout="
                + "org.apache.log4j.PatternLayout\n"
                + "log4j.appender.CONSOLE.layout.ConversionPattern="
                + "%d [%t] %-5p %c:%M:%L - %m%n");
    }
}
