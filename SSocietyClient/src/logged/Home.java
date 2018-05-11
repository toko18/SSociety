package logged;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import util.Screen;

public class Home 
{
	private static Console cons = System.console();
	
	private String username;
	private String pathHome = System.getProperty("user.home");
	private String userFolder;
	
	public Home(String u) throws IOException
	{
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/AllUsers/" + username;
	}
	
	public void homeScreen() throws IOException
	{
		int chosenOption = 0;
		
		while(true)
		{
			System.out.println("Welcome, " + username + "!");
			System.out.println();
			System.out.println("1 - Open Feed");
			System.out.println("2 - My topics");
			System.out.println("3 - Subscribe new topic");
			System.out.println("4 - Log out");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0;}
			
			if (chosenOption == 1)
			{
				Screen.clear();
				this.displayFeed();
				return;
			}
			else if (chosenOption == 2)
			{
				Screen.clear();
				this.displayTopics();
				return;
			}
			else 
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println();
			}
		}
	}
	
	public void displayFeed() throws IOException
	{
	     int chosenOption = 0;
		 ArrayList<String> userTopics = new ArrayList<String>();
		
		 FileReader subscriptions = null;
		 try { subscriptions = new FileReader(userFolder + "/subscriptions.txt"); } 
		 catch (FileNotFoundException e) {} 
		
		 BufferedReader getUserTopics = new BufferedReader(subscriptions);
		 
		 String line = "";
			
		 while (line != null)
		 {
			 line = getUserTopics.readLine();
			 if (line != null)
			 {
			   	userTopics.add(line);
			 }
		 }
	 
		 getUserTopics.close();
		 
		 System.out.println("Feed");
		 System.out.println();
		 
		 Topic userFeed = new Topic(userTopics, username); 
		 for (int i = 0; i < userTopics.size(); i++)
		 {
			 userFeed.openTopic(i);
		 }
		 
		 while (true)
		 {
			 System.out.println("1 - Back");
			 try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			 catch(Exception e) { chosenOption = 0;}
			 
			 if (chosenOption == 1) 
			 {
				 Screen.clear();
				 this.homeScreen();
				 return;
			 }
			 else
			 {
				 Screen.clear();
				 System.out.println("Invalid input!");
				 System.out.println();
			 }
		 }
	}
	
	public void displayTopics() throws IOException
	{
		int n = 0;
		int chosenOption = 0;
		ArrayList<String> userTopics = new ArrayList<String>();
		
		FileReader subscriptions = null;
		try { subscriptions = new FileReader(userFolder + "/subscriptions.txt"); } 
		catch (FileNotFoundException e) {} 
		
		BufferedReader getUserTopics = new BufferedReader(subscriptions);
		
		String line = "";
		
		while (line != null)
		{
			line = getUserTopics.readLine();
			if (line != null)
			{
				n++;
				userTopics.add(line);
				System.out.println(n + "- " + line);
			}
		}
		
		getUserTopics.close();
		
		if (n == 0)
		{
			System.out.println("You are not subscribed to any topic!");
			cons.readLine("Press Enter to continue...");
			Screen.clear();
			this.homeScreen();
			return; //ainda tenho que ver se este return aqui faz alguma coisa
		}
		
		System.out.println();
		n++;
		System.out.println(n + " - Back");
		
		while (true)
		{
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0;}
			
			if (chosenOption >= 1 && chosenOption < n)
			{
				Screen.clear();
				
				Topic chosenTopic = new Topic(userTopics, username);
				chosenTopic.openTopic(chosenOption - 1);
			}
			else if (chosenOption == n)
			{
				Screen.clear();
				this.homeScreen();
				return;
			}
		}
	}
}
