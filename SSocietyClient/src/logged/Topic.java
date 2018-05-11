package logged;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import util.Screen;

public class Topic {
	
	private static Console cons = System.console();
	
	private String username;
	private String pathHome = System.getProperty("user.home");
	private String allTopics = pathHome + "/SSociety_data/Topics";
	
	ArrayList<String> userTopics = new ArrayList<String>();
	
	Topic (ArrayList<String> a, String u)
	{
		userTopics = a;
		username = u;
	}
	
	public void openTopic(int topic) throws IOException
	{
		File commentsFolder = new File(allTopics + "/" + userTopics.get(topic) + "/Comments");
		System.out.println(userTopics.get(topic));
		
		File[] comments = commentsFolder.listFiles();
		
		int numberOfComments = 0;
		for (File file: comments) {
	        if (file.isDirectory())
	            numberOfComments++;
	    }
		
		for (int i = 1; i <= numberOfComments; i++) 
		{
			FileReader readFile = null;
			try { readFile = new FileReader(allTopics + "/" + userTopics.get(topic) + "/Comments/" + i + "/comment.txt"); } //i é a pasta que contem o comentario 
			catch (FileNotFoundException e) {} 
			BufferedReader displayComment = new BufferedReader(readFile);
			
			System.out.println(i + " - " + displayComment.readLine());
			System.out.println();
			
			String line = "";
			while (line != null)
			{
				line = displayComment.readLine();
				if(line != null)
					System.out.println(line);
			}
			
			displayComment.close();
			
			try { readFile = new FileReader(allTopics + "/" + userTopics.get(topic) + "/Comments/" + i + "/likes.txt"); } //i é a pasta que contem o comentario 
			catch (FileNotFoundException e) {} 
			BufferedReader likes = new BufferedReader(readFile);
			
			String users = "";
			int numberOfLikes = 0;
			while (users != null)
			{
				users = likes.readLine();
				if (users != null)
					numberOfLikes++;
			}
			
			likes.close();
			System.out.println("Comment likes: " + numberOfLikes);
			System.out.println();
			System.out.println();
		}
	}
}
