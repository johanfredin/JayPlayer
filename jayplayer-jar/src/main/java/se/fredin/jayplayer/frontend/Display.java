package se.fredin.jayplayer.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;

import se.fredin.jayplayer.domain.Track;
import se.fredin.jayplayer.service.TrackService;
import javax.swing.JTextField;


public class Display extends JFrame implements ActionListener {
	
	@Autowired
	private TrackService trackService;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> playListDlm, tracksListDlm;
	
	private JMenuItem newPlayListItem, loadPlaylistItem, loadMusicItem, quitItem;
	private JMenuItem clearTracksItem, clearPlayListsItem;
	private JTextField statusField;
	
	

	public Display() {
		setTitle("JayPlayer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		
		initMenuBar();
				
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		JPanel buttonsAndVolumePanel = new JPanel();
		bottomPanel.add(buttonsAndVolumePanel);
		buttonsAndVolumePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		Dimension buttonDimension = new Dimension(50, 30);
		
		JButton previousButton = new JButton();
		previousButton.setPreferredSize(buttonDimension);
		previousButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/icons/backwards.png"));
		buttonsAndVolumePanel.add(previousButton);
				
		JButton playPauseButton = new JButton();
		playPauseButton.setPreferredSize(buttonDimension);
		playPauseButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/icons/play.png"));
		buttonsAndVolumePanel.add(playPauseButton);
		
		JButton nextButton = new JButton();
		nextButton.setPreferredSize(buttonDimension);
		nextButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/icons/forward.png"));
		buttonsAndVolumePanel.add(nextButton);
		
		JSlider volumeSlider = new JSlider();
		volumeSlider.setValue(1);
		volumeSlider.setMaximum(1);
		volumeSlider.setPreferredSize(new Dimension(100, 30));
		buttonsAndVolumePanel.add(volumeSlider);
		
		JPanel trackProgressPanel = new JPanel();
		bottomPanel.add(trackProgressPanel);
		trackProgressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel timeLabel = new JLabel("0.0");
		timeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		trackProgressPanel.add(timeLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(300, 30));
		trackProgressPanel.add(progressBar);
		
		JPanel shuffleRepeatPanel = new JPanel();
		bottomPanel.add(shuffleRepeatPanel);
		shuffleRepeatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton shuffleButton = new JButton();
		shuffleButton.setPreferredSize(buttonDimension);
		shuffleButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/icons/shuffle.png"));
		shuffleRepeatPanel.add(shuffleButton);
		
		JButton repeatButton = new JButton();
		repeatButton.setPreferredSize(buttonDimension);
		repeatButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/icons/repeat.png"));
		shuffleRepeatPanel.add(repeatButton);
		
		JPanel playListPanel = new JPanel();
		playListPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(playListPanel, BorderLayout.WEST);
		
		playListDlm = new DefaultListModel<String>();
		playListPanel.setLayout(new BoxLayout(playListPanel, BoxLayout.PAGE_AXIS));
		
		JLabel playListsLabel = new JLabel("Playlists");
		playListsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playListPanel.add(playListsLabel);
		JList<String> playListDisplay = new JList<String>(playListDlm);
		playListDisplay.setBorder(new EmptyBorder(25, 0, 0, 0));
		
		JScrollPane playListScroller = new JScrollPane(playListDisplay);
		playListScroller.setPreferredSize(new Dimension(100, 400));
		playListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playListPanel.add(playListScroller);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JLabel tracksLabel = new JLabel("Tracks");
		tracksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(tracksLabel);
		
		tracksListDlm = new DefaultListModel<String>();
		JList<String> tracksDisplay = new JList<String>(tracksListDlm);
		tracksDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));
		JScrollPane tracksScroller = new JScrollPane(tracksDisplay);
		tracksScroller.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight() - tracksLabel.getHeight()));
		mainPanel.add(tracksScroller);
		
		JPanel topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		statusField = new JTextField();
		statusField.setFont(new Font("Dialog", Font.PLAIN, 12));
		statusField.setEditable(false);
		statusField.setText("Welcome!");
		topPanel.add(statusField);
		statusField.setColumns(63);
			
		setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public void start() {
		
	}

	
	
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		newPlayListItem = new JMenuItem("New Playlist");
		loadPlaylistItem = new JMenuItem("Load Playlist");
		loadMusicItem = new JMenuItem("Load Music");
		quitItem = new JMenuItem("Quit");
		JMenu editFileMenu = new JMenu("Edit");
		clearTracksItem = new JMenuItem("Clear Tracks");
		clearPlayListsItem = new JMenuItem("Clear Playlists");
				
		menuBar.add(fileMenu);
		fileMenu.add(newPlayListItem);
		fileMenu.add(loadPlaylistItem);
		fileMenu.add(loadMusicItem);
		fileMenu.add(quitItem);
		menuBar.add(editFileMenu);
		editFileMenu.add(clearTracksItem);
		editFileMenu.add(clearPlayListsItem);
		
		quitItem.addActionListener(this);
		loadMusicItem.addActionListener(this);
		clearTracksItem.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Menu Actions
		if(e.getSource() == loadMusicItem) {
			JFileChooser openFile = new JFileChooser();
			openFile.setMultiSelectionEnabled(true);
			openFile.showOpenDialog(this);
			File[] selectedFiles = openFile.getSelectedFiles();
				
			if(selectedFiles == null)
				return;
			try {
				for(File file : selectedFiles) {
					String fileName = openFile.getName(file);
					if(fileName.toLowerCase().endsWith("wav") || fileName.toLowerCase().endsWith("mp3")) {					
						Track track = new Track(file.getAbsolutePath());
						trackService.addTrack(track);
						tracksListDlm.addElement(track.getTitle());
					} 
					else
						return;
					}
			} catch(Exception ex) { 
				ex.printStackTrace(); 
			}
		}
		
		else if(e.getSource() == quitItem)
			System.exit(0);
		
		else if(e.getSource() == clearTracksItem) {
			tracksListDlm.removeAllElements();
			trackService.clearTrackList();
		}
	}

}
