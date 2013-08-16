package se.fredin.jayplayer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import se.fredin.jayplayer.Jayplayer;

public class AboutWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWindow.class.getResource("/res/desktop_small.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("About");
		setBounds(100, 100, 409, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AboutWindow.class.getResource("/res/logo_small.png")));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("JayPlayer v." + Jayplayer.JAYPLAYER_VERSION);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_2.setBounds(143, 13, 139, 15);
		lblNewLabel_2.setForeground(new Color(211,211,211));
		panel_1.add(lblNewLabel_2);
		
		JLabel linkLabel = new JLabel("Git Repository");
		linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		linkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/johanfredin/JayPlayer"));
				} catch(Exception ex) {}
			}
		});
		linkLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		linkLabel.setForeground(new Color(0, 51, 255));
		linkLabel.setBounds(143, 40, 116, 15);
		panel_1.add(linkLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_1 = new JLabel( (char)169 +  " 2013 Johan Fredin");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1.setForeground(new Color(211,211,211));
		panel_2.add(lblNewLabel_1);
		
		setVisible(true);
	}
}
