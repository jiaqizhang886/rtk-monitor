package com.example.rtkmonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Udp udp = new Udp();
    private DeviceMap deviceMap = new DeviceMap();
    private Geodetic geodetic = new Geodetic();


    @Data
    public static class Udp {
        private int port;
        private int bufferSize;
        private int threadPoolSize;
    }


    @Data
    public static class DeviceMap {
        private String personnelDeviceId;
        private String vehicleDeviceId;
    }


    @Data
    public static class Geodetic {
        private String baseUrl;
    }
}
