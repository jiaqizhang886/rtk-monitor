package com.example.rtkmonitor.service;

import com.example.rtkmonitor.model.GnggaData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NmeaParserService {


    public Optional<GnggaData> parse(String message) {
        String[] fields = message.split(",");
        if (fields.length != 21) {
            return Optional.empty();
        }
        if (!"$GNGGA".equals(fields[0])) {
            return Optional.empty();
        }


        return Optional.of(GnggaData.builder()
                .raw(message)
                .utcTime(fields[1])
                .latDm(fields[2])
                .latDir(fields[3])
                .lonDm(fields[4])
                .lonDir(fields[5])
                .fixQuality(fields[6])
                .satelliteCount(fields[7])
                .hdop(fields[8])
                .altitude(fields[9])
                .altitudeUnit(fields[10])
                .geoidHeight(fields[11])
                .geoidUnit(fields[12])
                .dgpsAge(fields[13])
                .checksum(fields[14])
                .deviceId(fields[15])
                .battery(fields[16])
                .signal(fields[17])
                .reserve1(fields[18])
                .reserve2(fields[19])
                .reserve3(fields[20])
                .build());
    }
}
