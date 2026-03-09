package com.example.rtkmonitor.util;

import com.example.rtkmonitor.config.AppProperties;
import com.example.rtkmonitor.model.DeviceType;

public class DeviceClassifier {


public static DeviceType classify(String deviceId, AppProperties properties) {
if (deviceId == null) return DeviceType.UNKNOWN;
if (deviceId.equalsIgnoreCase(properties.getDeviceMap().getPersonnelDeviceId())) {
return DeviceType.PERSONNEL;
}
if (deviceId.equalsIgnoreCase(properties.getDeviceMap().getVehicleDeviceId())) {
return DeviceType.VEHICLE;
}
return DeviceType.UNKNOWN;
}
}