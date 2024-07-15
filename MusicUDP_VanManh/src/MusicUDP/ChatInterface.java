package MusicUDP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ChatInterface extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton cancelButton;
    private JButton browserButton;

    public ChatInterface() {
        setTitle("Chat Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Chat area on the left
        chatArea = new JTextArea();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.weighty = 1.0;
        JScrollPane scrollPane = new JScrollPane(chatArea);
        mainPanel.add(scrollPane, gbc);

        // Browser button on the right
        browserButton = new JButton("Browser");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        mainPanel.add(browserButton, gbc);

        // Send button on the left
        sendButton = new JButton("Send");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        mainPanel.add(sendButton, gbc);

        // Cancel button on the right
        cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        mainPanel.add(cancelButton, gbc);

        // Message field at the bottom
        messageField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        mainPanel.add(messageField, gbc);

        // Add action listeners
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFile();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the window
                dispose();
            }
        });

        browserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    messageField.setText(filePath); // Display file path in message field
                }
            }
        });

        add(mainPanel);
    }

    private void sendFile() {
        // Get selected file path from message field
        String filePath = messageField.getText().trim();
        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a file to send.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Send file to client (Replace this with your actual sending logic)
        try {
            // Simulating sending file (replace this with your actual sending logic)
            Thread.sleep(2000); // Simulating sending process
            chatArea.append("Đã gửi thành công đến Client: " + filePath + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength()); // Scroll to bottom
            messageField.setText(""); // Clear message field after successful sending
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatInterface chatInterface = new ChatInterface();
            chatInterface.setVisible(true);
        });
    }
}

