package com.example.rtkmonitor.repository;

import com.example.rtkmonitor.model.DeviceReading;
import com.example.rtkmonitor.model.DeviceType;
import com.example.rtkmonitor.model.DeviceReading;
import com.example.rtkmonitor.model.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceReadingRepository extends JpaRepository<DeviceReading, Long> {

    Optional<DeviceReading> findTopByDeviceTypeOrderByReceivedAtDesc(DeviceType deviceType);

    Optional<DeviceReading> findTopByDeviceIdOrderByReceivedAtDesc(String deviceId);

    List<DeviceReading> findTop100ByDeviceIdOrderByReceivedAtDesc(String deviceId);

    List<DeviceReading> findTop100ByDeviceTypeOrderByReceivedAtDesc(DeviceType deviceType);
}