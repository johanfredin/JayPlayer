package se.fredin.jayplayer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import se.fredin.jayplayer.Jayplayer;
import java.awt.Toolkit;

public class AboutWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWindow.class.getResource("/res/desktop_small.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("About");
		setBounds(100, 100, 450, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(AboutWindow.class.getResource("/res/logo_small.png")));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(new Color(211, 211, 211));
		textArea.setFont(new Font("Impact", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setBackground(Color.GRAY);
		textArea.setText("JayPlayer v." + Jayplayer.JAYPLAYER_VERSION);
		panel_1.add(textArea);
		
		setVisible(true);
	}

}
