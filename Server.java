package musicudp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Server extends JFrame {
    private JButton browseButton;
    private JButton sendButton;
    private JTextArea chatTextArea;

    private DatagramSocket socket;
    private InetAddress clientIP;
    private int clientPort;

    public Server() {
        setTitle("Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        browseButton = new JButton("Browse");
        sendButton = new JButton("Send");
        chatTextArea = new JTextArea();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(browseButton);
        buttonPanel.add(sendButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(chatTextArea), BorderLayout.CENTER);

        add(panel);

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseFile();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFile();
            }
        });

        try {
            socket = new DatagramSocket(9876);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    private void browseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            chatTextArea.append("Selected file: " + selectedFile.getAbsolutePath() + "\n");
        }
    }

    private void sendFile() {
        try {
            // Trước khi gửi file, chờ yêu cầu kết nối từ máy khách
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            // Lấy địa chỉ IP và cổng của máy khách
            clientIP = receivePacket.getAddress();
            clientPort = receivePacket.getPort();

            File file = new File("D:\\App\\MusicUDP_VanManh\\Server\\musicserver.mp3");
            FileInputStream fis = new FileInputStream(file);

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
            chatTextArea.append("File sent successfully.\n");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Server server = new Server();
                server.setVisible(true);
            }
        });
    }
}
