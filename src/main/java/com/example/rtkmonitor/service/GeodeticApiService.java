package com.example.rtkmonitor.service;

import com.example.rtkmonitor.config.AppProperties;
import com.example.rtkmonitor.model.Hk1980Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeodeticApiService {


private final RestTemplate restTemplate;
private final AppProperties properties;


public Hk1980Result wgs84ToHk1980(double lat, double lon) {
String url = UriComponentsBuilder
.fromHttpUrl(properties.getGeodetic().getBaseUrl())
.queryParam("inSys", "wgsgeog")
.queryParam("outSys", "hkgrid")
.queryParam("lat", lat)
.queryParam("long", lon)
.build(true)
.toUriString();


ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
Map<String, Object> body = response.getBody();


Hk1980Result result = new Hk1980Result();
result.setHkN(String.valueOf(body.get("hkN")));
result.setHkE(String.valueOf(body.get("hkE")));
result.setHkpD(String.valueOf(body.get("hkpD")));
return result;
}
}