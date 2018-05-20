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
import java.util.Collections;

public class Topic {
	
	private static Console cons = System.console();
	
	private String pathHome = System.getProperty("user.home");
	private String allTopics = pathHome + "/SSociety_data/Topics";
	
	static final String BOLD = "\033[1m";
	static final String RESET = "\033[0m";
	
	ArrayList<String> userTopics = new ArrayList<String>();
	
	Topic (ArrayList<String> a, String u)
	{
		userTopics = a;
	}
	
	public void openTopic(int topic, int n, int f) throws IOException
	{
		File postsFolder = new File(allTopics + "/" + userTopics.get(topic) + "/Posts");
		System.out.println("-------------------------");
		System.out.println(BOLD + userTopics.get(topic) + RESET);
		System.out.println("-------------------------");
		
		File[] posts = postsFolder.listFiles();
		ArrayList<Integer> postsList = new ArrayList<Integer>();
		
		for (File file: posts) {
	        if (file.isDirectory())
	        	postsList.add(Integer.parseInt(file.getName()));
	    }
		
		Collections.sort(postsList);
		
		System.out.print("Descrição: ");
		FileReader readDescription = null;
		try { readDescription = new FileReader(allTopics + "/" + userTopics.get(topic) + "/description.txt"); }
		catch (FileNotFoundException e) {} 
		BufferedReader displayDescription = new BufferedReader(readDescription);
		
		displayDescription.readLine();
		String line = "";
		while (line != null)
		{
			line = displayDescription.readLine();
			if(line != null)
				System.out.println(line);
		}
		
		displayDescription.close();
		System.out.println();
		
		
		Integer[] nPost = new Integer[postsList.size()];
		postsList.toArray(nPost);
		
		int start = 0;
		
		if (f == 1)
		{
			if(nPost.length > n) {
				start = nPost.length - n;
			}
		}
		
		for (int i = start; i < nPost.length; i++) 
		{
			File post = new File(allTopics + "/" + userTopics.get(topic) + "/Posts/" + nPost[i] + "/post.txt");
			
			FileReader readFile = null;
			try { readFile = new FileReader(post); } 
			catch (FileNotFoundException e) {} 
			BufferedReader displayComment = new BufferedReader(readFile);
			
			String name = (displayComment.readLine());
			System.out.println(nPost[i] + " - " + name);
			System.out.println();
			
			line = "";
			while (line != null)
			{
				line = displayComment.readLine();
				if(line != null)
					System.out.println(line);
			}
			
			displayComment.close();
			
			try { readFile = new FileReader(allTopics + "/" + userTopics.get(topic) + "/Posts/" + nPost[i] + "/likes.txt"); }
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
	
	@SuppressWarnings("unused")
	public void actions(String username, int topicNumber, int n, int f) throws Exception
	{
		String topic = userTopics.get(topicNumber);

		System.out.println("-------------------------");
	    System.out.println("Down here you can like a comment, post in this topic and unsubscribe this topic.");
	    System.out.println();
	    System.out.println("Write /like + space + post number to like a post.");
	    System.out.println("Write /unlike + space + post number to remove your like from a post");
	    System.out.println("Write /post + your message to write a post in this topic.");
	    System.out.println("write /favorite to make this topic a favourite of yours");
	    System.out.println("write /unfavorite to delete this topic from your favorites");
	    System.out.println("Write /unsubscribe to unsubscribe this topic.");
	    System.out.println("Write /back to leave this topic");
	    System.out.println("-------------------------");
	    
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
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
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
					
					File postLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					File newPostLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/new likes.txt");
					newPostLikes.createNewFile();
					
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
					userLikes.delete();
					newUserLikes.renameTo(userLikes);
					
					reader = new FileReader(postLikes);
					writer = new FileWriter (newPostLikes, true);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					String toRemovePost = username;
					line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.equals(toRemovePost))
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					postLikes.delete();
					newPostLikes.renameTo(postLikes);
					
					Screen.clear();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					chosenPost = 0;
				}
			}
			else if (keyword.equals("/post"))
			{
				try
				{
					String post = action.substring(6, action.length());
					String nextPost = "";
					File postsFolder = new File(allTopics + "/" + topic + "/Posts");
					File[] posts = postsFolder.listFiles(); 
					
					int nPosts = 0;
					int menor = 1000000000;
					int numero = 0;
					int next = numero;
					
					for (File file: posts) {
				        if (file.isDirectory() && file.getName() != ".DS_Store")
				        {
				        	numero = Integer.parseInt(file.getName());
				        	if (numero > next)
				        	{
				        		next = numero;
				        	}
				        	if (numero < menor)
				        	{
				        		menor = numero;
				        	}
				        	nPosts++;
				        }
				    }
					
					next++;
					
					File description = new File(allTopics + "/" + topic + "/description.txt");
					BufferedReader readLimit = new BufferedReader(new FileReader(description));
					
					int limit = Integer.parseInt(readLimit.readLine());
					readLimit.close();
					
					if (limit > 0 && nPosts >= limit)
					{
						File toDelete = new File(allTopics + "/" + topic + "/Posts/" + menor);
						File[] filesToDelete = toDelete.listFiles();
						
						for(File file: filesToDelete)
						{
							file.delete();
						}
						toDelete.delete();
					}
					
					File newPost = new File(allTopics + "/" + topic + "/Posts/" + next);
					newPost.mkdir();
					
					FileWriter write = new FileWriter(allTopics + "/" + topic + "/Posts/" + next + "/post.txt");
					BufferedWriter addToFile = new BufferedWriter(write);
					
					addToFile.write(username);
					addToFile.newLine();
					addToFile.write(post);
					addToFile.close();
					
					File likes = new File(allTopics + "/" + topic + "/Posts/" + next + "/likes.txt");
					likes.createNewFile();
					
					String add = topic + "|" + next;
					
					File userPost = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/posts.txt");
					FileReader read = new FileReader (userPost);
					BufferedReader readFromFile = new BufferedReader(read);
					
					File newUserPost = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/new posts.txt");
					write = new FileWriter (newUserPost, true);
					addToFile = new BufferedWriter(write);
					
					String line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null)
						{
							addToFile.write(line);
							addToFile.newLine();
						}
					}
					
