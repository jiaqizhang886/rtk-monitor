package com.example.rtkmonitor.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceReading {
    private String rawMessage;

    private String deviceId;
    private DeviceType deviceType;

    private String utcTime;

    // 原始度分格式
    private String latDm;
    private String latDir;
    private String lonDm;
    private String lonDir;

    // 轉換後十進制度
    private Double latitude;
    private Double longitude;

    private String fixQuality;
    private String satelliteCount;
    private String hdop;
    private String altitude;
    private String altitudeUnit;
    private String geoidHeight;
    private String geoidUnit;
    private String dgpsAge;
    private String checksum;
    private String battery;
    private String signal;
    private String reserve1;
    private String reserve2;
    private String reserve3;

    private LocalDateTime receivedAt;
}