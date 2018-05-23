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
	
	private String username; //variable that saves the username entered by the user
	private String pathHome = System.getProperty("user.home"); //variable that saves the path to user home folder
	private String userFolder; //path to user folder
	
	static final String BOLD = "\033[1m"; //starts bold string
	static final String RESET = "\033[0m"; //stops bold string
	
	public Home(String u) throws IOException
	{//constructor that initializes the variables above
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/AllUsers/" + username;
	}
	
	//---------------------------------------user home screen after login---------------------------------
	public void homeScreen() throws Exception
	{
		int chosenOption = 0; //option chosen by the user
		
		while(true)
		{//waht the user sees
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
			catch(Exception e) { chosenOption = 0;} //ensures that the user chooses an integer
			
			if (chosenOption == 1)
			{//calls the method to display feed
				Screen.clear();
				this.displayFeed();
				return;
			}
			else if (chosenOption == 2)
			{//calls the method to display a specific topic
				Screen.clear();
				this.displayTopics();
				return;
			}
			else if (chosenOption == 3)
			{//calls the method that allows to subscribe topic
				Screen.clear();
				this.subscribeTopic();
				return;
			}
			else if (chosenOption == 4)
			{//calls the method that shows the most active topics
				Screen.clear();
				this.showMostActiveTopics();
				return;
			}
			else if (chosenOption == 5)
			{//calls the method that opens user statistics
				Screen.clear();
				UserStats stats = new UserStats(username);
				stats.displayStats();
				return;
			}
			else if (chosenOption == 6) 
			{//calls the method that opens account settings
				Screen.clear();
				AccountSettings changeSettings = new AccountSettings(username);
				changeSettings.settingsScreen();
			}
			else if (chosenOption == 7)
			{//calls the method to log out
				Screen.clear();
				System.out.println("It's sad to see you go!");
				cons.readLine("Press Enter to continue...");
				Screen.clear();
				SSocietyClient.firstScreen();
				return;
			}
			else 
			{//when something fails screen is clean and menu appears
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println();
			}
		}
	}
	
	public void displayFeed() throws Exception
	{//method to display feed
	     ArrayList<String> userTopics = new ArrayList<String>();
	     //array list that saves the topics that the user subscribes
		
		 FileReader subscriptions = null; //tries to read subscriptions file to get the topics that the user subscribes
		 try { subscriptions = new FileReader(userFolder + "/subscriptions.txt"); } 
		 catch (FileNotFoundException e) {} 
		
		 BufferedReader getUserTopics = new BufferedReader(subscriptions);
		 
		 String line = "";
		 //reads all the lines in subscriptions file
		 while (line != null)
		 {
			 line = getUserTopics.readLine();
			 if (line != null)
			 {
				line = line.substring(0, line.length() - 2); //deletes last two characters that indicate if the topic is a favorite
			   	userTopics.add(line); //adds the topic to the array list
			 }
		 }
	 
		 getUserTopics.close();
		 
		 Topic userFeed = new Topic(userTopics, username); 
		 //creates object of type Topic
		 while(true)
		 { //what the user sees when feed is opem
			 System.out.println("-------------------------");
			 System.out.println(BOLD + "Feed" + RESET);
			 System.out.println("-------------------------");
			 System.out.println("This is your feed. Here you can check the five most recent posts from every topic you subscribe and like some of the posts");
			 for (int i = 0; i < userTopics.size(); i++)
			 {
				 userFeed.openTopic(i, 5, 1); // i = topic , 5 number of posts displayed per topic, 1 = flag //if flag = 1 we know that only 5 posts per topic can be displayed
			 }
			 userFeed.actionsFeed(username, 5, 1); //actions that the user can perform in the feed - like/unlike and back§
			 return;
		 }
	}
	
	public void displayTopics() throws Exception
	{//method that displays a topic
		while(true)
		{
			int n = 0;//number of topics the user subscribe
			int chosenOption = 0; //topci chosen by the user
			ArrayList<String> userTopics = new ArrayList<String>(); //array list that contains the topics subscribed by the user
			
			//waht the user sees when has to choose a topic
			System.out.println("-------------------------");
			System.out.println(BOLD + "My Topics" + RESET);
			System.out.println("-------------------------");
			System.out.println("In this section you can see a list of all the topics you subscribe an enter in one of your choice in order to performe actions: like/unlike a post, post a message, unsubscribe a topic and favorite/unfavorite a topic. Topics that appear in Bold are the ones you have favorited.");
			System.out.println("-------------------------");
			
			FileReader subscriptions = null;
			try { subscriptions = new FileReader(userFolder + "/subscriptions.txt"); } 
			catch (FileNotFoundException e) {}  //tries to read subscriptions file in user folder
			
			BufferedReader getUserTopics = new BufferedReader(subscriptions);
			
			String line = "";
			
			while (line != null)
			{
				line = getUserTopics.readLine();
				if (line != null)
				{
					n++; //increases the number of topics the user subscribe by one
					String lastTwo = line.substring(line.length() - 2, line.length());
					line = line.substring(0, line.length() - 2);
					userTopics.add(line);//add topic to array list
					//check if the topic is favorite
					if(lastTwo.equals("|."))
					{//dont print in bold
						System.out.println(n + " - " + line);
					}
					else if(lastTwo.equals("|*"))
					{//print in bold
						System.out.println(BOLD + n + " - " + line + RESET);
					}
				}
			}
			
			getUserTopics.close();
			
			if (n == 0)
			{//what the user sees when the number of topics is zero
				System.out.println("You are not subscribed to any topic!");
				cons.readLine("Press Enter to continue...");
				Screen.clear();
				this.homeScreen(); //returns to homescreen after pressing enter
				return;
			}
			
			System.out.println();
			n++;
			System.out.println("-------------------------");
			System.out.println(n + " - Back");
			
			while (true)
			{//chooses which topic wants to open
				try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
				catch(Exception e) { chosenOption = 0;} //ensures that the users types an integer
				
				if (chosenOption >= 1 && chosenOption < n)
				{//ensures that the topic chosen is between 1 and the number of topics
					Screen.clear();
					Topic chosenTopic = new Topic(userTopics, username); //creates object of type Topic
					chosenTopic.openTopic(chosenOption - 1, 1, 0); //open topic and dipslay starts in one and flag = 0
					chosenTopic.actions(username, chosenOption - 1, 1, 0); 
					return;
					
				}
				else if (chosenOption == n)
				{//return to homescreen
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
		{//what the user sees when enters subscribe topic
			System.out.println("--------------------------");
			System.out.println(BOLD + "Subscribe new Topic" + RESET);
			System.out.println("--------------------------");
			System.out.println("In this section you can see all the active topic you can subscribe to on SSociety.");
			
			int chosenTopic = 0;
			
			ArrayList<String> ssocietyTopics = new ArrayList<String>();
			//array list that contains all the topics subscribed
			
			File allTopics = new File(pathHome + "/SSociety_data/Topics/topics.txt");
			FileReader readTopics = new FileReader(allTopics); 
			BufferedReader getTopics = new BufferedReader(readTopics);
			//opens topics.txt a file that contains all the topics in SSociety_data
			
			int n = 0;//topic order
			int i = 0; //topic index in topics array list
			String line = "";
			
			while (line != null)
			{//displays the topics description
				line = getTopics.readLine();
				if (line != null)
				{
					n++; //increases n that is the number of the topic. this starts in one.
					ssocietyTopics.add(line);
					System.out.println("--------------------------");
					System.out.println(BOLD + n + " - " + line + RESET);
					
					System.out.print("Descrição: ");
					FileReader readDescription = null;
					try 
					{ 
						File description = new File(pathHome + "/SSociety_data/Topics/" + ssocietyTopics.get(i) + "/description.txt");
						readDescription = new FileReader(description); //tries to open topic description
					}
					catch (FileNotFoundException e) {} 
					
					BufferedReader displayDescription = new BufferedReader(readDescription);
					
					displayDescription.readLine();
					String line2 = "";
					while (line2 != null)
					{//displays description
						line2 = displayDescription.readLine();
						if(line2 != null)
							System.out.println(line2);
					}
					
					displayDescription.close();
					i++;//increases the index of the topic
				}
			}
			
			getTopics.close();
			
			n++;
			System.out.println("--------------------------");
			System.out.println(n + " - Back"); //back 
			
			while(true)
			{//while back is not entered
				try {chosenTopic = Integer.parseInt(cons.readLine("Subscribe Topic: ")); }
				catch(Exception e) { //ensures that the an integer is inserted by the user
					Screen.clear();
					System.out.println("That is not a valid topic. Please try again...");
					chosenTopic = 0;
				}
				
				if (chosenTopic >= 1 && chosenTopic < n)
				{//when a valid number of topic is inserted
					File addUserSubscription = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					FileWriter write = new FileWriter(addUserSubscription, true);
					BufferedWriter writeToSubscriptions = new BufferedWriter(write);
					//gets subscriptions file from user folder
					FileReader read = new FileReader(addUserSubscription);
					BufferedReader readSubscriptions = new BufferedReader(read);
					
					String toAdd = ssocietyTopics.get(chosenTopic - 1) + "|.";
					//line to add in subscriptions file
					line = "";
					boolean add = true;
					while (line != null)
					{//checks if subscriptions it is not already done
						line = readSubscriptions.readLine();
						if (line != null && line.equals(toAdd))
						{
							add = false;
						}
					}
					
					if (add)
					{//if user can subscribe topic
						writeToSubscriptions.write(toAdd); //write topic in subscriptions 
						writeToSubscriptions.newLine();
						
						readSubscriptions.close();
						writeToSubscriptions.close();
						
						File topicSubscriptions = new File(pathHome + "/SSociety_data/Topics/" + ssocietyTopics.get(chosenTopic - 1) + "/subscriptions.txt");
						write = new FileWriter(topicSubscriptions, true);//read topics file in topic folder
						writeToSubscriptions = new BufferedWriter(write);
						
						writeToSubscriptions.write(username); //write username of user in file subscriptions
						writeToSubscriptions.newLine();
					}
					readSubscriptions.close();
					writeToSubscriptions.close();
					Screen.clear();
					System.out.println("Topic subscribed successfully...");
					System.out.println();
					break;
					
				}
				else if (chosenTopic == n)
				{//go back
					Screen.clear();
					this.homeScreen();
					return;
				}
				else {
					//input not accepted
					Screen.clear();
					System.out.println("That is not a valid input! Please try again...");
					break;
				}
			}
		}
	}
	
	public void showMostActiveTopics() throws Exception
	{//this method shows the most active topics
		ArrayList <String> topics = new ArrayList<String>(); //array list that saves the topics
		ArrayList <Integer> frequence = new ArrayList<Integer>(); //array list of integers that contains the number of posts per topic
		//the index of the topic and the number of posts is the same in the two array lists
		
		FileReader read = new FileReader(pathHome + "/SSociety_data/Topics/topics.txt");
		BufferedReader getTopic = new BufferedReader(read); //reads topics.txt file in the topics folder
		
		String line = "";
		while(line != null)
		{//adds topics to array list
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
			File[] listPosts = topic.listFiles();//lists all the posts in the topic
			
			Date dateSixHoursAgo = new Date(System.currentTimeMillis() - (6 * 60 * 60 * 1000)); //date 6 hours ago
			//time in millis six hours ago
			for (int j = 0; j < listPosts.length; j++)
			{
				if(!listPosts[j].getName().equals(".DS_Store"))
				{
					Path pathToFolder = Paths.get(listPosts[j].getAbsolutePath());
					BasicFileAttributes getCreationDate = Files.readAttributes(pathToFolder, BasicFileAttributes.class);
					//get creation date of each post
					Date creationDate = new Date(getCreationDate.creationTime().toMillis());
					
					if(creationDate.compareTo(dateSixHoursAgo) >= 0) {
						before++; //number of posts created in the last 6 hours for topic i
					}
				}
			}
			
			frequence.add(before); //add the number of topics created in the last six hours to the respective position in the array
		}
		
		Integer[] a = new Integer[frequence.size()];
		frequence.toArray(a);
		//conversion of array list to array - easier to manipulate positions
		
		String[] b = new String[topics.size()];
		topics.toArray(b);
		//conversion of array list to array - easier to manipulate positions
		
		//------------------------------sort by descending order the number of posts in each topic-------------
		for(int i = 0; i < a.length - 1; i++)
		{
			for (int j = 1; j < a.length; j++)
			{	
				if(a[i] < a[j])
				{
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;
					//position of the name of the topic follows the number of topics that correspond to itself
					String temp2 = b[i];
					b[i] = b[j];
					b[j] = temp2;
				}
			}
		}
		//---------------------------------------------------------------------------------------------------------
		
		//what the user sees when enters the most active topics screen
		System.out.println("--------------------------");
		System.out.println(BOLD + "Most active topics" + RESET);
		System.out.println("--------------------------");
		System.out.println("In this section you can check the most active topics on SSociety in the last 6 hours. These are ordered by ascending order and next to each one you can see how many posts were added to each topic.");
		System.out.println("--------------------------");
		//for - prints the result
		for (int i = 0; i < a.length; i++)
		{	//
			if (a[i] != 1) {
				System.out.println((i + 1) + " - " + b[i] + " (" + a[i] + " posts)");
			}
			else {//when number of topics in the last six hours is zero
				System.out.println((i + 1) + " - " + b[i] + " (" + a[i] + " posts)");
			}
		}
		
		//when the user presses enter goes back to homescreen
		System.out.println();
		cons.readLine("Press Enter to return...");
		Home back = new Home(username);
		Screen.clear();
		back.homeScreen();
		return;
	}
}