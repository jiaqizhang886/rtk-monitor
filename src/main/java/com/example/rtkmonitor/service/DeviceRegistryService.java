package com.example.rtkmonitor.service;

import com.example.rtkmonitor.config.AppProperties;
import com.example.rtkmonitor.model.DeviceReading;
import com.example.rtkmonitor.model.DeviceType;
import com.example.rtkmonitor.model.GnggaData;
import com.example.rtkmonitor.repository.DeviceMemoryRepository;
import com.example.rtkmonitor.util.DeviceClassifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DeviceRegistryService {


private final DeviceMemoryRepository repository;
private final AppProperties properties;
private final CoordinateService coordinateService;
private final WebSocketPushService webSocketPushService;


public DeviceReading saveReading(GnggaData data) {
DeviceType type = DeviceClassifier.classify(data.getDeviceId(), properties);


DeviceReading reading = DeviceReading.builder()
.deviceId(data.getDeviceId())
.deviceType(type)
.utcTime(data.getUtcTime())
.latitude(coordinateService.toLatitude(data))
.longitude(coordinateService.toLongitude(data))
.fixQuality(data.getFixQuality())
.satelliteCount(data.getSatelliteCount())
.hdop(data.getHdop())
.altitude(data.getAltitude())
.battery(data.getBattery())
.signal(data.getSignal())
.receivedAt(LocalDateTime.now())
.build();


repository.save(reading);
webSocketPushService.pushLatest(reading);
return reading;
}


public Collection<DeviceReading> getAllLatest() {
return repository.findAll();
}


public DeviceReading getByType(DeviceType type) {
return repository.findByType(type);
}
}