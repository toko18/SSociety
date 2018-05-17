package logged;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import util.Screen;
import settings.AccountSettings;

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
			else if (chosenOption == 6) {
				Screen.clear();
				AccountSettings changeSettings = new AccountSettings(username);
				changeSettings.settingsScreen();
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
				line = line.substring(0, line.length() - 2);
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
				line = line.substring(0, line.length() - 2);
				userTopics.add(line);
				System.out.println(n + " - " + line);
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
				chosenTopic.actions(username, chosenOption - 1);
				return;
				
			}
			else if (chosenOption == n)
			{
				Screen.clear();
				this.homeScreen();
				return;
			}
		}
	}
	
	public void subscribeTopic() throws IOException
	{
		while(true)
		{
			System.out.println("--------------------------");
			System.out.println("Subscribe new Topic");
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
}