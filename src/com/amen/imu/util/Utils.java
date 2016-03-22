package com.amen.imu.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author AmeN
 */
public class Utils {
    
    public static long timestampToEpoch(String timestamp) {
        //Example tstamp: 2012-07-28T08:46:15Z
        String[] splitTstamp = timestamp.split("-|T|:|Z");
        ArrayList<String> stringArray = new ArrayList<>();
        for (String s : splitTstamp) {
            stringArray.add(s);
        }

        int year = Integer.parseInt(stringArray.get(0));
        int month = Integer.parseInt(stringArray.get(1));
        int date = Integer.parseInt(stringArray.get(2));
        int hrs = Integer.parseInt(stringArray.get(3));
        int min = Integer.parseInt(stringArray.get(4));
        int sec = Integer.parseInt(stringArray.get(5));

        Calendar calendar = new GregorianCalendar(year, month, date, hrs, min, sec);

        return calendar.getTime().getTime() / 1000;
    }

    public static String epochToTimestamp(long epoch) {
        Date date = new Date(epoch * 1000);
        String s = date.getYear() + 1900 + "-" + date.getMonth() + "-" + date.getDate() + "T" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "Z";
        return s;
    }

    public static String getCurrentDate() {
        Date date = new Date();
        return epochToTimestamp(date.getTime() / 1000);
    }
    
}
