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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.beans.factory.annotation.Autowired;

import se.fredin.jayplayer.domain.Track;
import se.fredin.jayplayer.service.TrackService;


public class Display extends JFrame {
	
	@Autowired
	private TrackService trackService;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> playListDlm, tracksListDlm;
	private JList<String> tracksDisplay, playListDisplay;
	
	private JSlider volumeSlider;
	private JProgressBar progressBar;
	
	private JMenuItem newPlayListItem, loadPlaylistItem, loadMusicItem, quitItem;
	private JMenuItem clearTracksItem, clearPlayListsItem;
	private JTextField statusField;
	
	private JButton previousButton, playPauseButton, nextButton;
	private JButton shuffleButton, repeatButton;
	
	private JLabel timeLabel;
	
	

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
		
		previousButton = new JButton();
		previousButton.setPreferredSize(buttonDimension);
		previousButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/JayPlayer/jayplayer-jar/icons/backwards.png"));
		buttonsAndVolumePanel.add(previousButton);
				
		playPauseButton = new JButton();
		playPauseButton.setPreferredSize(buttonDimension);
		playPauseButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/JayPlayer/jayplayer-jar/icons/play.png"));
		buttonsAndVolumePanel.add(playPauseButton);
		
		nextButton = new JButton();
		nextButton.setPreferredSize(buttonDimension);
		nextButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/JayPlayer/jayplayer-jar/icons/forward.png"));
		buttonsAndVolumePanel.add(nextButton);
		
		volumeSlider = new JSlider();
		volumeSlider.setValue(1);
		volumeSlider.setMaximum(1);
		volumeSlider.setPreferredSize(new Dimension(100, 30));
		buttonsAndVolumePanel.add(volumeSlider);
		
		JPanel trackProgressPanel = new JPanel();
		bottomPanel.add(trackProgressPanel);
		trackProgressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		timeLabel = new JLabel("0.0");
		timeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		trackProgressPanel.add(timeLabel);
		
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(300, 30));
		trackProgressPanel.add(progressBar);
		
		JPanel shuffleRepeatPanel = new JPanel();
		bottomPanel.add(shuffleRepeatPanel);
		shuffleRepeatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		shuffleButton = new JButton();
		shuffleButton.setPreferredSize(buttonDimension);
		shuffleButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/JayPlayer/jayplayer-jar/icons/shuffle.png"));
		shuffleRepeatPanel.add(shuffleButton);
		
		repeatButton = new JButton();
		repeatButton.setPreferredSize(buttonDimension);
		repeatButton.setIcon(new ImageIcon("/home/johan/workspace/MediaPlayer/JayPlayer/jayplayer-jar/icons/repeat.png"));
		shuffleRepeatPanel.add(repeatButton);
		
		JPanel playListPanel = new JPanel();
		playListPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(playListPanel, BorderLayout.WEST);
		
		playListDlm = new DefaultListModel<String>();
		playListPanel.setLayout(new BoxLayout(playListPanel, BoxLayout.PAGE_AXIS));
		
		JLabel playListsLabel = new JLabel("Playlists");
		playListsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playListPanel.add(playListsLabel);
		playListDisplay = new JList<String>(playListDlm);
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
		tracksDisplay = new JList<String>(tracksListDlm);
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
	
	
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		JMenu editFileMenu = new JMenu("Edit");
		newPlayListItem = new JMenuItem("New Playlist");
		loadPlaylistItem = new JMenuItem("Load Playlist");
		loadMusicItem = addMenuItemListener(MenuActions.LOAD_MUSIC); 
		quitItem = addMenuItemListener(MenuActions.QUIT);
		clearTracksItem = addMenuItemListener(MenuActions.CLEAR_TRACKS);
		clearPlayListsItem = new JMenuItem("Clear Playlists");
				
		
		fileMenu.add(newPlayListItem);
		fileMenu.add(loadPlaylistItem);
		fileMenu.add(loadMusicItem);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		
		editFileMenu.add(clearTracksItem);
		editFileMenu.add(clearPlayListsItem);
		menuBar.add(editFileMenu);
				
	}

	public JMenuItem addMenuItemListener(final MenuActions action) {
		JMenuItem item = new JMenuItem(action.getActionName());
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(action) {
				case LOAD_MUSIC:
					JFileChooser openFile = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Music", "mp3", "wav", "MP3", "WAV");
					openFile.setFileFilter(filter);
					openFile.setMultiSelectionEnabled(true);
					int returnType = openFile.showOpenDialog(null);
					if(returnType == JFileChooser.APPROVE_OPTION) {
						File[] selectedFiles = openFile.getSelectedFiles();
						for(File file : selectedFiles) {
							Track track = new Track(file.getAbsolutePath());
							trackService.addTrack(track);
							tracksListDlm.addElement(track.getTitle());
						}
					}
					break;
				case QUIT:
					System.exit(0);
					break;
				case CLEAR_TRACKS:
					tracksListDlm.removeAllElements();
					trackService.clearTrackList();
					break;
				default:
					break;
				}
			}
		});
		return item;
	}
	
	
}
