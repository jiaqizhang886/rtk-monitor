package com.example.rtkmonitor.service;

import com.example.rtkmonitor.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UdpServerService {

    @Resource
    private  AppProperties properties;

    @Resource
    private  NmeaParserService parserService;

    @Resource
    private  DeviceRegistryService deviceRegistryService;


private DatagramSocket socket;
private ExecutorService executor;
private volatile boolean running = true;


@PostConstruct
public void start() {
executor = Executors.newFixedThreadPool(properties.getUdp().getThreadPoolSize());
Thread serverThread = new Thread(this::listen, "udp-server-thread");
serverThread.setDaemon(true);
serverThread.start();
}


private void listen() {
try {
socket = new DatagramSocket(properties.getUdp().getPort());
log.info("UDP server started on port {}", properties.getUdp().getPort());


while (running) {
byte[] buffer = new byte[properties.getUdp().getBufferSize()];
DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
socket.receive(packet);


byte[] packetData = Arrays.copyOf(packet.getData(), packet.getLength());
executor.submit(() -> processPacket(packetData));
}
} catch (SocketException e) {
log.error("Failed to start UDP server", e);
} catch (IOException e) {
if (running) {
log.error("UDP receive error", e);
}
}
}


private void processPacket(byte[] packetData) {
String receivedData = new String(packetData, StandardCharsets.UTF_8);
String[] messages = receivedData.split("\\$");
for (String message : messages) {
if (message.isBlank()) continue;
String fullMessage = "$" + message.trim();
parserService.parse(fullMessage).ifPresent(deviceRegistryService::saveReading);
}
}


@PreDestroy
public void stop() {
running = false;
if (socket != null && !socket.isClosed()) {
socket.close();
}
if (executor != null) {
executor.shutdown();
}
}
}