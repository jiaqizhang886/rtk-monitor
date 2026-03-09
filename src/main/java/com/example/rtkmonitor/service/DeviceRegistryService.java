package com.example.rtkmonitor.service;

import com.example.rtkmonitor.config.AppProperties;
import com.example.rtkmonitor.model.DeviceReading;
import com.example.rtkmonitor.model.DeviceType;
import com.example.rtkmonitor.model.GnggaData;
import com.example.rtkmonitor.repository.DeviceMemoryRepository;
import com.example.rtkmonitor.repository.DeviceReadingRepository;
import com.example.rtkmonitor.util.DeviceClassifier;
import com.example.rtkmonitor.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DeviceRegistryService {

    private final DeviceMemoryRepository memoryRepository;
    private final DeviceReadingRepository dbRepository;
    private final AppProperties properties;
    private final CoordinateService coordinateService;
    private final WebSocketPushService webSocketPushService;

    public DeviceReading saveReading(GnggaData data) {
        DeviceType type = DeviceClassifier.classify(data.getDeviceId(), properties);

        DeviceReading reading = DeviceReading.builder()
                .rawMessage(data.getRaw())
                .deviceId(data.getDeviceId())
                .deviceType(type)
                .utcTime(data.getUtcTime())

                .latDm(data.getLatDm())
                .latDir(data.getLatDir())
                .lonDm(data.getLonDm())
                .lonDir(data.getLonDir())

                .latitude(BigDecimal.valueOf(coordinateService.toLatitude(data)))
                .longitude(BigDecimal.valueOf(coordinateService.toLongitude(data)))

                .fixQuality(data.getFixQuality())
                .satelliteCount(data.getSatelliteCount())
                .hdop(data.getHdop())
                .altitude(data.getAltitude())
                .altitudeUnit(data.getAltitudeUnit())
                .geoidHeight(data.getGeoidHeight())
                .geoidUnit(data.getGeoidUnit())
                .dgpsAge(data.getDgpsAge())
                .checksum(data.getChecksum())
                .battery(data.getBattery())
                .signal(data.getSignal())
                .reserve1(data.getReserve1())
                .reserve2(data.getReserve2())
                .reserve3(data.getReserve3())
                .receivedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        DeviceReading saved = dbRepository.save(reading);
        memoryRepository.save(saved);
        webSocketPushService.pushLatest(saved);
        return saved;
    }

    public Collection<DeviceReading> getAllLatest() {
        return memoryRepository.findAll();
    }

    public DeviceReading getByType(DeviceType type) {
        return memoryRepository.findByType(type);
    }

    public DeviceReading getByDeviceId(String deviceId) {
        return memoryRepository.findByDeviceId(deviceId);
    }
}