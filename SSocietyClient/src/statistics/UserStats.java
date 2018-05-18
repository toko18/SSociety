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
	
	public UserStats(String u)
	{
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/AllUsers/" + username;
	}
	
	public static void statsCounter()
	{
		//total de likes nos posts deste utilizador
		//todos os posts deste utilizador
		//numero de likes que o utilizador deu
		//nuemro de subscrições do utilizador
		
	}
	
	public void displayStats() throws IOException
	{
		System.out.println("-------------------------");
		System.out.println("Your statistics");
		System.out.println("-------------------------");
		
		System.out.println("Likes you received on your posts: " + userReceivedLikes());
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
	
	public int userReceivedLikes() throws IOException
	{
		int totalLikes = 0;
		
		File readReceivedLikes = new File(userFolder + "/received likes.txt");
		FileReader read = new FileReader(readReceivedLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		totalLikes = Integer.parseInt(readFromFile.readLine());
		readFromFile.close();
		return totalLikes;
		
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
		
		File readGivenLikes = new File(userFolder + "/posts.txt");
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
