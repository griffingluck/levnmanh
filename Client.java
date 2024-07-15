package musicudp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Client extends JFrame {
    private JButton receiveButton;
    private JTextArea chatTextArea;

    private DatagramSocket clientSocket;

    public Client() {
        setTitle("Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        receiveButton = new JButton("Receive");
        chatTextArea = new JTextArea();

        // Đặt phông chữ cho JTextArea thành Arial
        Font font = new Font("Arial", Font.PLAIN, 12); // Thay đổi kích thước phù hợp
        chatTextArea.setFont(font);

        panel.add(receiveButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(chatTextArea), BorderLayout.CENTER);

        add(panel);

        receiveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiveFile();
            }
        });

        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    private void receiveFile() {
        try {
            byte[] receiveData = new byte[1024];

            InetAddress serverIP = InetAddress.getLocalHost();
            int serverPort = 9876;

            // Gửi yêu cầu kết nối đến server
            DatagramPacket sendPacket = new DatagramPacket(new byte[1024], 0, serverIP, serverPort);
            clientSocket.send(sendPacket);

            // Nhận tên file mới từ server
            DatagramPacket fileNamePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(fileNamePacket);
            String newFileName = new String(fileNamePacket.getData()).trim();
            chatTextArea.append("Received file name\n");

            // Tạo file mới để nhận dữ liệu
            FileOutputStream fos = new FileOutputStream("D:\\App\\MusicUDP_VanManh\\Client\\Recevie_Music.mp3");

            // Nhận dữ liệu nhạc từ server và ghi vào file
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                fos.write(receivePacket.getData(), 0, receivePacket.getLength());

                // Kiểm tra xem đã nhận đủ dữ liệu chưa
                if (receivePacket.getLength() < 1024) {
                    break;
                }
            }
            fos.close();
            
            chatTextArea.append("Bài hát musicserver.mp3 đã phát thành công.\n");

            // Phát nhạc từ tệp tin vừa nhận được
            playAudio("D:\\App\\MusicUDP_VanManh\\Client\\Recevie_Music.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playAudio(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            AdvancedPlayer player = new AdvancedPlayer(fis);
            player.play();
        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Client client = new Client();
                client.setVisible(true);
            }
        });
    }
}
