package se.fredin.jayplayer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static final String TEST_DIR = "/home/johan/workspace/MediaPlayer/jayplayer/jayplayer-jar/tracks/";
	
	public static void main( String[] args ) {
//		try {
//		PrintWriter pw = new PrintWriter(new File(System.getProperty("user.home")) + "/settings.txt");
//		pw.println("hejsan");
//		pw.flush();
//		} catch(Exception e) {}
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
		context.getBean("display");
	}
}
