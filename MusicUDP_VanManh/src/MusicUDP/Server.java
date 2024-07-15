package MusicUDP;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];

            // Đọc file nhạc
            File file = new File("D:\\App\\MusicUDP_VanManh\\Server\\musicserver.mp3");
            FileInputStream fis = new FileInputStream(file);

            InetAddress clientIP;
            int clientPort;

            System.out.println("Server is running...");
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                socket.receive(receivePacket);
                clientIP = receivePacket.getAddress();
                clientPort = receivePacket.getPort();
                System.out.println("Client connected: " + clientIP + ":" + clientPort);

                // Gửi dữ liệu nhạc cho client
                byte[] sendData = new byte[1024];
                while (true) {
                    int bytesRead = fis.read(sendData);
                    if (bytesRead == -1) {
                        break;
                    }
                    DatagramPacket sendPacket = new DatagramPacket(sendData, bytesRead, clientIP, clientPort);
                    socket.send(sendPacket);
                    Thread.sleep(5); // Thời gian chờ giữa các gói
                }
                fis.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}