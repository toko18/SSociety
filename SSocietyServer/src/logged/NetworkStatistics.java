package logged;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import util.Help;
import util.Screen;

//---------------------------------------------*NetworkStatistics*----------------------------------------------
// This is the class that has the screens of the Network Statistics's menu.

public class NetworkStatistics
{
	// A Console object ('cons') will be used to read the user input.
	private static Console cons = System.console();
	
	// Finds the path of user's computer home directory
	private static String pathHome = System.getProperty("user.home");
	
	// This FilenameFilter will be used to filter files that have ".txt" suffix.
	private static FilenameFilter filtertxt = new FilenameFilter()
	{
		public boolean accept(File file, String name)
		{
			return name.endsWith(".txt");
		}
	};
	
	// This FileFilter will be used to filter directories.
	private static FileFilter filterDir = new FileFilter()
	{
		public boolean accept(File file)
		{
			return file.isDirectory();
		}
	};
	
	//-----------------------------------------Topics Statistics Screen----------------------------------------
	// Topics Statistics Screen - this is the screen that displays when the admin wants to see statistics about the topics.
	// Here, the admin can select the topic from which he wants to view the statistics.
	
	public static void topicsScreen() throws Exception
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
			
			// Getting the number of topics.
			int numberOfTopics = listOfTopics.length;
			
			// Displays some general statistics and the list of topics.
			
			System.out.println("Topics Statistics");
			System.out.println();
			System.out.println("Number of active topics: " + numberOfTopics);
			System.out.println();
			System.out.println("Active topics");
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
			
			// If the input corresponds to a topic option number, it opens statistics of the selected topic.
			if(chosenOption > 0 && chosenOption <= listOfTopics.length)
			{
				Screen.clear();
				
				// Getting the topic's title.
				File topic = listOfTopics[chosenOption-1];
				String topicName = topic.getName();
				String topicS = topic.getPath();
				
				// Getting the number of subscribers that the topic has.
				File subscriptions = new File(topicS + "/subscriptions.txt");
				BufferedReader readerSubs = new BufferedReader(new FileReader(subscriptions));
				int numberOfSubs = 0;
				while(readerSubs.readLine() != null)
					numberOfSubs++;
				readerSubs.close();
				
				// Getting the number of posts that the topic has.
				File posts = new File(topicS + "/Posts/");
				int numberOfPosts = posts.listFiles(filterDir).length;
				
				// Displaying the info.
				
				System.out.println("Topic's title: " + topicName);
				System.out.println();
				System.out.println("Number of subscribers: " + numberOfSubs);
				System.out.println("Number of posts: " + numberOfPosts);
				System.out.println();
				System.out.println("--------------------------------------------");
				cons.readPassword("Press Enter to return to the previous menu.");
				Screen.clear();
			}
			
			// If the input corresponds to "Help" option number,
			//   it opens the help screen corresponding to the current one (Topics Statistics Screen).
			else if(chosenOption == listOfTopics.length+1)
				helpScreen("Home.NetworkStatistics.topicsScreen");
			
