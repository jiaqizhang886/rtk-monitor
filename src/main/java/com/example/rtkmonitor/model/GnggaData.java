package com.example.rtkmonitor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GnggaData {
private String raw;
private String utcTime;
private String latDm;
private String latDir;
private String lonDm;
private String lonDir;
private String fixQuality;
private String satelliteCount;
private String hdop;
private String altitude;
private String altitudeUnit;
private String geoidHeight;
private String geoidUnit;
private String dgpsAge;
private String checksum;
private String deviceId;
private String battery;
private String signal;
private String reserve1;
private String reserve2;
private String reserve3;
}