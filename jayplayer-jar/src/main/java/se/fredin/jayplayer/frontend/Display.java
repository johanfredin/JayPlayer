package se.fredin.jayplayer.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javafx.scene.input.KeyCode;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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
import se.fredin.jayplayer.utils.IconLoader;

import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Display extends JFrame {
	
	@Autowired
	private TrackService trackService;
	
	private IconLoader iconLoader;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> playListDlm, tracksListDlm;
	private JList<String> tracksDisplay, playListDisplay;
	private JSlider volumeSlider;
	private JProgressBar progressBar;
	private JMenuItem newPlayListItem, loadMusicItem, quitItem;
	private JMenuItem clearTracksItem, clearPlayListsItem;
	private JTextField statusField;
	private JButton previousButton, playPauseButton, nextButton;
	private JButton shuffleButton, repeatButton;
	private JLabel timeLabel, totalDuration;
	
	public Display() {
		setTitle("JayPlayer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		iconLoader = new IconLoader();
		
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
		
		previousButton = new JButton(iconLoader.getIcon(iconLoader.BACKWARDS));
		previousButton.setEnabled(false);
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
				trackService.previousTrack();
				tracksDisplay.setSelectedIndex(trackService.getId());
				statusField.setText(trackService.getStatus());
			}
		});
		previousButton.setPreferredSize(buttonDimension);
		buttonsAndVolumePanel.add(previousButton);
				
		playPauseButton = new JButton(iconLoader.getIcon(iconLoader.PLAY));
		playPauseButton.setEnabled(false);
		playPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playPauseButton.getIcon() == iconLoader.getIcon(iconLoader.PLAY)) {
					tracksDisplay.setSelectedIndex(trackService.getId());
					trackService.playTrack(trackService.getId());
					playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
				} else {
					trackService.stop();
					playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PLAY));
				}
				statusField.setText(trackService.getStatus());
			}
		});
		playPauseButton.setPreferredSize(buttonDimension);
		buttonsAndVolumePanel.add(playPauseButton);
		
		nextButton = new JButton(iconLoader.getIcon(iconLoader.FORWARD));
		nextButton.setEnabled(false);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
				trackService.nextTrack();
				tracksDisplay.setSelectedIndex(trackService.getId());
				statusField.setText(trackService.getStatus());
			}
		});
		nextButton.setPreferredSize(buttonDimension);
		buttonsAndVolumePanel.add(nextButton);
		
		volumeSlider = new JSlider();
		volumeSlider.setEnabled(false);
		volumeSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				trackService.setVolume(volumeSlider.getValue() * 0.01f);
				statusField.setText("Volume: " + volumeSlider.getValue());
			}
		});
		volumeSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				trackService.setVolume(volumeSlider.getValue() * 0.01f);
				statusField.setText("Volume: " + volumeSlider.getValue());
			}
		});
		volumeSlider.setValue(100);
		volumeSlider.setPreferredSize(new Dimension(100, 30));
		buttonsAndVolumePanel.add(volumeSlider);
		
		JPanel trackProgressPanel = new JPanel();
		bottomPanel.add(trackProgressPanel);
		trackProgressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		timeLabel = new JLabel("0:00");
		timeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		trackProgressPanel.add(timeLabel);
		
		progressBar = new JProgressBar();
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO: Skip forward/backward in track
			}
		});
		progressBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//TODO: Skip forward/backward in track
			}
		});
		progressBar.setPreferredSize(new Dimension(300, 30));
		trackProgressPanel.add(progressBar);
		
		totalDuration = new JLabel("3:25");
		trackProgressPanel.add(totalDuration);
		
		JPanel shuffleRepeatPanel = new JPanel();
		bottomPanel.add(shuffleRepeatPanel);
		shuffleRepeatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		shuffleButton = new JButton(iconLoader.getIcon(iconLoader.SHUFFLE));
		shuffleButton.setEnabled(false);
		shuffleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(shuffleButton.getIcon() == iconLoader.getIcon(iconLoader.SHUFFLE)) {
					shuffleButton.setIcon(iconLoader.getIcon(iconLoader.SHUFFLE_ENABLED));
					trackService.shuffle(true);
				}
				else {
					shuffleButton.setIcon(iconLoader.getIcon(iconLoader.SHUFFLE));
					trackService.shuffle(false);
				}
				statusField.setText(trackService.getStatus());
			}
		});
		shuffleButton.setPreferredSize(buttonDimension);
		shuffleRepeatPanel.add(shuffleButton);
		
		repeatButton = new JButton(iconLoader.getIcon(iconLoader.REPEAT));
		repeatButton.setEnabled(false);
		repeatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(repeatButton.getIcon() == iconLoader.getIcon(iconLoader.REPEAT)) {
					trackService.repeat(true);
					repeatButton.setIcon(iconLoader.getIcon(iconLoader.REPEAT_ENABLED));
				} else {
					trackService.repeat(false);
					repeatButton.setIcon(iconLoader.getIcon(iconLoader.REPEAT));
				}
				statusField.setText(trackService.getStatus());
			}
		});
		repeatButton.setPreferredSize(buttonDimension);
		shuffleRepeatPanel.add(repeatButton);
		
		JPanel playListPanel = new JPanel();
		playListPanel.setBackground(new Color(105, 105, 105));
		playListPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(playListPanel, BorderLayout.WEST);
		
		playListDlm = new DefaultListModel<String>();
		playListPanel.setLayout(new BoxLayout(playListPanel, BoxLayout.PAGE_AXIS));
		
		JLabel playListsLabel = new JLabel("Playlists");
		playListsLabel.setForeground(new Color(211, 211, 211));
		playListsLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		playListsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playListPanel.add(playListsLabel);
		playListDisplay = new JList<String>(playListDlm);
		playListDisplay.setBackground(new Color(0, 0, 0));
		playListDisplay.setBorder(new EmptyBorder(25, 0, 0, 0));
		
		JScrollPane playListScroller = new JScrollPane(playListDisplay);
		playListScroller.setPreferredSize(new Dimension(100, 400));
		playListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playListPanel.add(playListScroller);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(105, 105, 105));
		mainPanel.setBorder(new EmptyBorder(10, 20, 20, 10));
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JLabel tracksLabel = new JLabel("Tracks");
		tracksLabel.setForeground(new Color(211, 211, 211));
		tracksLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		tracksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(tracksLabel);
		
		tracksListDlm = new DefaultListModel<String>();
		tracksDisplay = new JList<String>(tracksListDlm);
		tracksDisplay.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!trackService.isEmpty()) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						trackService.playTrack(trackService.getId());
						statusField.setText(trackService.getStatus());
						break;
					case KeyEvent.VK_DELETE:
						trackService.deleteTrack(trackService.getId());
						statusField.setText(trackService.getStatus());
						tracksListDlm.remove(trackService.getId());
						break;
					case KeyEvent.VK_UP:
						if(trackService.getId() > 0) 
							tracksDisplay.setSelectedIndex(trackService.previousId());
						break;
					case KeyEvent.VK_DOWN:
						if(trackService.getId() < trackService.getTrackAmount() - 1) 
							tracksDisplay.setSelectedIndex(trackService.nextId());
						break;
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				trackService.setWasPlayed(false);
			}
		});
		tracksDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!trackService.isEmpty()) {
					trackService.setId(tracksDisplay.getSelectedIndex());
					if(e.getClickCount() >= 2) {
						playPauseButton.setIcon(iconLoader.getIcon(iconLoader.PAUSE));
						trackService.playTrack(trackService.getId());
						statusField.setText(trackService.getStatus());
					}
				}
			}
		});
		tracksDisplay.setFont(new Font("Impact", Font.PLAIN, 12));
		tracksDisplay.setBackground(new Color(0, 0, 0));
		tracksDisplay.setForeground(new Color(0, 255, 0));
		tracksDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));
		JScrollPane tracksScroller = new JScrollPane(tracksDisplay);
		tracksScroller.setPreferredSize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight() - tracksLabel.getHeight()));
		mainPanel.add(tracksScroller);
		
		JPanel topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		statusField = new JTextField();
		statusField.setHorizontalAlignment(SwingConstants.CENTER);
		statusField.setFont(new Font("Dialog", Font.PLAIN, 12));
		statusField.setEditable(false);
		statusField.setText("Welcome!");
		topPanel.add(statusField);
		statusField.setColumns(69);
			
		setVisible(true);
	}
	
	private void enableButtons(boolean enable) {
		playPauseButton.setEnabled(enable);
		nextButton.setEnabled(enable);
		previousButton.setEnabled(enable);
		volumeSlider.setEnabled(enable);
		shuffleButton.setEnabled(enable);
		repeatButton.setEnabled(enable);
	}
	
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		JMenu editFileMenu = new JMenu("Edit");
		newPlayListItem = new JMenuItem("New Playlist");
		loadMusicItem = addMenuItemListener(MenuActions.LOAD_MUSIC); 
		quitItem = addMenuItemListener(MenuActions.QUIT);
		clearTracksItem = addMenuItemListener(MenuActions.CLEAR_TRACKS);
		clearPlayListsItem = new JMenuItem("Clear Playlists");
				
		
		fileMenu.add(newPlayListItem);
		fileMenu.add(loadMusicItem);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		
		//TODO: Uncomment later, some weird thing with windowbuilder editor when theese are there
//		editFileMenu.add(clearTracksItem);
//		editFileMenu.add(clearPlayListsItem);
		menuBar.add(editFileMenu);
	}

	private JMenuItem addMenuItemListener(final MenuActions action) {
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
						trackService.setId(0);
						tracksDisplay.setSelectedIndex(trackService.getId());
						statusField.setText("Added " + trackService.getTrackAmount() + " tracks");
						enableButtons(true);
					}
					break;
				case QUIT:
					trackService.stop();
					System.exit(0);
					break;
				case CLEAR_TRACKS:
					trackService.stop();
					tracksListDlm.removeAllElements();
					trackService.clearTrackList();
					enableButtons(false);
					statusField.setText(trackService.getStatus());
					break;
				default:
					break;
				}
			}
		});
		return item;
	}


	
	
	
	
	
}
