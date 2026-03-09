package com.example.rtkmonitor.service;

import com.example.rtkmonitor.model.GnggaData;
import com.example.rtkmonitor.util.Wgs84Converter;
import org.springframework.stereotype.Service;

@Service
public class CoordinateService {


public double toLatitude(GnggaData data) {
return Wgs84Converter.convertLat(data.getLatDm(), data.getLatDir());
}


public double toLongitude(GnggaData data) {
return Wgs84Converter.convertLon(data.getLonDm(), data.getLonDir());
}
}