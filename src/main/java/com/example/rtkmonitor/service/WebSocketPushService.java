package com.example.rtkmonitor.service;

import com.example.rtkmonitor.model.DeviceReading;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketPushService {


private final SimpMessagingTemplate messagingTemplate;


public void pushLatest(DeviceReading reading) {
messagingTemplate.convertAndSend("/topic/rtk/latest", reading);
}
}