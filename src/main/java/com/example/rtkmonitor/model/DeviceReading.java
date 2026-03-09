package com.example.rtkmonitor.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rtk_device_record")
public class DeviceReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "raw_msg")
    private String rawMessage;

    @Column(name = "device_no", nullable = false, length = 64)
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_kind", length = 32)
    private DeviceType deviceType;

    @Column(name = "gps_time", length = 32)
    private String utcTime;

    // 原始度分格式
    @Column(name = "lat_dm_value", length = 32)
    private String latDm;

    @Column(name = "lat_ns", length = 8)
    private String latDir;

    @Column(name = "lon_dm_value", length = 32)
    private String lonDm;

    @Column(name = "lon_ew", length = 8)
    private String lonDir;

    // 轉換後十進制度
    @Column(name = "wgs84_lat")
    private BigDecimal latitude;

    @Column(name = "wgs84_lon")
    private BigDecimal longitude;

    @Column(name = "fix_quality", length = 16)
    private String fixQuality;

    @Column(name = "satellite_count", length = 16)
    private String satelliteCount;

    @Column(name = "hdop_value", length = 32)
    private String hdop;

    @Column(name = "altitude_value", length = 32)
    private String altitude;

    @Column(name = "altitude_unit", length = 16)
    private String altitudeUnit;

    @Column(name = "geoid_height_value", length = 32)
    private String geoidHeight;

    @Column(name = "geoid_height_unit", length = 16)
    private String geoidUnit;

    @Column(name = "dgps_age_value", length = 32)
    private String dgpsAge;

    @Column(name = "checksum_value", length = 32)
    private String checksum;

    @Column(name = "battery_value", length = 32)
    private String battery;

    @Column(name = "signal_value", length = 32)
    private String signal;

    @Column(name = "reserve_1", length = 64)
    private String reserve1;

    @Column(name = "reserve_2", length = 64)
    private String reserve2;

    @Column(name = "reserve_3", length = 64)
    private String reserve3;

    @Column(name = "received_time", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "source_ip", length = 64)
    private String sourceIp;

    @Column(name = "source_port")
    private Integer sourcePort;

    @Column(name = "created_time")
    private LocalDateTime createdAt;
}