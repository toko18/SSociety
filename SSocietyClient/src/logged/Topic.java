package logged;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import util.Screen;

public class Topic {
	
	private static Console cons = System.console();
	
	private String pathHome = System.getProperty("user.home");
	private String allTopics = pathHome + "/SSociety_data/Topics";
	
	ArrayList<String> userTopics = new ArrayList<String>();
	
	Topic (ArrayList<String> a, String u)
	{
		userTopics = a;
	}
	
	public void openTopic(int topic) throws IOException
	{
		File postsFolder = new File(allTopics + "/" + userTopics.get(topic) + "/Posts");
		System.out.println("------------------------------");
		System.out.println(userTopics.get(topic));
		System.out.println("------------------------------");
		
		File[] posts = postsFolder.listFiles();
		
		int numberOfPosts = 0;
		for (File file: posts) {
	        if (file.isDirectory())
	            numberOfPosts++;
	    }
		
		System.out.print("Descrição: ");
		FileReader readDescription = null;
		try { readDescription = new FileReader(allTopics + "/" + userTopics.get(topic) + "/description.txt"); }
		catch (FileNotFoundException e) {} 
		BufferedReader displayDescription = new BufferedReader(readDescription);
		
		String line = "";
		while (line != null)
		{
			line = displayDescription.readLine();
			if(line != null)
				System.out.println(line);
		}
		
		displayDescription.close();
		System.out.println();
		
		for (int i = 1; i <= numberOfPosts; i++) 
		{
			FileReader readFile = null;
			try { readFile = new FileReader(allTopics + "/" + userTopics.get(topic) + "/Posts/" + i + "/post.txt"); } //i é a pasta que contem o comentario 
			catch (FileNotFoundException e) {} 
			BufferedReader displayComment = new BufferedReader(readFile);
			
			System.out.println(i + " - " + displayComment.readLine());
			System.out.println();
			
			line = "";
			while (line != null)
			{
				line = displayComment.readLine();
				if(line != null)
					System.out.println(line);
			}
			
			displayComment.close();
			
			try { readFile = new FileReader(allTopics + "/" + userTopics.get(topic) + "/Posts/" + i + "/likes.txt"); } //i é a pasta que contem o comentario 
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
	
	public void actions(String username, int topicNumber) throws IOException
	{
		String topic = userTopics.get(topicNumber);

		System.out.println("------------------------------");
	    System.out.println("Down here you can like a comment, post in this topic and unsubscribe this topic.");
	    System.out.println();
	    System.out.println("Write /like + space + post number to like a post.");
	    System.out.println("Write /unlike + space + post number to remove your like from a post");
	    System.out.println("Write /post + your message to write a post in this topic.");
	    System.out.println("write /favorite to make this topic a favourite of yours");
	    System.out.println("write /unfavourite to delete this topic from your favorites");
	    System.out.println("Write /unsubscribe to unsubscribe this topic.");
	    System.out.println("Write /back to leave this topic");
	    System.out.println("------------------------------");
	    
	    String action = "";
	    System.out.println();
		
		while (true)
		{
			
			action = cons.readLine("Action: ");
			
			Scanner viewAction = new Scanner(action);
			String keyword = viewAction.next();
			
			if (keyword.equals("/like"))
			{
				int chosenPost = 0;
				chosenPost = Integer.parseInt(viewAction.next());
				viewAction.close();
				
				try 
				{
					FileReader readLikes = new FileReader(allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					BufferedReader read = new BufferedReader(readLikes);
					
					boolean allow = true;
					String line = "";
					while(line != null)
					{
						line = read.readLine();
						if (line != null && line.equals(username))
						{
							allow = false;
						}
					}
					read.close();
					
					if (allow)
					{
						FileWriter write = new FileWriter(allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt", true);
						BufferedWriter addToFile = new BufferedWriter(write);
						
						addToFile.write(username);
						addToFile.newLine();
						addToFile.close();
						
						write = new FileWriter(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt", true);
						addToFile = new BufferedWriter(write);
						
						addToFile.write(topic + "|" + chosenPost);
						addToFile.newLine();
						addToFile.close();
					}
					
					Screen.clear();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				catch (Exception e) 
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
					chosenPost = 0;
				}
				
			}
			else if (keyword.equals("/unlike"))
			{
				int chosenPost = 0;
				chosenPost = Integer.parseInt(viewAction.next());
				viewAction.close();
				
				try
				{	
					File userLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt");
					File newUserLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/new likes.txt");
					newUserLikes.createNewFile();
					
					File topicLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					File newTopicLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/new likes.txt");
					newTopicLikes.createNewFile();
					
					FileReader reader = new FileReader(userLikes);
					FileWriter writer = new FileWriter(newUserLikes, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String toRemoveUser = topic + "|" + chosenPost;
					String line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.equals(toRemoveUser))
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					userLikes.delete();
					newUserLikes.renameTo(userLikes);
					
					reader = new FileReader(topicLikes);
					writer = new FileWriter (newTopicLikes, true);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					String toRemovePost = username + "|" + chosenPost;
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.equals(toRemovePost));
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					topicLikes.delete();
					newTopicLikes.renameTo(topicLikes);
					
					Screen.clear();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
					chosenPost = 0;
				}
			}
			else if (keyword.equals("/post"))
			{
				try
				{
					String post = action.substring(6, action.length());
					File postsFolder = new File(allTopics + "/" + topic + "/Posts");
					File[] posts = postsFolder.listFiles(); 
					
					int numberOfPosts = 0;
					for (File file: posts) {
				        if (file.isDirectory())
				            numberOfPosts++;
				    }
					
					numberOfPosts++;
					File newPost = new File(allTopics + "/" + topic + "/Posts/" + numberOfPosts);
					newPost.mkdir();
					
					FileWriter text = new FileWriter(allTopics + "/" + topic + "/Posts/" + numberOfPosts + "/post.txt");
					BufferedWriter addToFile = new BufferedWriter(text);
					
					addToFile.write(username);
					addToFile.newLine();
					addToFile.write(post);
					addToFile.close();
					
					File likes = new File(allTopics + "/" + topic + "/Posts/" + numberOfPosts + "/likes.txt");
					likes.createNewFile();
					
					String add = topic + "|" + numberOfPosts;
					
					File userPost = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/posts.txt");
					FileWriter postsFile = new FileWriter (userPost);
					addToFile = new BufferedWriter(postsFile);
					
					addToFile.write(add);
					addToFile.newLine();
					addToFile.close();
					
					Screen.clear();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}	
			}
			else if (keyword.equals("/favorite"))
			{
				try
				{
					String favorite = topic + "|*";
				
					File subscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					File newSubscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/newSubscriptions.txt");
					newSubscriptions.createNewFile();
					
					FileReader reader = new FileReader(subscriptions);
					FileWriter writer = new FileWriter(newSubscriptions, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && line.substring(0, line.length() - 2).equals(topic))
						{
							writeToFile.write(favorite);
							writeToFile.newLine();
						}
						else if (line != null)
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}	
					}
					
					readFromFile.close();
					writeToFile.close();
					
					subscriptions.delete();
					newSubscriptions.renameTo(subscriptions);
					
					Screen.clear();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				
			}
			else if (keyword.equals("/unfavorite"))
			{
				try
				{
					String unfavorite = topic + "|.";
					
					File subscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					File newSubscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/newSubscriptions.txt");
					newSubscriptions.createNewFile();
					
					FileReader reader = new FileReader(subscriptions);
					FileWriter writer = new FileWriter(newSubscriptions, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && line.substring(0, line.length() - 2).equals(topic))
						{
							writeToFile.write(unfavorite);
							writeToFile.newLine();
						}
						else if (line != null)
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}	
					}
					
					readFromFile.close();
					writeToFile.close();
					
					subscriptions.delete();
					newSubscriptions.renameTo(subscriptions);
					
					Screen.clear();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				catch (Exception e){
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
			}
			else if (keyword.equals("/unsubscribe"))
			{
				try
				{
					String unsubscribe = topic;
					
					File subscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					File newSubscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/newSubscriptions.txt");
					newSubscriptions.createNewFile();
					
					FileReader reader = new FileReader(subscriptions);
					FileWriter writer = new FileWriter(newSubscriptions, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.substring(0, line.length() - 2).equals(unsubscribe))
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					
					subscriptions.delete();
					newSubscriptions.renameTo(subscriptions);
					
					Screen.clear();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber);
					this.actions(username, topicNumber);
				}
			}
			else if (keyword.equals("/back"))
			{
				Home back = new Home(username);
				Screen.clear();
				back.displayTopics();
				break;
			}
			
			Screen.clear();
			System.out.println("Error! Try again...");
			System.out.println();
			this.openTopic(topicNumber);
			this.actions(username, topicNumber);
		}
	}
}