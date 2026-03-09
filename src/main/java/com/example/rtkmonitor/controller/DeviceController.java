package com.example.rtkmonitor.controller;

import com.example.rtkmonitor.model.ApiResponse;
import com.example.rtkmonitor.model.DeviceReading;
import com.example.rtkmonitor.model.DeviceType;
import com.example.rtkmonitor.model.Hk1980Result;
import com.example.rtkmonitor.service.DeviceRegistryService;
import com.example.rtkmonitor.service.GeodeticApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    @Resource
    private  DeviceRegistryService registryService;
    @Resource
    private  GeodeticApiService geodeticApiService;


    @GetMapping("/latest")
    public ApiResponse<Collection<DeviceReading>> latest() {
        return new ApiResponse<>(true, "OK", registryService.getAllLatest());
    }


    @GetMapping("/{type}")
    public ApiResponse<DeviceReading> byType(@PathVariable String type) {
        DeviceType deviceType = DeviceType.valueOf(type.toUpperCase());
        return new ApiResponse<>(true, "OK", registryService.getByType(deviceType));
    }


    @GetMapping("/{type}/convert")
    public ApiResponse<Hk1980Result> convert(@PathVariable String type) {
        DeviceType deviceType = DeviceType.valueOf(type.toUpperCase());
        DeviceReading reading = registryService.getByType(deviceType);
        if (reading == null) {
            return new ApiResponse<>(false, "No data for device type: " + deviceType, null);
        }
        Hk1980Result result = geodeticApiService.wgs84ToHk1980(reading.getLatitude(), reading.getLongitude());
        return new ApiResponse<>(true, "OK", result);
    }
}
