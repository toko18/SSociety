package statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import logged.Home;
import util.Screen;

import java.io.Console;

public class UserStats {
	private static Console cons = System.console();
	
	private String username;
	private String pathHome = System.getProperty("user.home");
	private String userFolder;
	
	static final String BOLD = "\033[1m";
	static final String RESET = "\033[0m";
	
	public UserStats(String u)
	{
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/AllUsers/" + username;
	}
	
	public void displayStats() throws Exception
	{
		System.out.println("-------------------------");
		System.out.println(BOLD + "Your statistics" + RESET);
		System.out.println("-------------------------");
		
		System.out.println("Likes have given on others posts: " + userGivenLikes());
		System.out.println("Number of posts you have published: " + userTotalPosts());
		System.out.println("Number of topics you subscribe: " + totalSubscriptions());
		
		System.out.println();
		cons.readLine("Press Enter to return...");
		Home back = new Home(username);
		Screen.clear();
		back.homeScreen();
		return;
		
	}
	
	public int userGivenLikes() throws IOException
	{
		int totalLikes = 0;
		
		File readGivenLikes = new File(userFolder + "/likes.txt");
		FileReader read = new FileReader(readGivenLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		String line = "";
		while (line != null)
		{
			line = readFromFile.readLine();
			if(line != null)
				totalLikes++;
		}
		
		readFromFile.close();
		return totalLikes;
	}
	
	public int userTotalPosts() throws IOException
	{
		int totalPosts = 0;
		
		File readGivenLikes = new File(userFolder + "/posts.txt");
		FileReader read = new FileReader(readGivenLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		String line = "";
		while (line != null)
		{
			line = readFromFile.readLine();
			if(line != null)
				totalPosts++;
		}
		
		readFromFile.close();
		return totalPosts;
	}
	
	public int totalSubscriptions() throws IOException
	{
		int totalSubscriptions = 0;
		
		File readGivenLikes = new File(userFolder + "/subscriptions.txt");
		FileReader read = new FileReader(readGivenLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		String line = "";
		while (line != null)
		{
			line = readFromFile.readLine();
			if(line != null)
				totalSubscriptions++;
		}
		
		readFromFile.close();
		return totalSubscriptions;
	}

}
