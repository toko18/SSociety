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
	
	static final String BOLD = "\033[1m"; //line to start bold 
	static final String RESET = "\033[0m"; //line to stop bold 
	//these go to the System.out.println()
	
	ArrayList<String> userTopics = new ArrayList<String>();
	//list that can grow without size limitation that keeps a list of all the topics in SSociety
	
	Topic (ArrayList<String> a, String u)
	{
		userTopics = a; //constructor passes the topics to the array list in this class
	}
	//----------------------------This method opens a topic and displays all the posts in it----------------------------
	//this class serves to both open topic and display feed
	public void openTopic(int topic, int n, int f) throws IOException
	{
		File postsFolder = new File(allTopics + "/" + userTopics.get(topic) + "/Posts");
		System.out.println("-------------------------");
		System.out.println(BOLD + userTopics.get(topic) + RESET);
		System.out.println("-------------------------");
		
		File[] posts = postsFolder.listFiles(); //array of the type file that lists all the posts in the topic
		ArrayList<Integer> postsList = new ArrayList<Integer>();
		
		for (File file: posts) {
	        if (file.isDirectory())
	        	postsList.add(Integer.parseInt(file.getName())); //adds the post name to the arraylist postsList
	    }
		
		Collections.sort(postsList); //sorts the array list 
		//this allows to have wholes in the naming of the posts. even if one post disappears he always know in which order we have to display 
		//them
		
		//--------------------------------Reading and displaying the description of the topic--------------------------------------------
		System.out.print("Descrição: ");
		FileReader readDescription = null;
		try { readDescription = new FileReader(allTopics + "/" + userTopics.get(topic) + "/description.txt"); }
		catch (FileNotFoundException e) {} //trying to find the file that contains the topics description
		BufferedReader displayDescription = new BufferedReader(readDescription);
		
		displayDescription.readLine(); //the first line in the file only interests to admins and only admins with access to the files can see it
		//this first line contains the limit number of posts that the topic can have
		String line = "";
		while (line != null) //this while displays all the lines in the description
		{
			line = displayDescription.readLine();
			if(line != null)
				System.out.println(line);
		}
		
		displayDescription.close();
		//------------------------------------------------------------------------------------------------------------------
		System.out.println();
		
		Integer[] nPost = new Integer[postsList.size()]; //new array this the size of the array list that saves the number of the posts
		postsList.toArray(nPost); //conversion from array list to array 
		//could have used array list but I prefer conventional arrays to manipulate data
		
		//-------------------------Open topic or display feed?----------------------------------------------------------
		//we need to know if we want to open the topic or display feed
		//open topic shows all the posts in the topic so we start by the first position in the array nPost
		//display feed only shows the five most recent posts in each topic
		int start = 0; 
		
		if (f == 1) //if flag == 1 this means we want to display feed so we check if the number of topics is greater that the n which is the maximum number of posts from each topic we display
		{
			if(nPost.length > n) {
				start = nPost.length - n; //if the condition is met we start displaying topics from the lenght - n
			}
		}
		//-------------------------------------------for loop that displays the posts---------------------------------------
		for (int i = start; i < nPost.length; i++) 
		{
			File post = new File(allTopics + "/" + userTopics.get(topic) + "/Posts/" + nPost[i] + "/post.txt"); 
			//nPosts[i]  numeber of the post
			
			FileReader readFile = null;
			try { readFile = new FileReader(post); } 
			catch (FileNotFoundException e) {} 
			BufferedReader displayComment = new BufferedReader(readFile);
			
			String name = (displayComment.readLine()); //name of the author of the post
			System.out.println(nPost[i] + " - " + name);  //prints the number of post + its author
			System.out.println();
			//-------dipslay post----------
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
			//----above the post we show the likes
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
	
	//this method allows the user to like, unlike, post, favirite a topic and unsubscribe a topic --- this inside the topic
	public void actions(String username, int topicNumber, int n, int f) throws Exception
	{
		String topic = userTopics.get(topicNumber);
		//string that gets the name of the topic

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
			String keyword = viewAction.next(); //gets the action we want to perform
			
			if (keyword.equals("/like")) //condition to like a post
			{
				int chosenPost = 0;
				chosenPost = Integer.parseInt(viewAction.next()); //gets the number of the post we want to give a like
				viewAction.close();
				
				try 
				{
					FileReader readLikes = new FileReader(allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					BufferedReader read = new BufferedReader(readLikes);
					
					boolean allow = true;
					String line = "";
					//-----this while chekcs if a like has not been given before to the post-----
					while(line != null)
					{
						line = read.readLine();
						if (line != null && line.equals(username))
						{
							allow = false;
						}
					}
					read.close();
					
					if (allow) //user can give a like
					{
						FileWriter write = new FileWriter(allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt", true);
						BufferedWriter addToFile = new BufferedWriter(write);
						
						addToFile.write(username); //writes the username of the user in the likes files in the post folder
						addToFile.newLine();
						addToFile.close();
						
						write = new FileWriter(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt", true);
						addToFile = new BufferedWriter(write);
						
						addToFile.write(topic + "|" + chosenPost); //writes to the likes file in the user folder
						addToFile.newLine();
						addToFile.close();
						
					}
					
					Screen.clear();
					System.out.println("You liked a post!");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e) 
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);//refreshes the screen and displays everything again but updated
					this.actions(username, topicNumber, n, f);
					chosenPost = 0;
				}
				
			}
			else if (keyword.equals("/unlike")) //condition to unlike a post
			{
				int chosenPost = 0;
				chosenPost = Integer.parseInt(viewAction.next()); //gets the number of the post we want to unlike
				viewAction.close();
				
				try
				{	
					File userLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt");
					File newUserLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/new likes.txt");
					newUserLikes.createNewFile(); //creates new file for likes in the user folder
					
					File postLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					File newPostLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/new likes.txt");
					newPostLikes.createNewFile(); //creates new file for likes in the post folder
					
					FileReader reader = new FileReader(userLikes);
					FileWriter writer = new FileWriter(newUserLikes, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String toRemoveUser = topic + "|" + chosenPost; //line we want to delete in the file
					String line = "";
					//while only writes in the new file the lines that are different from the ones we want to delete
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
					//delete old file and rename the new one in order to maintain the naming convention
					userLikes.delete();
					newUserLikes.renameTo(userLikes);
					
					reader = new FileReader(postLikes);
					writer = new FileWriter (newPostLikes, true);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					String toRemovePost = username; //line we want to delete in the file
					line = "";
					//while only writes in the new file the lines that are different from the ones we want to delete
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
					//delete old file and rename new one in order to maintain the naming convention
					postLikes.delete();
					newPostLikes.renameTo(postLikes);
					
					Screen.clear();
					System.out.println("You unliked a post...");
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f); //refreshes the screen and displays everything again but updated
					this.actions(username, topicNumber, n, f);
					chosenPost = 0;
				}
			}
			else if (keyword.equals("/post")) //condition that allows to post to the topic
			{
				try
				{
					String post = action.substring(6, action.length()); //gets the string we want to write in the post
					File postsFolder = new File(allTopics + "/" + topic + "/Posts"); //path to the posts of the topic in question
					File[] posts = postsFolder.listFiles();  //list all the topics
					
					int nPosts = 0; //variable that saves the number of posts in the topic
					int menor = 1000000000; //variable that keeps track of the minimum number that is the name of a post
					int numero = 0; //variable that gets the name of the post we are reading
					int next = numero; //next is the variable that is going to name the next post in the topic
					
					for (File file: posts) {
				        if (file.isDirectory() && file.getName() != ".DS_Store") //.DS_Store is a folder that only appears on macOS
				        {
				        	numero = Integer.parseInt(file.getName()); //gets the numeber of the post we are reading
				        	if (numero > next) //gets the name of the next post
				        	{
				        		next = numero;
				        	}
				        	if (numero < menor) //gets the number of the post we have to delete
				        	{
				        		menor = numero;
				        	}
				        	nPosts++;
				        }
				    }
					
					next++; //increments the variable next by one and is going to be the name of the next post in the topic
					
					File description = new File(allTopics + "/" + topic + "/description.txt"); //reads file description from the topic in question
					BufferedReader readLimit = new BufferedReader(new FileReader(description));
					
					int limit = Integer.parseInt(readLimit.readLine()); //reads the first line of the description file to get the limit of posts of this topic
					readLimit.close(); 
					//if limit is greater that the number of posts execute the if statement
					if (limit > 0 && nPosts >= limit) //this if statement deletes the oldest post of the topic
					{   
						//--------------------------Delete post name from likes.txt in the user Folder-----------------------------
						//getting the users that liked the post we are about to delete
						File likes = new File (allTopics + "/" + topic + "/Posts/" + menor + "/likes.txt");
						BufferedReader readNames = new BufferedReader(new FileReader(likes));
						
						ArrayList<String> names = new ArrayList<String>(); //array list that contains the name of the users that likes the post we are about to delete
						
						String line = "";
						//this while adds the names of the users that likes the post to the array list
						while(line != null)
						{
							line = readNames.readLine();
							if (line != null)
							{
								names.add(line);
							}
						}
						
						readNames.close();
						
						String deleteLine = topic + "|" + menor;
						//this for loop goes to every likes.txt file in th user folder and deletes the line that contains the post
						//because the post disappears
						for (int i = 0; i < names.size(); i++)
						{
							File userLikes = new File(pathHome + "/SSociety_data/Users/AllUsers/" + names.get(i) + "/likes.txt");
							File newUserLikes = new File(pathHome + "/SSociety_data/Users/AllUsers/" + names.get(i) + "/new likes.txt");
							BufferedReader readLikes = new BufferedReader(new FileReader(userLikes));
							BufferedWriter writeToLikes = new BufferedWriter(new FileWriter(newUserLikes));
							
							line = "";
							//while only writes in the new file the lines that are different from the ones we want to delete
							while(line != null)
							{
								line = readLikes.readLine();
								if(line != null && !line.equals(deleteLine))
								{
									writeToLikes.write(line);
									writeToLikes.newLine();
								}
							}
		
							readLikes.close();
							writeToLikes.close();
							//deletes old file and renames the new one in order to maintain the naming convention
							userLikes.delete();
							newUserLikes.renameTo(userLikes);
						}					
						//---------------------------------Delete post folder from topic folder-----------------------------------
						//delete post data from the user folder
						File postAuthor = new File(allTopics + "/" + topic + "/Posts/" + menor + "/post.txt");
						BufferedReader getAuthor = new BufferedReader(new FileReader(postAuthor));
						String name = getAuthor.readLine(); //getting the name of the author of the post in question
						
						getAuthor.close();
						//listing all the files in the folder of the post we want to delete
						File toDelete = new File(allTopics + "/" + topic + "/Posts/" + menor);
						File[] filesToDelete = toDelete.listFiles();
						
						for(File file: filesToDelete) //listing files
						{
							file.delete(); //delete file
						}
						toDelete.delete(); //delete folder
						
						//---------------------------------Delete post name form posts.txt from users folder-----------------------
						String postDelete = topic + "|" + menor; //line to delete in the posts file of the user folder
						
						File userPosts = new File(pathHome + "/SSociety_data/Users/AllUsers/" + name + "/posts.txt");
						File newUserPosts = new File(pathHome + "/SSociety_data/Users/AllUsers/" + name + "/new posts.txt"); //new file for user posts
						BufferedReader readPosts = new BufferedReader(new FileReader(userPosts));
						BufferedWriter writeToPosts = new BufferedWriter(new FileWriter(newUserPosts));
						
						line = "";
						//this while only writes to the new file those lines which are different form postDelete
						while (line != null)
						{
							line = readPosts.readLine();
							if(line != null && !line.equals(postDelete)) //only writes to the new file those lines which are different form postDelete
							{
								writeToPosts.write(line);
								writeToPosts.newLine();;
							}
						}
						
						readPosts.close();
						writeToPosts.close();
						//deletes old file and renames the new one in order to maintain naming convention
						userPosts.delete();
						newUserPosts.renameTo(userPosts); //rename new file to post.txt
						//---------------------------------------------------------------------------------------------------
					}
					//------------------Creation of new post after checking if the limit had been reached----------------------------
					File newPost = new File(allTopics + "/" + topic + "/Posts/" + next);
					newPost.mkdir(); //creation of the post directory
					
					FileWriter write = new FileWriter(allTopics + "/" + topic + "/Posts/" + next + "/post.txt");
					BufferedWriter addToFile = new BufferedWriter(write);
					
					addToFile.write(username); //first line of the post has the name of its author
					addToFile.newLine();
					addToFile.write(post);
					addToFile.close();
					
					File likes = new File(allTopics + "/" + topic + "/Posts/" + next + "/likes.txt");
					likes.createNewFile(); //creation of the likes file for this new post
					
					String add = topic + "|" + next; //line to add to the posts.txt of the auhtor folder
					
					//write the topic and the number of the post to the file posts.txt in the user directory
					File userPost = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/posts.txt");
					FileReader read = new FileReader (userPost);
					BufferedReader readFromFile = new BufferedReader(read);
					
					File newUserPost = new File(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/new posts.txt"); //creation of the new posts file
					write = new FileWriter (newUserPost, true);
					addToFile = new BufferedWriter(write);
					
					String line = "";
					//while passes all the information to a new file and adds the new line
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null)
						{
							addToFile.write(line);
							addToFile.newLine(); //write posts list to the new posts file
						}
					}
					
					addToFile.write(add); //write information about the new post
					addToFile.newLine();
					readFromFile.close();
					addToFile.close();
					
					userPost.delete(); //delete old posts file
					newUserPost.renameTo(userPost);  //rename new posts.txt to posts.txt to maintain names
					
					Screen.clear();
					System.out.println("Your post has been published!");
					System.out.println();
					this.openTopic(topicNumber, n, f);//refreshes the screen and displays everything again but updated
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);//refreshes the screen and displays everything again but updated
					this.actions(username, topicNumber, n, f);
					viewAction.close();
					return;
				}	
			}
			else if (keyword.equals("/favorite")) //condition that allows the user to favorite a topic
			{
				try
				{
					String favorite = topic + "|*"; //this is what we want to add/modify to the new subscriptions file
					//|* means that the topic is a favorite
				
					File subscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					File newSubscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/newSubscriptions.txt");
					newSubscriptions.createNewFile();
					
					//get access to the current subscriptions file and create new one
					
					FileReader reader = new FileReader(subscriptions);
					FileWriter writer = new FileWriter(newSubscriptions, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String line = "";
					
					//this while changes the line that in the subscriptions file
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && line.substring(0, line.length() - 2).equals(topic))
						{//this condition checks if the line we read except the last two characters are equals to the topic name
							writeToFile.write(favorite);
							writeToFile.newLine();
						}
						else if (line != null) //continue writing
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}	
					}
					
					readFromFile.close();
					writeToFile.close();
					//delete old file and create new one to maintain naming convention
					subscriptions.delete();
					newSubscriptions.renameTo(subscriptions);
					
					Screen.clear();
					System.out.println("Topic favorited successfully!");
					System.out.println();
					this.openTopic(topicNumber, n, f);//refreshes the screen and displays everything again but updated
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f);//refreshes the screen and displays everything again but updated in case of error
					this.actions(username, topicNumber, n, f);
					viewAction.close();
					return;
				}
				
			}
			else if (keyword.equals("/unfavorite")) //this keyword does the inverse of the previous one
			{
				try
				{
					String unfavorite = topic + "|."; //line we want to modify in the subscriptions file
					
					File subscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					File newSubscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/newSubscriptions.txt");
					newSubscriptions.createNewFile();
					//cretes new subscription file that is going to have the modified line
					
					FileReader reader = new FileReader(subscriptions);
					FileWriter writer = new FileWriter(newSubscriptions, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String line = "";
					//this while changes the line that in the subscriptions file
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && line.substring(0, line.length() - 2).equals(topic))
						{//this condition checks if the line we read except the last two characters are equals to the topic name
							writeToFile.write(unfavorite);
							writeToFile.newLine();
						}
						else if (line != null)//continue writing
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}	
					}
					
					readFromFile.close();
					writeToFile.close();
					//deletes old file and renames new one to maintain naming convention
					subscriptions.delete();
					newSubscriptions.renameTo(subscriptions);
					
					Screen.clear();
					System.out.println("Topic unfavorited successfully...");
					System.out.println();
					this.openTopic(topicNumber, n, f);;//refreshes the screen and displays everything again but updated
					this.actions(username, topicNumber, n, f);
					return;
				}
				catch (Exception e){
					Screen.clear();
					System.out.println("Error! Try again...");//refreshes the screen and displays everything again but updated in case of error
					System.out.println();
					this.openTopic(topicNumber, n, f);
					this.actions(username, topicNumber, n, f);
				}
			}
			else if (keyword.equals("/unsubscribe")) //this keyword allows the user to unsubscribe topic
			{
				try
				{
					String unsubscribe = topic; //gets the name of the topic we want to unsubscribe
					//-----------------------------------modify subscriptions file in user folder-------------------------------
					File subscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/subscriptions.txt");
					File newSubscriptions = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/newSubscriptions.txt");
					newSubscriptions.createNewFile(); //creates new subscription file
					
					FileReader reader = new FileReader(subscriptions);
					FileWriter writer = new FileWriter(newSubscriptions, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String line = "";
					//this while changes the line that in the subscriptions file
					while (line != null)
					{//this condition checks if the line we read except the last two characters are equals to the topic name
						line = readFromFile.readLine();
						if (line != null && !line.substring(0, line.length() - 2).equals(unsubscribe))
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					
					//--------------------------------------------modify subscription file in topic-------------------------------------
					
					File usersSubscribed = new File (pathHome + "/SSociety_data/Topics/" + userTopics.get(topicNumber) + "/subscriptions.txt");
					File newUsersSubscribed = new File (pathHome + "/SSociety_data/Topics/" + userTopics.get(topicNumber) + "/newSubscriptions.txt");
					//create new subscription file in topic folder
					reader = new FileReader(usersSubscribed);
					writer = new FileWriter(newUsersSubscribed);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					line = "";
					//this while changes the line that in the subscriptions file
					while (line != null)
					{
						line = readFromFile.readLine();
						if (line != null && !line.equals(username))
						{//this condition checks if the line we read equals the username
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					
					//delete file and renaming new one to maintain naming convention
					usersSubscribed.delete();
					newUsersSubscribed.renameTo(usersSubscribed);
					
					subscriptions.delete(); //delete file and renaming new one to maintain naming convention
					newSubscriptions.renameTo(subscriptions);
					
					Home back = new Home(username);
					Screen.clear(); //if the user is inside topic and unsubscribes the topic the program goes back to the previous menu
					System.out.println("Topic unsubscribed successfully...");
					System.out.println();
					back.displayTopics();
					viewAction.close();
					return;
				}
				catch (Exception e)
				{
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					this.openTopic(topicNumber, n, f); ;//refreshes the screen and displays everything again but updated in case of error
					this.actions(username, topicNumber, n, f);
					viewAction.close();
					return;
				}
			}
			else if (keyword.equals("/back")) //goes back to the previous menu with a list of all the subscribed topics
			{
				Home back = new Home(username);
				Screen.clear();
				back.displayTopics();
				viewAction.close();
				return;
			}
			
			Screen.clear();
			System.out.println("Error! Try again..."); //refreshes the screen and displays everything again but updated in case of error
			System.out.println();
			this.openTopic(topicNumber, n, f);
			this.actions(username, topicNumber, n, f);
			return;
		}
	}

	
	//this method allows the user to like and unlike a post inside the feed
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
			String action2 = cons.readLine("Action: "); //gets the command form the user
			
			String[] partes = action2.split(" "); //splits the command into strings and saves them in array of strings
			keyword = partes[0]; //we know that the keyword is going to be in the first position in the array
			
			if (keyword.equals("/like")) //keyword that allows to like a post
			{	
				try 
				{
					chosenPost = Integer.parseInt(partes[partes.length - 1]);
					//gets the post that the user chose to give a like
					
					//--------------------gets the name of the topics right-----------------
					topic = partes[1];
					
					for(int i = 2; i < partes.length - 1; i++)
					{
						topic =  topic + " " + partes[i]; //puts together the name if the topic
					}
					//--------------------------------------------------------------------------
					
					FileReader readLikes = new FileReader(allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					BufferedReader read = new BufferedReader(readLikes);
					
					//---------------------------------checks if the user can give like to post------------------
					boolean allow = true;
					String line = "";
					while(line != null)
					{
						line = read.readLine();
						if (line != null && line.equals(username))
						{//checks if the name of the user is inside the likes file in the user folder
							allow = false;
						}
					}
					read.close();
					//------------------------------------------------------------------------------------------------
					
					if (allow) //if the user is allowed to give a like in 
					{
						FileWriter write = new FileWriter(allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt", true);
						BufferedWriter addToFile = new BufferedWriter(write);
						
						addToFile.write(username); //adds username to the likes file in the posts folder
						addToFile.newLine();
						addToFile.close();
						
						write = new FileWriter(pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt", true);
						addToFile = new BufferedWriter(write);
						
						addToFile.write(topic + "|" + chosenPost); //adds topic + chosen post to the likes file in the user folder
						addToFile.newLine();
						addToFile.close();
						
					}
					//refresh and updates the screen
					Screen.clear();
					System.out.println("You liked a post!");
					System.out.println();
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					for (int i = 0; i < userTopics.size(); i++)
					{ //opens all the topics that have to appear i the feed
						 this.openTopic(i, 4, 1);
					}
					
					this.actionsFeed(username, n, f); //calls actions menu to the feed
					return;
				}
				catch (Exception e) 
				{
					//refreshes the screen and displays everything again but updated in case of error
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					//opens all the topics that have to appear i the feed
					//calls actions menu to the feed
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
					//gets the post that the user chose to give unlike
					
					//--------------------gets the name of the topics right-----------------
					topic = partes[1];
					
					for(int i = 2; i < partes.length - 1; i++)
					{
						topic =  topic + " " + partes[i];
					}
					//------------------------------------------------------------------------
					
					File userLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/likes.txt");
					File newUserLikes = new File (pathHome + "/SSociety_data/Users/AllUsers/" + username + "/new likes.txt");
					newUserLikes.createNewFile();
					//creates new likes file in the user folder
					
					File postLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/likes.txt");
					File newPostLikes = new File (allTopics + "/" + topic + "/Posts/" + chosenPost + "/new likes.txt");
					newPostLikes.createNewFile();
					//creates new likes file in the post folder
					
					FileReader reader = new FileReader(userLikes);
					FileWriter writer = new FileWriter(newUserLikes, true);
					
					BufferedReader readFromFile = new BufferedReader(reader);
					BufferedWriter writeToFile = new BufferedWriter(writer);
					
					String toRemoveUser = topic + "|" + chosenPost; //removes this string from the likes file in the user folder
					String line = "";
					while (line != null)
					{//checks if the name of the user is inside the likes file in the user folder
						line = readFromFile.readLine();
						if (line != null && !line.equals(toRemoveUser)) //if it is does not write it to the user likes file
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					//deletes old likes file in user folder and renames the new one to keep naming convention
					userLikes.delete();
					newUserLikes.renameTo(userLikes);
					
					reader = new FileReader(postLikes);
					writer = new FileWriter (newPostLikes, true);
					
					readFromFile = new BufferedReader(reader);
					writeToFile = new BufferedWriter(writer);
					
					String toRemovePost = username; //string we eant to remove from the likes file in the post folder
					line = "";
					while (line != null)
					{//checks if the name of the user is inside the likes file in the post folder
						line = readFromFile.readLine();
						if (line != null && !line.equals(toRemovePost))//if it is does not write it to the post likes file
						{
							writeToFile.write(line);
							writeToFile.newLine();
						}
					}
					
					readFromFile.close();
					writeToFile.close();
					
					//deletes old likes file in the post folder and renames the new one to keep naming convention
					postLikes.delete();
					newPostLikes.renameTo(postLikes);
					
					//refresh and updates the screen
					Screen.clear();
					System.out.println("You unliked a post...");
					System.out.println();
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					//opens all the topics that have to appear i the feed
					for (int i = 0; i < userTopics.size(); i++)
					 {
						 this.openTopic(i, 4, 1);
					 }
					
					this.actionsFeed(username, n, f);//calls actions menu to the feed
					return;
				}
				catch (Exception e)
				{
					//refreshes the screen and displays everything again but updated in case of error
					Screen.clear();
					System.out.println("Error! Try again...");
					System.out.println();
					
					System.out.println("-------------------------");
					System.out.println(BOLD + "Feed" + RESET);
					System.out.println("-------------------------");
					System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
					
					//opens all the topics that have to appear i the feed
					//calls actions menu to the feed
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
				//exits the feed and goes to the home screen
				Home back = new Home(username);
				Screen.clear();
				back.homeScreen();
				return;
			}
			//refresh and updates the screen
			Screen.clear();
			System.out.println("Error! Try again...");
			System.out.println();
			System.out.println("-------------------------");
			System.out.println(BOLD + "Feed" + RESET);
			System.out.println("-------------------------");
			System.out.println("This is your feed. Here you can check the five most recent posts from every topic you sunscribe and like some of the posts");
			
			//opens all the topics that have to appear i the feed
			//calls actions menu to the feed
			for (int i = 0; i < userTopics.size(); i++)
			 {
				 this.openTopic(i, 4, 1);
			 }
			
			this.actionsFeed(username, n, f);
			return;
		}	
	}
}