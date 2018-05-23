package logged;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import util.FileSystem;
import util.Help;
import util.Screen;

//---------------------------------------------*TopicsManagement*----------------------------------------------
// This is the class that has the screens of the Topics Management's menu.

public class TopicsManagement
{
	// A Console object ('cons') will be used to read the user input.
	private static Console cons = System.console();
	
	// Finds the path of user's computer home directory
	private static String pathHome = System.getProperty("user.home");
	
	// This FileFilter will be used to filter directories.
	private static FileFilter filterDir = new FileFilter()
	{
		public boolean accept(File file)
		{
			return file.isDirectory();
		}
	};
	
	//--------------------------------------------Creation Screen-----------------------------------------------
	// Creation Screen - this is the screen that displays when the admin wants to create a new topic.
	// Here, the admin needs to choose the topic title, the description and the maximum number posts that the topic can have.
	
	public static void creationScreen() throws Exception
	{	
		Screen.clear();
		
		System.out.println("Topic creation");
		System.out.println();
		
		// Reads the topic name and checks if it is valid and available.
		
		String topicName;
		topicName = cons.readLine("Choose a name/title: ");
		
		if(topicName.isEmpty())
		{
			Screen.clear();
			System.out.println("Null topic title!");
			System.out.println("Try to create it using a sequence of letters and digits.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning here, it will go back to the Topics Management Screen.
			return;
		}
		
		for(int i = 0; i < topicName.length(); i++)
		{
			if(!Character.isLetter(topicName.charAt(i)) && !Character.isDigit(topicName.charAt(i)) && topicName.charAt(i) != ' ')
			{
				Screen.clear();
				System.out.println("Topic titles can only contain letters, space character and digits.");
				System.out.println("Try to create it using another title.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				// By returning here, it will go back to the Topics Management Screen.
				return;
			}
		}
		
		File allTopics = new File(pathHome + "/SSociety_data/Topics/topics.txt");
		BufferedReader reader = new BufferedReader(new FileReader(allTopics));
		
		// This while loop will give an error message and it will go back to the Topics Management Screen if the
		//   title already exists in the 'topics.txt' file (or it leaves the loop, meaning that the title is available).
		String line;
		while((line = reader.readLine()) != null)
		{
			if(line.equalsIgnoreCase(topicName))
			{
				reader.close();
				
				Screen.clear();
				System.out.println("This topic already exists!");
				System.out.println("Try to create it using another title.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				// By returning here, it will go back to the Topics Management Screen.
				return;
			}
		}
		reader.close();
		
		// Reads the maximum limit of posts and checks if it is valid.
		
		System.out.println();
		
		int maxPosts;
		// In case of input not being a number, the 'maxPosts' will be invalid (-1, in this case).
		try { maxPosts = Integer.parseInt(cons.readLine("Choose the maximum number of posts that the topic can have: ")); }
		catch(Exception e) { maxPosts = -1; }
		
		if(maxPosts < 0)
		{
			Screen.clear();
			System.out.println("Invalid number!");
			System.out.println("The maximum number of posts has to be 0 or more.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning here, it will go back to the Topics Management Screen.
			return;
		}
		
		// Reads the description and checks is it is valid.
		
		System.out.println();
		
		String topicDescription;
		topicDescription = cons.readLine("Choose a description: ");
		
		if(topicDescription.isEmpty())
		{
			Screen.clear();
			System.out.println("Null topic description!");
			System.out.println("You have to write something in the description.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning here, it will go back to the Topics Management Screen.
			return;
		}
		
		// The title is valid and available and the maximum number of posts and description are valid.
		// The topic is created.
		FileSystem.newTopic(topicName, topicDescription, maxPosts);
		
		Screen.clear();
		System.out.println("Topic successfully created!");
		System.out.println("---------------------------");
		cons.readPassword("Press Enter to continue...");
		Screen.clear();
		// By returning, it will go back to the Topics Management Screen. (not necessary here tho)
		return;
	}
	
	//----------------------------------------------Edit Screen-------------------------------------------------
	// Edit Screen - this is the screen that displays when the admin wants to edit an existing topic.
	// Here, the admin can choose the topic that he wants to edit and then will be asked to write the new topic informations.
	
	public static void editScreen() throws Exception
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		String topicsS;
		File topics;
		File[] listOfTopics;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			// Creating an array of files (folders that correspond to the topics) from 'Topics' folder.
			topicsS = pathHome + "/SSociety_data/Topics/";
			topics = new File(topicsS);
			// This array will only contain directories, thanks to the FileFilter 'filterDir'.
			listOfTopics = topics.listFiles(filterDir);
			
			// Displays the list of topics.
			
			System.out.println("Topics");
			System.out.println();
			System.out.println("------------------------------------------------");
			for(int i = 1; i <= listOfTopics.length; i++)
			{
				String topicName = listOfTopics[i-1].getName();
				System.out.println(i + " - " + topicName);
			}
			System.out.println("------------------------------------------------");
			System.out.println();
			System.out.printf("%d - Help\n", listOfTopics.length+1);
			System.out.printf("%d - Back\n", listOfTopics.length+2);
			System.out.println();
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input corresponds to a topic option number, it asks for the admin to choose the new topic title,
			//   description and maximum number posts that the topic can have.
			// If the chosen topic is 'About SSociety', the menu will be slightly different.
			if(chosenOption > 0 && chosenOption <= listOfTopics.length)
			{
				Screen.clear();
				
				File topic = listOfTopics[chosenOption-1];
				String topicS = topic.getPath();
				
				File description = new File(topicS + "/description.txt");
				BufferedReader reader = new BufferedReader(new FileReader(description));
				
				// Getting the current configuration of the topic.
				String curTopicName = topic.getName();
				int curMaxPosts = Integer.parseInt(reader.readLine());
				String curTopicDescription = reader.readLine();
				reader.close();
				
				// Getting the number of posts that the topic currently has.
				File posts = new File(topicS + "/Posts/");
				int numberOfPosts = posts.listFiles(filterDir).length;
				
				System.out.println("Editing topic");
				System.out.println();
				System.out.println("Current topic title: " + curTopicName);
				if(curTopicName.equals("About SSociety"))
					System.out.println("Current posts limit: unlimited!");
				else
					System.out.println("Current posts limit: " + curMaxPosts);
				System.out.println("Current number of posts: " + numberOfPosts);
				System.out.println("Current topic description: " + curTopicDescription);
				System.out.println();
				System.out.println("------------------------------------------------");
				
				String newTopicName = "";
				int newMaxPosts = 0;
				String newTopicDescription = "";
				
				// Reads the new topic name and checks if it is valid and available.
				
				if(!curTopicName.equals("About SSociety"))
				{
					System.out.println();
					newTopicName = cons.readLine("Choose the new name/title: ");
				}
				
				// If the admin wants to keep the current topic's name, will only press Enter (empty String).
				if(newTopicName.isEmpty())
					newTopicName = curTopicName;
				
				// Checking if the new topic name is valid and available.
				if(!newTopicName.equals(curTopicName))
				{
					int aux = 0;
					for(int i = 0; i < newTopicName.length(); i++)
					{
						if(!Character.isLetter(newTopicName.charAt(i)) && !Character.isDigit(newTopicName.charAt(i)) && newTopicName.charAt(i) != ' ')
						{
							Screen.clear();
							System.out.println("Topic titles can only contain letters, space character and digits.");
							System.out.println("Try to change to another title.");
							System.out.println("---------------------------");
							cons.readPassword("Press Enter to continue...");
							Screen.clear();
							
							aux = 1;
							break;
						}
					}
					if(aux == 1)
						continue; // By continuing here, it will display this screen again.
				
					// This loop will give an error message and it will go back to the Topics Management Screen if the
					//   title is already in use (or it leaves the loop, meaning that the title is available).
					int aux2 = 0;
					for(File checkTopic : listOfTopics)
					{
						if(checkTopic.getName().equalsIgnoreCase(newTopicName))
						{
							Screen.clear();
							System.out.println("That title is already in use!");
							System.out.println("Try to change to another title.");
							System.out.println("---------------------------");
							cons.readPassword("Press Enter to continue...");
							Screen.clear();
							
							aux2 = 1;
							break;
						}
					}
					if(aux2 == 1)
						continue; // By continuing here, it will display this screen again.
				}
				
				// Reads the new maximum limit of posts and checks if it is valid.
				
				String newMaxPostsS  = "";
				if(!curTopicName.equals("About SSociety"))
				{
					System.out.println();
					newMaxPostsS = cons.readLine("Choose the new maximum number of posts that the topic can have: ");
				}
				
				// If the admin wants to keep the current max limit, will only press Enter (empty String).
				if(newMaxPostsS.isEmpty())
					newMaxPostsS = Integer.toString(curMaxPosts);
				
				// In case of input not being a number, the 'newMaxPosts' will be invalid (-1, in this case).
				try { newMaxPosts = Integer.parseInt(newMaxPostsS); }
				catch(Exception e) { newMaxPosts = -1; }
				
				// Checking if the new maximum posts limit is valid.
				if(newMaxPosts != curMaxPosts)
				{
					if(newMaxPosts < 0)
					{
						Screen.clear();
						System.out.println("Invalid number!");
						System.out.println("The maximum number of posts has to be 0 or more.");
						System.out.println("---------------------------");
						cons.readPassword("Press Enter to continue...");
						Screen.clear();
						// By continuing here, it will display this screen again.
						continue;
					}
				}
				
				// Reads the new description.
				
				System.out.println();
				
				newTopicDescription = cons.readLine("Choose the new description: ");
				
				// If the admin wants to keep the current topic's description, will only press Enter (empty String).
				if(newTopicDescription.isEmpty())
					newTopicDescription = curTopicDescription;
				
				// The new title is valid and available and the new maximum number of posts and description are valid.
				// The topic is modified.
				
				// If the new limit of posts is lower than the current number of posts, the older topics are deleted.
				if(!curTopicName.equals("About SSociety"))
				{
					int postNumber = 1;
					while(newMaxPosts < numberOfPosts)
					{
						String postS = topicS + "/Posts/" + Integer.toString(postNumber) + "/";
						File post = new File(postS);
						
						if(post.exists())
						{
							FileSystem.deletePost(curTopicName, Integer.toString(postNumber));
							numberOfPosts--;
						}
						postNumber++;
					}
				}
				
				// Updating the 'description.txt' file.
				if(!newTopicDescription.equals(curTopicDescription) || newMaxPosts != curMaxPosts)
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(description));
					writer.write(Integer.toString(newMaxPosts) + "\n" + newTopicDescription);
					writer.close();
				}
				
				// Changing the topic's title.
				if(!newTopicName.equals(curTopicName))
				{
					// Changing the correspondent folder's name.
					
					File newTopic = new File(pathHome + "/SSociety_data/Topics/" + newTopicName + "/");
					topic.renameTo(newTopic);
					
					// Updating the 'topics.txt' file.
					
					// 'temptopics.txt' will be the new 'topics.txt' file, now without the deleted topic.
					File allTopics = new File (topicsS + "topics.txt");
					BufferedReader readerT = new BufferedReader(new FileReader(allTopics));
					
					File tempTopics = new File (topicsS + "temptopics.txt");
					tempTopics.createNewFile();
					BufferedWriter writerT = new BufferedWriter(new FileWriter(tempTopics, true));
					
					String line;
					while((line = readerT.readLine()) != null)
					{
						if(line.equals(curTopicName))
							writerT.append(newTopicName + "\n");
						else
							writerT.append(line + "\n");
					}
					
					readerT.close();
					writerT.close();
					
					allTopics.delete();
					tempTopics.renameTo(allTopics);
				}
				
				Screen.clear();
				System.out.println("Topic successfully edited!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				// By continuing here, it will display this screen again.
				continue;
			}
			
			// If the input corresponds to "Help" option number,
			//   it opens the help screen corresponding to the current one (Edit Screen).
			else if(chosenOption == listOfTopics.length+1)
				helpScreen("Home.TopicsManagement.editScreen");
			
			// If the input corresponds to "Back" option number,
			//   it leaves this screen and goes back to the previous screen (Topics Management Screen).
			else if(chosenOption == listOfTopics.length+2)
			{
				Screen.clear();
				// By returning, it will go back to the Topics Management Screen.
				return;
			}
			
			// Else, if the input didn't correspond to any option number, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
			}
		}
	}
	
	//---------------------------------------------Deletion Screen----------------------------------------------
	// Deletion Screen - this is the screen that displays when the admin wants to delete an existing topic.
	// Here, the admin can choose the topic that he wants to delete.
	
	public static void deletionScreen(String loggedA)
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			String topicsS = pathHome + "/SSociety_data/Topics";
			File topics = new File(topicsS);
			File[] listOfTopics = topics.listFiles(filterDir);
			
			// Displays the list of topics.
			
			System.out.println("Delete topics");
			System.out.println();
			System.out.println("------------------------------------------------");
			for(int i = 1; i <= listOfTopics.length; i++)
			{
				String topicName = listOfTopics[i-1].getName();
				
				System.out.println(i + " - " + topicName);
			}
			System.out.println("------------------------------------------------");
			System.out.println();
			System.out.printf("%d - Help\n", listOfTopics.length+1);
			System.out.printf("%d - Back\n", listOfTopics.length+2);
			System.out.println();
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input corresponds to topic option number, it checks if the deleting topic is valid and then deletes it.
			if(chosenOption > 0 && chosenOption <= listOfTopics.length)
			{
				Screen.clear();
				
				File topic = listOfTopics[chosenOption-1];
				String topicName = topic.getName();
				
				// Checks is the topic is the 'About SSociety'. If so, it can't be deleted.
				if(topicName.equals("About SSociety"))
				{
					Screen.clear();
					System.out.println("This topic can never be deleted!");
					if(loggedA.equals("admin"))
						System.out.println("Nope, not even you, Sir Super Admin. The programmer rules!");
					System.out.println("---------------------------");
					cons.readPassword("Press Enter to continue...");
					Screen.clear();
				}
				
				// The topic is valid so it will be deleted.
				else
				{
					try { FileSystem.deleteTopic(topicName, filterDir); }
					catch (Exception e) { }
					
					Screen.clear();
					System.out.println("Topic successfully deleted!");
					System.out.println("---------------------------");
					cons.readPassword("Press Enter to continue...");
					Screen.clear();
				}
			}
			
			// If the input corresponds to "Help" option number,
			//   it opens the help screen corresponding to the current one (Deletion Screen).
			else if(chosenOption == listOfTopics.length+1)
				helpScreen("Home.TopicsManagement.deletionScreen");
			
			// If the input corresponds to "Back" option number,
			//   it leaves this screen and goes back to the previous screen (Topics Management Screen).
			else if(chosenOption == listOfTopics.length+2)
			{
				Screen.clear();
				// By returning, it will go back to the Topics Management Screen.
				return;
			}
			
			// Else, if the input didn't correspond to any option number, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
			}
		}
	}
	
	//----------------------------------------------Help Screen------------------------------------------------
	// Help Screen - this is the screen that can be called in any other screen, when the user chooses the 'Help' option.
	// This screen prints instructions on how to interact with the screen where it is called.
	
	public static void helpScreen(String current)
	{
		Screen.clear();
		System.out.println("Help");
		System.out.println();
		// Calls 'screen' method of 'Help' class for the current screen, that will display instructions.
		Help.screen(current);
		// In order to leave the help screen, the user has to press Enter.
		// For that, we use the method readPassword and that way nothing that the user tries to write will appear in the screen.
		cons.readPassword("Press Enter to leave the help screen and return to the previous menu.");
		Screen.clear();
	}
}
