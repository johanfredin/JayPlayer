package se.fredin.jayplayer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static final String TEST_DIR = "/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/tracks/";
	
	public static void main( String[] args ) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext_mock.xml");
		context.getBean("display");
	}
}