					addToFile.write(add);
					addToFile.newLine();
					readFromFile.close();
					addToFile.close();
					
					userPost.delete();
					newUserPost.renameTo(userPost);
					
					Screen.clear();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					viewAction.close();
					return;
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
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					viewAction.close();
					return;
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
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e){
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
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
					
					File usersSubscribed = new File (pathHome + "/SSociety_data/Topics/" + userTopics.get(topicNumber) + "/subscriptions.txt");
					File newUsersSubscribed = new File (pathHome + "/SSociety_data/Topics/" + userTopics.get(topicNumber) + "/newSubscriptions.txt");
					
					reader = new FileReader(usersSubscribed);
					writer = new FileWriter(newUsersSubscribed);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.equals(username))
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					
					usersSubscribed.delete();
					newUsersSubscribed.renameTo(usersSubscribed);
					
					subscriptions.delete();
					newSubscriptions.renameTo(subscriptions);
					
					Home back = new Home(username);
					Screen.clear();
					back.displayTopics();
					viewAction.close();
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					viewAction.close();
					return;
				}
			}
			else if (keyword.equals("/back"))
			{
				Home back = new Home(username);
				Screen.clear();
				back.displayTopics();
				viewAction.close();
				return;
			}
			
			Screen.clear();
			System.out.println("Error! Try again...");
			System.out.println();
			this.openTopic(topicNumber, n, f);
			this.actions(username, topicNumber, n, f);
			return;
		}
	}

	
	@SuppressWarnings("resource")
	public void actionsFeed (String username, int n, int f)  throws Exception
	{

		System.out.println("-------------------------");
	    System.out.println("Down here you can like and unlike a post and go back.");
	    System.out.println();
	    System.out.println("Write /like + space + topic name + space + post number to like a post.");
	    System.out.println("Write /unlike + space + topic name + post number to remove your like from a post.");
	    System.out.println("Write /back to leave this topic.");
	    System.out.println("-------------------------");
	    
	    System.out.println();
		
		while (true)
		{	
			String keyword = "";
			String topic = "";
			int chosenPost = 0;
			String action2 = cons.readLine("Action: ");
			
			String[] partes = action2.split(" ");
			keyword = partes[0];
			
			if (keyword.equals("/like"))
			{	
				try 
				{
					chosenPost = Integer.parseInt(partes[partes.length - 1]);
					topic = partes[1];
					
					for(int i = 2; i < partes.length - 1; i++)
					{
						topic =  topic + " " + partes[i];
					}
					
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
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					for (int i = 0; i < userTopics.size(); i++)
					 {
						 this.openTopic(i, 4, 1);
					 }
					
					this.actionsFeed(username, n, f);
					return;
				}
				catch (Exception e) 
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					e.printStackTrace();
					System.out.println();
					
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					for (int i = 0; i < userTopics.size(); i++)
					 {
						 this.openTopic(i, 4, 1);
					 }
					
					this.actionsFeed(username, n, f);
					return;
				}
				
			}
			else if (keyword.equals("/unlike"))
			{
				try
				{	
					chosenPost = Integer.parseInt(partes[partes.length - 1]);
					topic = partes[1];
					
					for(int i = 2; i < partes.length - 1; i++)
					{
						topic =  topic + " " + partes[i];
					}
					
					File userLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt");
					File newUserLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/new likes.txt");
					newUserLikes.createNewFile();
					
					File postLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					File newPostLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/new likes.txt");
					newPostLikes.createNewFile();
					
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
					
					userLikes.delete();
					newUserLikes.renameTo(userLikes);
					
					reader = new FileReader(postLikes);
					writer = new FileWriter (newPostLikes, true);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					String toRemovePost = username;
					line = "";
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.equals(toRemovePost))
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					postLikes.delete();
					newPostLikes.renameTo(postLikes);
					
					Screen.clear();
					
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					for (int i = 0; i < userTopics.size(); i++)
					 {
						 this.openTopic(i, 4, 1);
					 }
					
					this.actionsFeed(username, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					e.printStackTrace();
					System.out.println();
					
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					for (int i = 0; i < userTopics.size(); i++)
					 {
						 this.openTopic(i, 4, 1);
					 }
					
					this.actionsFeed(username, n, f);
					return;
				}
			}
			else if (keyword.equals("/back"))
			{
				Home back = new Home(username);
				Screen.clear();
				back.homeScreen();
				return;
			}
			
			Screen.clear();
			System.out.println("Error! Try again...");
			System.out.println();
			System.out.println("-------------------------");
			System.out.println(BOLD + "Feed" + RESET);
			System.out.println("-------------------------");
			System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
			
			for (int i = 0; i < userTopics.size(); i++)
			 {
				 this.openTopic(i, 4, 1);
			 }
			
			this.actionsFeed(username, n, f);
			return;
		}	
	}
}