			// If the input corresponds to "Back" option number,
			//   it leaves this screen and goes back to the previous screen (Network Statistics Screen).
			else if(chosenOption == listOfTopics.length+2)
			{
				Screen.clear();
				// By returning, it will go back to the Network Statistics Screen.
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
	
	//-----------------------------------------Accounts Statistics Screen---------------------------------------
	// Accounts Statistics Screen - this is the screen that displays when the admin wants to see statistics about the active users.
	// Here, the admin can select the active account from which he wants to view the statistics.
	
	public static void accountsScreen() throws Exception
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			String allUsersS = pathHome + "/SSociety_data/Users/AllUsers/";
			File allUsers = new File(allUsersS);
			File[] listOfUsers = allUsers.listFiles(filterDir);
			
			// Getting the number of active accounts.
			int numberOfAccounts = listOfUsers.length;
			
			// Getting the number of admins (even if they are banned).
			File admins = new File(pathHome + "/SSociety_data/Users/admins.txt");
			BufferedReader readerA = new BufferedReader(new FileReader(admins));
			int numberOfAdmins = 0;
			while(readerA.readLine() != null)
				numberOfAdmins++;
			readerA.close();
			
			// Getting the number of pending admins.
			File pendingA = new File(pathHome + "/SSociety_data/Users/PendingAdmins/");
			int numberOfPendingA = pendingA.listFiles(filtertxt).length;
			
			// Getting the number of pending others.
			File pendingO = new File(pathHome + "/SSociety_data/Users/PendingOthers/");
			int numberOfPendingO = pendingO.listFiles(filtertxt).length;
			
			// Getting the number of banned accounts.
			File banned = new File(pathHome + "/SSociety_data/Users/Banned/");
			int numberOfBanned = banned.listFiles(filtertxt).length;
						
			// Displays some general statistics and the list of active accounts.
			
			System.out.println("Accounts Statistics");
			System.out.println();
			System.out.println("Number of active accounts: " + numberOfAccounts);
			System.out.println("Number of admins: " + numberOfAdmins);
			System.out.println("Number of pending admin accounts: " + numberOfPendingA);
			System.out.println("Number of pending normal user accounts: " + numberOfPendingO);
			System.out.println("Number of banned accounts: " + numberOfBanned);
			System.out.println();
			System.out.println("Active accounts");
			System.out.println("------------------------------------------------");
			for(int i = 1; i <= listOfUsers.length; i++)
			{
				String username = listOfUsers[i-1].getName();
				
				System.out.println(i + " - " + username);
			}
			System.out.println("------------------------------------------------");
			System.out.println();
			System.out.printf("%d - Help\n", listOfUsers.length+1);
			System.out.printf("%d - Back\n", listOfUsers.length+2);
			System.out.println();
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input corresponds to an account option number, it opens statistics of the selected account.
			if(chosenOption > 0 && chosenOption <= listOfUsers.length)
			{
				Screen.clear();
				
				// Getting the account's username.
				File account = listOfUsers[chosenOption-1];
				String username = account.getName();
				String accountS = account.getPath();
				
				// Getting the number of likes that the user has given.
				File likes = new File(accountS + "/likes.txt");
				BufferedReader readerLikes = new BufferedReader(new FileReader(likes));
				int numberOfLikes = 0;
				while(readerLikes.readLine() != null)
					numberOfLikes++;
				readerLikes.close();
				
				// Getting the number of posts that the user has written.
				File posts = new File(accountS + "/posts.txt");
				BufferedReader readerPosts = new BufferedReader(new FileReader(posts));
				int numberOfPosts = 0;
				while(readerPosts.readLine() != null)
					numberOfPosts++;
				readerPosts.close();
				
				// Getting the topics that the user is subscribed to.
				File subscriptions = new File(accountS + "/subscriptions.txt");
				BufferedReader readerSubs = new BufferedReader(new FileReader(subscriptions));
				String line;
				ArrayList<String> subs = new ArrayList<String>();
				while((line = readerSubs.readLine()) != null)
					subs.add(line.substring(0, line.indexOf('|')));
				readerSubs.close();
				
				// Displaying the info.
				
				System.out.println("Account's username: " + username);
				System.out.println();
				System.out.println("Number of given likes: " + numberOfLikes);
				System.out.println("Number of posts: " + numberOfPosts);
				if(subs.isEmpty())
					System.out.println("Subscribed topics: this user isn't subscribed to any topics.");
				else
				{
					System.out.println("Subscribed topics: - " + subs.get(0));
					for(int i = 1; i < subs.size(); i++)
						System.out.println("                   - " + subs.get(i));
					
				}
				System.out.println();
				System.out.println("--------------------------------------------");
				cons.readPassword("Press Enter to return to the previous menu.");
				Screen.clear();
			}
			
			// If the input corresponds to "Help" option number,
			//   it opens the help screen corresponding to the current one (Accounts Statistics Screen).
			else if(chosenOption == listOfUsers.length+1)
				helpScreen("Home.NetworkStatistics.accountsScreen");
			
			// If the input corresponds to "Back" option number,
			//   it leaves this screen and goes back to the previous screen (Network Statistics Screen).
			else if(chosenOption == listOfUsers.length+2)
			{
				Screen.clear();
				// By returning, it will go back to the Network Statistics Screen.
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
