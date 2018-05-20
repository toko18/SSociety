package main;

import java.io.File;
import java.io.IOException;
import util.Screen;

public class FileSystem
{//class that verifies the existence of all files and folders necessary to the functioning of the program
	private static String pathHome = System.getProperty("user.home"); //string that saves the path to the user home folder
	
	public static boolean checkFileSystem() throws IOException
	{
		String dir1 = pathHome + "/" + "SSociety_data/";
		File d1 = new File(dir1);//represents the SSociety_data folder
		
		String dir2 = dir1 + "Topics/";
		File d2 = new File(dir2); //represents the Topics Folder
		
		String dir3 = dir2 + "About SSociety/";
		File d3 = new File(dir3); //represents the folder that contains the posts of the topic About SSociety
		
		String file1 = dir3 + "description.txt";
		File f1 = new File(file1); //represents the file that contains the description of the topic About SSociety
		
		String file9 = dir3 + "subscriptions.txt";
		File f9 = new File(file9); //represents the file that contains the username of all the users that subscribe the topic About SSociety
		
		String dir4 = dir1 + "Users/";
		File d4 = new File(dir4); //represents the general users folder
		
		String dir5 = dir4 + "Banned/";
		File d5 = new File(dir5); //represents the folder with the information about banished users
		
		String dir6 = dir4 + "PendingAdmins/";
		File d6 = new File(dir6); //represents the folder that contains pending admins
		
		String dir7 = dir4 + "PendingOthers/";
		File d7 = new File(dir7); //represents the folder that contains pending users
		
		String file2 = dir4 + "admins.txt";
		File f2 = new File(file2); //file that has information on admins
		
		String dir8 = dir4 + "AllUsers/";
		File d8 = new File(dir8); //represents the folder that has the folder of all accepted users on SSociety
		
		String dir9 = dir8 + "admin/";
		File d9 = new File(dir9); //represents the admin folder on AllUsers folder
		
		String file3 = dir9 + "password.txt";
		File f3 = new File(file3); //file that contains the admin password
		
		String file4 = dir9 + "likes.txt";
		File f4 = new File(file4); //file that contains the likes the admin left on other posts
		
		String file5 = dir9 + "posts.txt";
		File f5 = new File(file5); //file that contains information about the posts written by the admin (topic + number)
		
		String file6 = dir9 + "subscriptions.txt";
		File f6 = new File(file6); //file that contains the the topics the admin is subscribed
		
		String file7 = dir2 + "topics.txt";
		File f7 = new File(file7); //file inside the Topics folder that contains all the topics on SSociety
		
		String dir10 = dir3 + "Posts/";
		File d10 = new File(dir10); //Folder that contains the posts written on About SSociety Topic

		
		if (!d1.exists() || !d2.exists() || !d3.exists() || !f1.exists() || !d4.exists() || !d5.exists() || !d6.exists() || !d7.exists() || !f2.exists() || !d8.exists() || !d9.exists() || !f3.exists() || !f4.exists() || !f5.exists() || !f6.exists() || !f7.exists() || !d10.exists() || !f9.exists())
		{	//checks if the all the necessary files exist
			Screen.clear();
			System.out.println("Problem found while trying to start the program.");
			System.out.println("Wait for an admin to fix it. Sorry for the trouble..."); //if something fails the program exists graciously and without errors
			return false;
		}
		return true; //if true is returned the firstScreen appears
	}
}
