package MusicUDP;

import java.io.*;
import java.net.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Client {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            byte[] receiveData = new byte[1024];

            InetAddress serverIP = InetAddress.getLocalHost();
            int serverPort = 9876;

            // Gửi yêu cầu kết nối đến server
            DatagramPacket sendPacket = new DatagramPacket(new byte[1024], 0, serverIP, serverPort);
            clientSocket.send(sendPacket);
            
            // Nhận dữ liệu nhạc từ server
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                baos.write(receivePacket.getData(), 0, receivePacket.getLength());
                
                // Kiểm tra xem đã nhận đủ dữ liệu chưa
                if (receivePacket.getLength() < 1024) {
                    break;
                }
            }
            clientSocket.close();

            // Phát nhạc từ dữ liệu nhận được
            byte[] audioData = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            Player player = new Player(bais);
            player.play();
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
}