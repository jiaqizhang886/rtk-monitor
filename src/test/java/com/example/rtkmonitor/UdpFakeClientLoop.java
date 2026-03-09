package com.example.rtkmonitor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpFakeClientLoop {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9234;

        String personnel = "$GNGGA,044929.00,2232.2248,N,11414.1188,E,4,12,0.8,23.128,M,0.0,M,0.0,*47,PERSON001,88,31,0,0,0";
        String vehicle = "$GNGGA,044930.00,2232.2258,N,11414.1198,E,4,10,0.9,25.328,M,0.0,M,0.0,*48,CAR001,76,29,0,0,0";

        DatagramSocket socket = new DatagramSocket();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                while (true) {
                    send(socket, host, port, personnel);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                while (true) {
                    send(socket, host, port, vehicle);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private static void send(DatagramSocket socket, String host, int port, String msg) throws Exception {
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                InetAddress.getByName(host),
                port
        );
        socket.send(packet);
        System.out.println("已发送: " + msg);
    }
}