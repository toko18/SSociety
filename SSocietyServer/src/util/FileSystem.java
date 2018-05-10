package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// This class ('FileSystem') has methods for managing the network data files.
// It creates, deletes and moves files, depending on what the method is for.
// The class will be called almost in any part of the program.

public class FileSystem
{
	// Finds the path of user's computer home directory
	private static String pathHome = System.getProperty("user.home");
	
	// These variables are paths that the methods need.
	
	private static String rootS = pathHome + "/" + "SSociety_data/";
	private static File root = new File(rootS);
	
	private static String topicsS = rootS + "Topics/";
	private static File topics = new File(topicsS);
	
	private static String usersS = rootS + "Users/";
	private static File users = new File(usersS);
	
	private static String bannedS = usersS + "Banned/";
	private static File banned = new File(bannedS);
	
	private static String pendingAS = usersS + "PendingAdmins/";
	private static File pendingA = new File(pendingAS);
	
	private static String pendingOS = usersS + "PendingOthers/";
	private static File pendingO = new File(pendingOS);
	
	private static String adminsS = usersS + "admins.txt";
	private static File admins = new File(adminsS);
	
	private static String allUsersS = usersS + "AllUsers/";
	private static File allUsers = new File(allUsersS);
	
	// The method 'setup' will be called whenever the program executes, to create the default file system of the network data.
	// The data is kept in a directory named SSociety_data, inside home directory of the computer user.
	// In case some default file is deleted, the method will create it, without compromising the other files.
	
	public static void setup() throws IOException
	{
		if(!root.exists())
			root.mkdir();
		
		if(!topics.exists())
			topics.mkdir();
		
		String topicAboutS = topicsS + "About SSociety/";
		File topicAbout = new File(topicAboutS);
		if(!topicAbout.exists())
			topicAbout.mkdir();
		
		String descriptionAboutS = topicAboutS + "description.txt";
		File descriptionAbout = new File(descriptionAboutS);
		if(!descriptionAbout.exists())
		{
			descriptionAbout.createNewFile();
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(descriptionAboutS));
			writer1.write("Descriçao sobre a SSociety\nou\nsobre o projeto\nou\nambos");
			writer1.append("\n(Possivelmente com parágrafos)");
			writer1.close();
		}
		
		if(!users.exists())
			users.mkdir();
		
		if(!banned.exists())
			banned.mkdir();
		
		if(!pendingA.exists())
			pendingA.mkdir();
		
		if(!pendingO.exists())
			pendingO.mkdir();
		
		if(!admins.exists())
		{
			admins.createNewFile();
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(adminsS));
			writer2.write("admin");
			writer2.close();
		}
		
		if(!allUsers.exists())
			allUsers.mkdir();
		
		String dirAdminS = allUsersS + "admin/";
		File dirAdmin = new File(dirAdminS);
		if(!dirAdmin.exists())
			dirAdmin.mkdir();
		
		String passAdminS = dirAdminS + "password.txt";
		File passAdmin = new File(passAdminS);
		if(!passAdmin.exists())
		{
			passAdmin.createNewFile();
			BufferedWriter writer3 = new BufferedWriter(new FileWriter(passAdminS));
			writer3.write("elonmusk");
			writer3.close();
		}
		
		String likesAdminS = dirAdminS + "likes.txt";
		File likesAdmin = new File(likesAdminS);
		if(!likesAdmin.exists())
			likesAdmin.createNewFile();
		
		String postsAdminS = dirAdminS + "posts.txt";
		File postsAdmin = new File(postsAdminS);
		if(!postsAdmin.exists())
			postsAdmin.createNewFile();
		
		String subsAdminS = dirAdminS + "subscriptions.txt";
		File subsAdmin = new File(subsAdminS);
		if(!subsAdmin.exists())
		{
			subsAdmin.createNewFile();
			BufferedWriter writer4 = new BufferedWriter(new FileWriter(subsAdminS));
			writer4.write("About SSociety|.");
			writer4.close();
		}
	}
	
	// The method 'newAdmin' will be called when a new account registration occurs.
	// This method creates the default files of a new user (in this program context, an admin) with the username
	//   and password that receives as arguments (String's).
	
	public static void newAdmin(String u, String p) throws IOException
	{
		// Has this program only registers admins, the new account files will go for the 'PendingAdmins' folder.
		String dirUserS = pendingAS + u + "/";
		File dirUser = new File(dirUserS);
		dirUser.mkdir();
		
		String passUserS = dirUserS + "password.txt";
		File passUser = new File(passUserS);
		passUser.createNewFile();
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(passUserS));
		writer1.write(p);
		writer1.close();
		
		String likesUserS = dirUserS + "likes.txt";
		File likesUser = new File(likesUserS);
		likesUser.createNewFile();
		
		String postsUserS = dirUserS + "posts.txt";
		File postsUser = new File(postsUserS);
		postsUser.createNewFile();
		
		String subsUserS = dirUserS + "subscriptions.txt";
		File subsUser = new File(subsUserS);
		subsUser.createNewFile();
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(subsUserS));
		// All users are subscribed to the 'About SSociety' topic.
		writer2.write("About SSociety|.");
		writer2.close();
	}
	
	// The method 'newDirect' will be called when a new direct registration occurs ('DirectRegistration' class).
	// This method creates the default files of a new user (admin or normal user) with the username
	//   and password that receives as arguments (String's). The third argument is the type of user (is says
	//   if it's an admin account ("admin") or a normal account ("other").
	
	public static void newDirect(String u, String p, String t) throws IOException
	{
		// The new account files will go directly for the "AllUsers" folder, as they are being directly created,
		//   without needing admin's validation.
		String dirUserS = allUsersS + u + "/";
		File dirUser = new File(dirUserS);
		dirUser.mkdir();
		
		String passUserS = dirUserS + "password.txt";
		File passUser = new File(passUserS);
		passUser.createNewFile();
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(passUserS));
		writer1.write(p);
		writer1.close();
		
		String likesUserS = dirUserS + "likes.txt";
		File likesUser = new File(likesUserS);
		likesUser.createNewFile();
		
		String postsUserS = dirUserS + "posts.txt";
		File postsUser = new File(postsUserS);
		postsUser.createNewFile();
		
		String subsUserS = dirUserS + "subscriptions.txt";
		File subsUser = new File(subsUserS);
		subsUser.createNewFile();
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(subsUserS));
		// All users are subscribed to the 'About SSociety' topic.
		writer2.write("About SSociety|.");
		writer2.close();
		
		// If the new account is for an admin, the username will be written to the 'admins.txt' file.
		if(t.equals("admin"))
		{
			BufferedWriter writer3 = new BufferedWriter(new FileWriter(adminsS, true));
			writer3.append("\n" + u);
			writer3.close();
		}
	}
}
