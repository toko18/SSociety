package settings;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import logged.Home;
import main.SSocietyClient;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;

import util.Screen;

public class AccountSettings {
	//----------------------------------------all the paths used in this class---------------------------------
	private static String pathHome = System.getProperty("user.home");
	
	private static String rootS = pathHome + "/SSociety_data/";
	
	private static String topicsS = rootS + "Topics/";
	
	private static String usersS = rootS + "Users/";
	
	private static String allUsersS = usersS + "AllUsers/";
	//------------------------------------------------------------------------------------------------------------
	
	private static Console cons = System.console(); //allows the use of the java console in this class
	
	private String username; //username of the user
	private String userFolder; //folder of the user in the file System
	
	static final String BOLD = "\033[1m"; //starts bold
	static final String RESET = "\033[0m"; //ends bold
	
	public AccountSettings(String u)
	{//constructor that initializes the variables in this class
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/Allusers/" + username;
	}
	
	public void settingsScreen() throws Exception
	{
		while (true)
		{
			int chosenOption = 0; //option chosen by the user
			
			//what the user sees when he opens this screen
			System.out.println("-------------------------");
			System.out.println(BOLD + "Settings" + RESET);
			System.out.println("-------------------------");
			System.out.println("In this section you can change your username, change your password and delete your account.");
			System.out.println("-------------------------");
			System.out.println("1 - Change password");
			System.out.println("2 - Delete account");
			System.out.println("3 - Back");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) //ensures that input that is not a int does not crashes the program
			{ //refreshes the screen
				chosenOption = 0;
				Screen.clear();
			}
			
			if(chosenOption == 1)
			{//method that changes the password
				changePassword();
				return;
			}
			else if(chosenOption == 2)
			{//method that deletes users account
				deleteAccountData(username);
				Screen.clear();
				System.out.println("Your account has been deleted!");
				cons.readLine("Press Enter to continue...");
				Screen.clear();
				SSocietyClient.firstScreen();
				return;
			}
			else if(chosenOption == 3)
			{//go back to user homescreen 
				Home back = new Home(username);
				Screen.clear();
				back.homeScreen();
				return;
			}
			else
			{//error message when invalid input has been entered
				Screen.clear();
				System.out.println("Invalid input! Try again...");
				System.out.println();
			}
		}
	}
	
	private void changePassword() throws Exception
	{
		while(true)
		{
			Screen.clear();
			char[] nP = cons.readPassword("Choose new Password: ");
			char[] repeatP = cons.readPassword("Write again the password: ");
			
			String newPassword = new String(nP);
			String repeatPassword = new String(repeatP);
			
			if(newPassword.equals("") && repeatPassword.equals(""))
			{//check if passwords are not empty
				Screen.clear();
				System.out.println("You have to choose a password that is not empty. Try again!");
				cons.readLine("Press Enter to try again...");
			}
			else if(newPassword.equals(repeatPassword))
			{//passwords match and are not empty
				File currentPasswordFile = new File(userFolder + "/password.txt");
				File newPasswordFile = new File(userFolder + "new password.txt");
				newPasswordFile.createNewFile(); //creates new passsword file
				
				FileWriter write = new FileWriter(newPasswordFile);
				BufferedWriter writeNewPassword = new BufferedWriter(write);
				writeNewPassword.write(newPassword); //writes password to new file
				
				currentPasswordFile.delete(); //deletes old password file and renames the most recent on to maintain naming convention
				newPasswordFile.renameTo(currentPasswordFile);
				writeNewPassword.close();
				
				Screen.clear();//user sees this message when the password is changed successfully
				System.out.println("Password changed successfully!");
				cons.readLine("Press Enter to return...");
				Screen.clear();
				
				settingsScreen();
			}
			else 
			{//when the inserted passwords do not match
				Screen.clear();
				System.out.println("Passwords dont't match!");
				cons.readLine("Press Enter to try again...");
			}
		}
	}
	
