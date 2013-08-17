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
import java.awt.Toolkit;
import java.awt.Font;

public class Equalizer extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JSlider balanceSlider, playBackRateSlider;

	public Equalizer(final TrackService trackService) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Equalizer.class.getResource("/images/desktop_small.png")));
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
		
		JButton resetButton = new JButton("Restore Defauts");
		
		bottompanel.add(resetButton);
		
		Panel panel = new Panel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Playback Rate");
		lblNewLabel_1.setFont(new Font("Impact", Font.BOLD, 12));
		lblNewLabel_1.setForeground(UIManager.getColor("Button.background"));
		panel.add(lblNewLabel_1);
		
		playBackRateSlider = new JSlider();
		playBackRateSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				trackService.setPlayBackRate(playBackRateSlider.getValue() * 0.1f);
			}
		});
		playBackRateSlider.setValue(10);
		playBackRateSlider.setMaximum(30);
		playBackRateSlider.setBackground(Color.GRAY);
		panel.add(playBackRateSlider);
		
		JLabel lblNewLabel = new JLabel("Balance            ");
		lblNewLabel.setFont(new Font("Impact", Font.BOLD, 12));
		lblNewLabel.setForeground(UIManager.getColor("Button.background"));
		panel.add(lblNewLabel);
		
		balanceSlider = new JSlider();
		balanceSlider.setValue(0);
		balanceSlider.setMinimum(-100);
		balanceSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				trackService.setBalance(balanceSlider.getValue() * 0.01f);
			}
		});
		balanceSlider.setBackground(Color.GRAY);
		panel.add(balanceSlider);
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				balanceSlider.setValue(0);
				playBackRateSlider.setValue(10);
				trackService.setBalance(balanceSlider.getValue() * 0.01f);
				trackService.setPlayBackRate(playBackRateSlider.getValue() * 0.1f);
			}
		});
		
		setVisible(true);
	}
	
	

}
