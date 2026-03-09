package com.example.rtkmonitor.repository;

import com.example.rtkmonitor.model.DeviceReading;
import com.example.rtkmonitor.model.DeviceType;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DeviceMemoryRepository {


    private final Map<String, DeviceReading> latestByDeviceId = new ConcurrentHashMap<>();
    private final Map<DeviceType, DeviceReading> latestByType = new ConcurrentHashMap<>();


    public void save(DeviceReading reading) {
        latestByDeviceId.put(reading.getDeviceId(), reading);
        latestByType.put(reading.getDeviceType(), reading);
    }


    public DeviceReading findByDeviceId(String deviceId) {
        return latestByDeviceId.get(deviceId);
    }


    public DeviceReading findByType(DeviceType type) {
        return latestByType.get(type);
    }


    public Collection<DeviceReading> findAll() {
        return latestByDeviceId.values();
    }
}
