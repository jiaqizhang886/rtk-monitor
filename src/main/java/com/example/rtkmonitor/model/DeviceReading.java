package com.example.rtkmonitor.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceReading {
private String deviceId;
private DeviceType deviceType;
private String utcTime;
private Double latitude;
private Double longitude;
private String fixQuality;
private String satelliteCount;
private String hdop;
private String altitude;
private String battery;
private String signal;
private LocalDateTime receivedAt;
}