//----------------------------------------------deleteFolder-----------------------------------------------
	// The 'deleteFolder' is a auxiliary method that will be called whenever we need to delete a folder (and their files).
	// This method deletes the files within a folder (given as argument) and then deletes the now empty folder.
	
	public static void deleteFolder(File dir)
	{
		File[] listOfFiles = dir.listFiles();
		
		for(File file : listOfFiles)
			file.delete();
		
		dir.delete();
	}
		
	//-----------------------------------------------deleteLike------------------------------------------------
	// The method 'deleteLike' will be called by the 'deleteAccountData' and 'deletePost' methods.
	// This method deletes a like (and updates all files with information correspondent to that like), given the topic name,
	//   the post name and the username of the account that gave the like as arguments.
	// It requires to modify the 'likes.txt' file of the post and user's folder and modify the 'received likes.txt' file of the post owner.
	
	public static void deleteLike(String topicName, String postName, String username) throws Exception
	{	
		// Auxiliary variable.
		String line;
		
		// Modifying the 'likes.txt' file of the post.
		
		String dirPostS = topicsS + topicName + "/Posts/" + postName + "/";
		
		File postLikes = new File(dirPostS + "likes.txt");
		BufferedReader readerPL = new BufferedReader(new FileReader(postLikes));
		
		// 'temppostlikes.txt' will be the new 'likes.txt' file, now without the like given by this account.
		File tempPostLikes = new File (dirPostS + "temppostlikes.txt");
		tempPostLikes.createNewFile();
		BufferedWriter writerPL = new BufferedWriter(new FileWriter(tempPostLikes, true));
		
		while((line = readerPL.readLine()) != null)
			if(!line.equals(username))
				writerPL.append(line + "\n");
		
		readerPL.close();
		writerPL.close();
		
		postLikes.delete();
		tempPostLikes.renameTo(postLikes);
		
		// Modifying the 'likes.txt' file of the user's folder.
		
		String dirUserS = allUsersS + username + "/";
		
		File userLikes = new File(dirUserS + "likes.txt");
		BufferedReader readerUL = new BufferedReader(new FileReader(userLikes));
		
		// 'tempuserlikes.txt' will be the new 'likes.txt' file, now without the unliked post.
		File tempUserLikes = new File (dirUserS + "tempuserlikes.txt");
		tempUserLikes.createNewFile();
		BufferedWriter writerUL = new BufferedWriter(new FileWriter(tempUserLikes, true));
		
		while((line = readerUL.readLine()) != null)
			if(!line.equals(topicName + "|" + postName))
				writerUL.append(line + "\n");
		
		readerUL.close();
		writerUL.close();
		
		// In the end, it deletes the old file and changes the name of the updated one.
		userLikes.delete();
		tempUserLikes.renameTo(userLikes);
		
		// Modifying the 'received likes.txt' file of the post owner.
		
		// Getting the username of the post owner.
		File post = new File(dirPostS + "post.txt");
		BufferedReader readerPost = new BufferedReader(new FileReader(post));
		@SuppressWarnings("unused")
		String postOwner = readerPost.readLine();
		readerPost.close();
	}
	
	//-----------------------------------------------deletePost------------------------------------------------
	// The method 'deletePost' will be called by the 'deleteAccountData' and 'deleteTopic' methods.
	// This method deletes a post (and updates all files with information correspondent to that post), given the topic name
	//   and the post name as arguments.
	// It requires to delete the likes that the post has, to modify the 'posts.txt' file of the user and then delete the post folder.
	
	public static void deletePost(String topicName, String postName) throws Exception
	{
		// Auxiliary variable.
		String line;
		
		String dirPostS = topicsS + topicName + "/Posts/" + postName + "/";
		File dirPost = new File(dirPostS);
		
		// Deleting the post likes.
		
		File postLikes = new File(dirPostS + "likes.txt");
		BufferedReader readerPL = new BufferedReader(new FileReader(postLikes));
		
		// Passing the lines of the 'likes.txt' file to an Array List because it will be constantly modifying:
		//   we can't simply read each line and delete the like in a loop because the file will change.
		ArrayList<String> likesToDel = new ArrayList<String>();
		while((line = readerPL.readLine()) != null)
			likesToDel.add(line);
		
		readerPL.close();
		
		for(String likeDel : likesToDel)
			deleteLike(topicName, postName, likeDel);
		
		// Deleting the post from 'posts.txt' file of the user.
		
		// Getting the username of the post owner.
		File post = new File(dirPostS + "post.txt");
		BufferedReader readerPost = new BufferedReader(new FileReader(post));
		String postOwner = readerPost.readLine();
		readerPost.close();
		
		File userPosts = new File(allUsersS + postOwner + "/posts.txt");
		BufferedReader readerUP = new BufferedReader(new FileReader(userPosts));
		
		// 'tempuserposts.txt' will be the new 'posts.txt' file, now without the deleted post.
		File tempUserPosts = new File (allUsersS + postOwner + "/tempuserposts.txt");
		tempUserPosts.createNewFile();
		BufferedWriter writerUP = new BufferedWriter(new FileWriter(tempUserPosts, true));
		
		while((line = readerUP.readLine()) != null)
			if(!line.equals(topicName + "|" + postName))
				writerUP.append(line + "\n");
					
		readerUP.close();
		writerUP.close();
		
		// In the end, it deletes the old file and changes the name of the updated one.
		userPosts.delete();
		tempUserPosts.renameTo(userPosts);
		
		// Deleting the actual directory of the post.
		
		deleteFolder(dirPost);
	}

	//-----------------------------------------------deleteSub-------------------------------------------------
	// The method 'deleteSub' will be called by the 'deleteAccountData' and 'deleteTopic' methods.
	// This method deletes a subscription (and updates all files with information correspondent to that sub), given the topic name
	//   and the username of the account that was subscribed to the topic as arguments.
	// It requires to modify the 'subscriptions.txt' file of the post and user's folder.
	
	public static void deleteSub(String topicName, String username) throws Exception
	{
		// Auxiliary variable.
		String line;
		
		// Updating the 'subscriptions.txt' file of the topic's folder.
		
		String dirTopicS = topicsS + topicName + "/";
		
		File subsTopic = new File(dirTopicS + "subscriptions.txt");
		BufferedReader readerST = new BufferedReader(new FileReader(subsTopic));
		
		// 'tempsubscriptions.txt' will be the new 'subscriptions.txt' file, now without the sub given by this account.
		File tempSubsTopic = new File (dirTopicS + "tempsubscriptions.txt");
		tempSubsTopic.createNewFile();
		BufferedWriter writerST = new BufferedWriter(new FileWriter(tempSubsTopic, true));
		
		while((line = readerST.readLine()) != null)
			if(!line.equals(username))
				writerST.append(line + "\n");
					
		readerST.close();
		writerST.close();
		
		subsTopic.delete();
		tempSubsTopic.renameTo(subsTopic);
		
		// Updating the 'subscriptions.txt' file of the user's folder.
		
		String dirUserS = allUsersS + username + "/";
		
		File subsUser = new File(dirUserS + "subscriptions.txt");
		BufferedReader readerSU = new BufferedReader(new FileReader(subsUser));
		
		// 'tempsubscriptions.txt' will be the new 'subscriptions.txt' file, now without the unsubscribed topic.
		File tempSubsUser = new File (dirUserS + "tempsubscriptions.txt");
		tempSubsUser.createNewFile();
		BufferedWriter writerSU = new BufferedWriter(new FileWriter(tempSubsUser, true));
		
		while((line = readerSU.readLine()) != null)
			if(!line.substring(0, line.indexOf('|')).equals(topicName))
				writerSU.append(line + "\n");
					
		readerSU.close();
		writerSU.close();
		
		subsUser.delete();
		tempSubsUser.renameTo(subsUser);
	}
	
	//--------------------------------------------deleteAccountData--------------------------------------------
	// The method 'deleteAccountData' will be called when an account is banned or deleted.
	// This method removes all the data related to the account which username is passed as argument (by calling
	//   the methods 'deleteLike', 'deletePost', 'deleteSub' and 'deleteFolder').
	// It basically deletes all posts, likes and subscriptions that the account has, and then the account folder.
	
	public static void deleteAccountData(String u) throws Exception
	{
		// These variables are paths/files that will be modified.
		
		String dirUserS = allUsersS + u + "/";
		File dirUser = new File(dirUserS);
		
		String likesUserS = dirUserS + "likes.txt";
		File likesUser = new File(likesUserS);
		BufferedReader readerLikes = new BufferedReader(new FileReader(likesUser));
		
		String postsUserS = dirUserS + "posts.txt";
		File postsUser = new File(postsUserS);
		BufferedReader readerPosts = new BufferedReader(new FileReader(postsUser));
		
		String subsUserS = dirUserS + "subscriptions.txt";
		File subsUser = new File(subsUserS);
		BufferedReader readerSubs = new BufferedReader(new FileReader(subsUser));
		
		// Auxiliary variable.
		String line;
		
		// Deleting likes that the user has done.
		
		// Passing the lines of the 'likes.txt' file to an Array List because it will be constantly modifying:
		//   we can't simply read each line and delete the like in a loop because the file will change.
		ArrayList<String> likesToDel = new ArrayList<String>();
		while((line = readerLikes.readLine()) != null)
			likesToDel.add(line);
		
		readerLikes.close();
		
		for(String likeDel : likesToDel)
		{
			String topicName = likeDel.substring(0, likeDel.indexOf('|'));
			String postName = likeDel.substring(likeDel.indexOf('|') + 1);
			
			deleteLike(topicName, postName, u);
		}
		
		// Deleting posts that the user has done.
		
		// Passing the lines of the 'posts.txt' file to an Array List because it will be constantly modifying:
		//   we can't simply read each line and delete the post in a loop because the file will change.
		ArrayList<String> postsToDel = new ArrayList<String>();
		while((line = readerPosts.readLine()) != null)
			postsToDel.add(line);
		
		readerPosts.close();
		
		for(String postDel : postsToDel)
		{
			String topicName = postDel.substring(0, postDel.indexOf('|'));
			String postName = postDel.substring(postDel.indexOf('|') + 1);
			
			deletePost(topicName, postName);
		}
		
		// Deleting subscriptions that the user has done.
		
		// Passing the lines of the 'subscriptions.txt' file to an Array List because it will be constantly modifying:
		//   we can't simply read each line and delete the subscription in a loop because the file will change.
		ArrayList<String> subsToDel = new ArrayList<String>();
		while((line = readerSubs.readLine()) != null)
			subsToDel.add(line);
		
		readerSubs.close();
		
		for(String subDel : subsToDel)
		{
			String topicName = subDel.substring(0, subDel.indexOf('|'));
			
			deleteSub(topicName, u);
		}
		
		// Now that all posts, likes and subscriptions that the account had are deleted and the files updated,
		//   the account folder is deleted.
		
		deleteFolder(dirUser);
		Screen.clear();
		System.out.println("Your account has been successfully deleted... We hope you come back!");
		System.out.println();
		SSocietyClient.firstScreen();
		return;
	}
	
}