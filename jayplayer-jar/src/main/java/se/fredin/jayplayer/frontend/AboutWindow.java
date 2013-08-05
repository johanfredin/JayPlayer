package se.fredin.jayplayer.frontend;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import se.fredin.jayplayer.App;

public class AboutWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AboutWindow() {
		setTitle("About");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("JayPlayer " + App.JAYPLAYER_VERSION + "\n copyright Johan Fredin 2013");
		panel.add(textArea);
		
		setVisible(true);
	}

}
