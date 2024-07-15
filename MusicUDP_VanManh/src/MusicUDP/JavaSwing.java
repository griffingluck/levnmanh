package MusicUDP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class JavaSwing extends JFrame {
	public class MyFrame extends JFrame {
		public MyFrame() {
		createGUI();
		}

		private void createGUI() {
		// Tạo bố cục và các thành phần trong giao diện
		// Gán các thuộc tính và xử lý sự kiện

		setTitle("Giao diện đồ họa");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		}
	}
}