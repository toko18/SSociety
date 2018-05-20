package logged;

import java.util.ArrayList;
import java.util.Date;

import main.SSocietyClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import util.Screen;
import settings.AccountSettings;
import statistics.UserStats;

public class Home 
{
	private static Console cons = System.console();
	
	private String username;
	private String pathHome = System.getProperty("user.home");
	private String userFolder;
	
	static final String BOLD = "\033[1m";
	static final String RESET = "\033[0m";
	
	public Home(String u) throws IOException
	{
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/AllUsers/" + username;
	}
	
	public void homeScreen() throws Exception
	{
		int chosenOption = 0;
		
		while(true)
		{
			System.out.println("--------------------------");
			System.out.println(BOLD + "Welcome, " + username + "!" + RESET);
			System.out.println("--------------------------");
			System.out.println("1 - Open Feed");
			System.out.println("2 - My topics");
			System.out.println("3 - Subscribe new topic");
			System.out.println("4 - Most active topics");
			System.out.println("5 - Statistics");
			System.out.println("6 - Account Settings");
			System.out.println("7 - Log out");
			
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
			else if (chosenOption == 3)
			{
				Screen.clear();
				this.subscribeTopic();
				return;
			}
			else if (chosenOption == 4)
			{
				Screen.clear();
				this.showMostActiveTopics();
				return;
			}
			else if (chosenOption == 5)
			{
				Screen.clear();
				UserStats stats = new UserStats(username);
				stats.displayStats();
				return;
			}
			else if (chosenOption == 6) {
				Screen.clear();
				AccountSettings changeSettings = new AccountSettings(username);
				changeSettings.settingsScreen();
			}
			else if (chosenOption == 7)
			{
				Screen.clear();
				System.out.println("It's sad to see you go!");
				cons.readLine("Press Enter to continue...");
				Screen.clear();
				SSocietyClient.firstScreen();
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
	
	public void displayFeed() throws Exception
	{
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
				line = line.substring(0, line.length() - 2);
			   	userTopics.add(line);
			 }
		 }
	 
		 getUserTopics.close();
		 
		 Topic userFeed = new Topic(userTopics, username); 
		 while(true)
		 {
			 System.out.println("-------------------------");
			 System.out.println(BOLD + "Feed" + RESET);
			 System.out.println("-------------------------");
			 System.out.println("This is your feed. Here you can check the five most recent posts from every topic you subscribe and like some of the posts");
			 for (int i = 0; i < userTopics.size(); i++)
			 {
				 userFeed.openTopic(i, 4, 1);
			 }
			 userFeed.actionsFeed(username, 4, 1);
			 return;
		 }
	}
	
	public void displayTopics() throws Exception
	{
		while(true)
		{
			int n = 0;
			int chosenOption = 0;
			ArrayList<String> userTopics = new ArrayList<String>();
			
			System.out.println("-------------------------");
			System.out.println(BOLD + "My Topics" + RESET);
			System.out.println("-------------------------");
			System.out.println("In this section you can see a list of all the topics you subscribe an enter in one of your choice in order to performe actions: like/unlike a post, post a message, unsubscribe a topic and favorite/unfavorite a topic. Topics that appear in Bold are the ones you have favorited.");
			System.out.println("-------------------------");
			
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
					String lastTwo = line.substring(line.length() - 2, line.length());
					line = line.substring(0, line.length() - 2);
					userTopics.add(line);
					
					if(lastTwo.equals("|."))
					{
						System.out.println(n + " - " + line);
					}
					else if(lastTwo.equals("|*"))
					{
						System.out.println(BOLD + n + " - " + line + RESET);
					}
				}
			}
			
			getUserTopics.close();
			
			if (n == 0)
			{
				System.out.println("You are not subscribed to any topic!");
				cons.readLine("Press Enter to continue...");
				Screen.clear();
				this.homeScreen();
				return;
			}
			
			System.out.println();
			n++;
			System.out.println("-------------------------");
			System.out.println(n + " - Back");
			
			while (true)
			{
				try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
				catch(Exception e) { chosenOption = 0;}
				
				if (chosenOption >= 1 && chosenOption < n)
				{
					Screen.clear();
					Topic chosenTopic = new Topic(userTopics, username);
					chosenTopic.openTopic(chosenOption - 1, 1, 0); //where starts e flag
					chosenTopic.actions(username, chosenOption - 1, 1, 0);
					return;
					
				}
				else if (chosenOption == n)
				{
					Screen.clear();
					this.homeScreen();
					return;
				}
				else {
					Screen.clear();
					break;
				}
			}
		}
		
	}
	
	public void subscribeTopic() throws Exception
	{
		while(true)
		{
			System.out.println("--------------------------");
			System.out.println(BOLD + "Subscribe new Topic" + RESET);
			System.out.println("--------------------------");
			System.out.println("In this section you can see all the active topic you can subscribe to on SSociety.");
			
			int chosenTopic = 0;
			
			ArrayList<String> ssocietyTopics = new ArrayList<String>();
			
			File allTopics = new File(pathHome + "/SSociety_data/Topics/topics.txt");
			FileReader readTopics = new FileReader(allTopics);
			BufferedReader getTopics = new BufferedReader(readTopics);
			
			int n = 0;
			int i = 0;
			String line = "";
			
			while (line != null)
			{
				line = getTopics.readLine();
				if (line != null)
				{
					n++;
					ssocietyTopics.add(line);
					System.out.println("--------------------------");
					System.out.println(n + " - " + line);
					
					System.out.print("Descrição: ");
					FileReader readDescription = null;
					try 
					{ 
						File description = new File(pathHome + "/SSociety_data/Topics/" + ssocietyTopics.get(i) + "/description.txt");
						readDescription = new FileReader(description); 
					}
					catch (FileNotFoundException e) {} 
					
					BufferedReader displayDescription = new BufferedReader(readDescription);
					
					displayDescription.readLine();
					String line2 = "";
					while (line2 != null)
					{
						line2 = displayDescription.readLine();
						if(line2 != null)
							System.out.println(line2);
					}
					
					displayDescription.close();
					i++;
				}
			}
			
			getTopics.close();
			
			n++;
			System.out.println("--------------------------");
			System.out.println(n + " - Back");
			
			while(true)
			{
				try {chosenTopic = Integer.parseInt(cons.readLine("Subscribe Topic: ")); }
				catch(Exception e) {
					Screen.clear();
					System.out.println("That is not a valid topic. Please try again.");
					chosenTopic = 0;
				}
				
				if (chosenTopic >= 1 && chosenTopic < n)
				{
					
					File addUserSubscription = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					FileWriter write = new FileWriter(addUserSubscription, true);
					BufferedWriter writeToSubscriptions = new BufferedWriter(write);
					
					FileReader read = new FileReader(addUserSubscription);
					BufferedReader readSubscriptions = new BufferedReader(read);
					
					String toAdd = ssocietyTopics.get(chosenTopic - 1) + "|.";
					line = "";
					boolean add = true;
					while (line != null)
					{
						line = readSubscriptions.readLine();
						if (line != null && line.equals(toAdd))
						{
							add = false;
						}
					}
					
					if (add)
					{
						writeToSubscriptions.write(toAdd);
						writeToSubscriptions.newLine();
						
						readSubscriptions.close();
						writeToSubscriptions.close();
						
						File topicSubscriptions = new File(pathHome + "/SSociety_data/Topics/" + ssocietyTopics.get(chosenTopic - 1) + "/subscriptions.txt");
						write = new FileWriter(topicSubscriptions, true);
						writeToSubscriptions = new BufferedWriter(write);
						
						writeToSubscriptions.write(username);
						writeToSubscriptions.newLine();
					}
					readSubscriptions.close();
					writeToSubscriptions.close();
					Screen.clear();
					break;
					
				}
				else if (chosenTopic == n)
				{
					Screen.clear();
					this.homeScreen();
					return;
				}
				else {
					Screen.clear();
					break;
				}
			}
		}
	}
	
	public void showMostActiveTopics() throws Exception
	{
		ArrayList <String> topics = new ArrayList<String>();
		ArrayList <Integer> frequence = new ArrayList<Integer>();
		
		FileReader read = new FileReader(pathHome + "/SSociety_data/Topics/topics.txt");
		BufferedReader getTopic = new BufferedReader(read);
		
		String line = "";
		while(line != null)
		{
			line = getTopic.readLine();
			if (line != null)
			{
				topics.add(line);
			}
		}
		
		getTopic.close();
		
		for (int i = 0; i < topics.size(); i++)
		{
			int before = 0;
			
			File topic = new File(pathHome + "/SSociety_data/Topics/" + topics.get(i) + "/Posts");
			File[] listPosts = topic.listFiles();
			
			Date dateSixHoursAgo = new Date(System.currentTimeMillis() - (6 * 60 * 60 * 1000)); //date 6 hours ago
			
			for (int j = 0; j < listPosts.length; j++)
			{
				if(!listPosts[j].getName().equals(".DS_Store"))
				{
					Path pathToFolder = Paths.get(listPosts[j].getAbsolutePath());
					BasicFileAttributes getCreationDate = Files.readAttributes(pathToFolder, BasicFileAttributes.class);
					
					Date creationDate = new Date(getCreationDate.creationTime().toMillis());
					
					if(creationDate.compareTo(dateSixHoursAgo) >= 0) {
						before++;
					}
				}
			}
			
			frequence.add(before);
		}
		
		Integer[] a = new Integer[frequence.size()];
		frequence.toArray(a);
		
		String[] b = new String[topics.size()];
		topics.toArray(b);
		
		for(int i = 0; i < a.length - 1; i++)
		{
			for (int j = 1; j < a.length; j++)
			{	
				if(a[i] < a[j])
				{
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;
					
					String temp2 = b[i];
					b[i] = b[j];
					b[j] = temp2;
				}
			}
		}
		
		System.out.println("--------------------------");
		System.out.println(BOLD + "Most active topics" + RESET);
		System.out.println("--------------------------");
		System.out.println("In this section you can check the most active topics on SSociety in the last 6 hours. These are ordered by ascending order and next to each one you can see how many posts were added to each topic.");
		System.out.println("--------------------------");
		
		for (int i = 0; i < a.length; i++)
		{
			if (a[i] != 1) {
				System.out.println((i + 1) + " - " + b[i] + " (" + a[i] + " topics)");
			}
			else {
				System.out.println((i + 1) + " - " + b[i] + " (" + a[i] + " topic)");
			}
		}
		System.out.println();
		cons.readLine("Press Enter to return...");
		Home back = new Home(username);
		Screen.clear();
		back.homeScreen();
		return;
	}
}