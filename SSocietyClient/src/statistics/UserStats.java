package statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import logged.Home;
import util.Screen;

import java.io.Console;

public class UserStats {//this class allows to calculate user statistics and present the results to the user
	private static Console cons = System.console(); //allows to use the java console
	
	private String username;//variable that saves the user username
	private String pathHome = System.getProperty("user.home"); //string that saves the path to user home folder
	private String userFolder; //String that saves the that to the user folder
	
	static final String BOLD = "\033[1m"; //starts printing in bold
	static final String RESET = "\033[0m"; //stops printing in bold
	
	public UserStats(String u)
	{//constructor that initializes the variables above
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/AllUsers/" + username;
	}
	
	public void displayStats() throws Exception
	{//what the user sees when opens the statistics screen
		//he can only look up the statistics and exit to the homescreen
		System.out.println("-------------------------");
		System.out.println(BOLD + "Your statistics" + RESET);
		System.out.println("-------------------------");
		
		System.out.println("Likes have given on others posts: " + userGivenLikes()); //likes that the user has given in posts
		System.out.println("Number of posts you have published: " + userTotalPosts()); //total posts the user has posted
		System.out.println("Number of topics you subscribe: " + totalSubscriptions()); //total topics the user subscribes
		
		System.out.println();
		cons.readLine("Press Enter to return..."); //return to the user homescreen
		Home back = new Home(username);
		Screen.clear();
		back.homeScreen();
		return;
		
	}
	
	public int userGivenLikes() throws IOException
	{//method that calculates the likes the user has given
		int totalLikes = 0; //variable that saves the number of likes
		
		File readGivenLikes = new File(userFolder + "/likes.txt"); //reads likes in the likes file in the user folder
		FileReader read = new FileReader(readGivenLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		String line = "";
		while (line != null)
		{//each new line read corresponds to one more like
			line = readFromFile.readLine();
			if(line != null)
				totalLikes++; //increments total likes variable
		}
		
		readFromFile.close();
		return totalLikes; //return total number of likes
	}
	
	public int userTotalPosts() throws IOException
	{//total number of posts written by the user
		int totalPosts = 0; //variable that saves the total number of posts written by the user
		
		File readGivenLikes = new File(userFolder + "/posts.txt"); //reads the posts file in user folder
		FileReader read = new FileReader(readGivenLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		String line = "";
		while (line != null)
		{//each line read corresponds to one more post
			line = readFromFile.readLine();
			if(line != null)
				totalPosts++; //increments total posts variable
		}
		
		
		readFromFile.close();
		return totalPosts; //returns the number of posts
	}
	
	public int totalSubscriptions() throws IOException
	{//this method calculates the total number of topics the user subscribes
		int totalSubscriptions = 0; //variable that saves the total number of subscriptions
		
		File readGivenLikes = new File(userFolder + "/subscriptions.txt"); //reads subscriptions file in user folder
		FileReader read = new FileReader(readGivenLikes);
		BufferedReader readFromFile = new BufferedReader(read);
		
		String line = "";
		while (line != null)
		{//each line corresponds to a topics the user subscribes
			line = readFromFile.readLine();
			if(line != null)
				totalSubscriptions++; //increments the number of subscriptions variable
		}
		
		readFromFile.close();
		return totalSubscriptions; //returns number of subscriptions
	}

}
