package com.example.rtkmonitor.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Wgs84Converter {


    public static double ubloxDm2d(double ddmmDotM) {
        double d0 = Math.floor(ddmmDotM / 100);
        double d = d0 + (ddmmDotM - d0 * 100) / 60;
        BigDecimal bd = BigDecimal.valueOf(d).setScale(6, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public static double convertLat(String latDM, String latDir) {
        double value = ubloxDm2d(Double.parseDouble(latDM));
        return "S".equalsIgnoreCase(latDir) ? -value : value;
    }


    public static double convertLon(String lonDM, String lonDir) {
        double value = ubloxDm2d(Double.parseDouble(lonDM));
        return "W".equalsIgnoreCase(lonDir) ? -value : value;
    }
}
