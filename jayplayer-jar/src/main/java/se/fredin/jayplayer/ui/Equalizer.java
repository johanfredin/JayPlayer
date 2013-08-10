package se.fredin.jayplayer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import se.fredin.jayplayer.service.TrackService;

public class Equalizer extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	public Equalizer(final TrackService trackService) {
		setResizable(false);
		setTitle("Equalizer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 129);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel bottompanel = new JPanel();
		contentPane.add(bottompanel, BorderLayout.SOUTH);
		
		JButton saveButton = new JButton("Save Settings");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Save settings to playerSettings.JSON
			}
		});
		bottompanel.add(saveButton);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Reset to standard settings
			}
		});
		bottompanel.add(resetButton);
		
		Panel panel = new Panel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Playback Rate");
		lblNewLabel_1.setForeground(UIManager.getColor("Button.background"));
		panel.add(lblNewLabel_1);
		
		final JSlider playBackRateSlider = new JSlider();
		playBackRateSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				trackService.setPlayBackRate(playBackRateSlider.getValue());
			}
		});
		playBackRateSlider.setValue(1);
		playBackRateSlider.setMinimum(-4);
		playBackRateSlider.setMaximum(6);
		playBackRateSlider.setBackground(Color.GRAY);
		panel.add(playBackRateSlider);
		
		JLabel lblNewLabel = new JLabel("Balance           ");
		lblNewLabel.setForeground(UIManager.getColor("Button.background"));
		panel.add(lblNewLabel);
		
		final JSlider balanceSlider = new JSlider();
		balanceSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				trackService.setBalance(balanceSlider.getValue() * 0.01f);
			}
		});
		balanceSlider.setBackground(Color.GRAY);
		panel.add(balanceSlider);
		setVisible(true);
	}

}
