package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//----------------------------------------------*FileSystem*---------------------------------------------------
// This class ('FileSystem') has methods for managing the network data files.
// It creates, deletes and moves files, depending on what the method is for.
// The class will be called almost in any part of the program.

public class FileSystem
{
	// Finds the path of user's computer home directory
	private static String pathHome = System.getProperty("user.home");
	
	// These variables are paths/files that the methods need.
	
	private static String rootS = pathHome + "/SSociety_data/";
	private static File root = new File(rootS);
	
	private static String topicsS = rootS + "Topics/";
	private static File topics = new File(topicsS);
	
	private static String allTopicsS = topicsS + "topics.txt";
	private static File allTopics = new File(allTopicsS);
	
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
	
	//------------------------------------------------setup----------------------------------------------------
	// The method 'setup' will be called whenever the program executes, to create the default file system of the network data.
	// The data is kept in a directory named SSociety_data, inside home directory of the computer user.
	// In case some default file is deleted, the method will create it, without compromising the other files.
	
	public static void setup() throws IOException
	{
		if(!root.exists())
			root.mkdir();
		
		if(!topics.exists())
			topics.mkdir();
		
		if(!allTopics.exists())
		{
			allTopics.createNewFile();
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(allTopics));
			writer1.write("About SSociety\n");
			writer1.close();
		}
		
		String topicAboutS = topicsS + "About SSociety/";
		File topicAbout = new File(topicAboutS);
		if(!topicAbout.exists())
			topicAbout.mkdir();
		
		String descriptionAboutS = topicAboutS + "description.txt";
		File descriptionAbout = new File(descriptionAboutS);
		if(!descriptionAbout.exists())
		{
			descriptionAbout.createNewFile();
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(descriptionAboutS));
			writer2.write("-1\nDescriçao sobre a SSociety.");
			writer2.close();
		}
		
		String subsS = topicAboutS + "subscriptions.txt";
		File subs = new File(subsS);
		if(!subs.exists())
		{
			subs.createNewFile();
			BufferedWriter writer3 = new BufferedWriter(new FileWriter(subsS));
			writer3.write("admin\n");
			writer3.close();
		}
		
		String postsAboutS = topicAboutS + "Posts/";
		File postsAbout = new File(postsAboutS);
		if(!postsAbout.exists())
			postsAbout.mkdir();
		
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
			BufferedWriter writer4 = new BufferedWriter(new FileWriter(adminsS));
			writer4.write("admin\n");
			writer4.close();
		}
		
		if(!allUsers.exists())
			allUsers.mkdir();
		
		// Creating the super admin account.
		
		String dirAdminS = allUsersS + "admin/";
		File dirAdmin = new File(dirAdminS);
		if(!dirAdmin.exists())
			dirAdmin.mkdir();
		
		String passAdminS = dirAdminS + "password.txt";
		File passAdmin = new File(passAdminS);
		if(!passAdmin.exists())
		{
			passAdmin.createNewFile();
			BufferedWriter writer5 = new BufferedWriter(new FileWriter(passAdminS));
			writer5.write("elonmusk");
			writer5.close();
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
			BufferedWriter writer6 = new BufferedWriter(new FileWriter(subsAdminS));
			writer6.write("About SSociety|.\n");
			writer6.close();
		}
		
		String receivedS = dirAdminS + "received likes.txt";
		File received = new File(receivedS);
		if(!received.exists())
		{
			received.createNewFile();
			BufferedWriter writer7 = new BufferedWriter(new FileWriter(receivedS));
			writer7.write("0");
			writer7.close();
		}
	}

	//---------------------------------------------registAdmin-------------------------------------------------
	// The method 'registAdmin' will be called when a new account registration occurs (in this program context, an admin).
	// This method creates the text file with name "(username).txt" and the password as content.
	
	public static void registAdmin(String u, String p) throws IOException
	{
		// Has this program only registers admins, the file will go to the 'PendingAdmins' folder.
		String fileUserS = pendingAS + u + ".txt";
		File fileUser = new File(fileUserS);
		fileUser.createNewFile();
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(fileUserS));
		writer1.write(p);
		writer1.close();
	}
	
	//-----------------------------------------------newAccount------------------------------------------------
	// The method 'newAccount' will be called when a new direct registration occurs ('DirectRegistration' class)
	//   or a pending account is validated by an admin.
	// This method creates the default files of a new user (admin or normal user) with the username
	//   and password that receives as arguments (String's). The third argument is the type of user (is says
	//   if it's an admin account ("admins") or a normal account ("others").
	
	public static void newAccount(String u, String p, String t) throws IOException
	{
		// The new account files will go to the "AllUsers" folder, either if it is an admin or a normal user account.
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
		// All users are subscribed to the 'About SSociety' topic by default.
		writer2.write("About SSociety|.\n");
		writer2.close();
		
		String receivedS = dirUserS + "received likes.txt";
		File received = new File(receivedS);
		received.createNewFile();
		BufferedWriter writer3 = new BufferedWriter(new FileWriter(receivedS));
		writer3.write("0");
		writer3.close();
		
		// Whenever a new account is created, the username is written on 'subscriptions.txt' file of
		//   'About SSociety' topic, since all users are subscribed to this topic by default.
		File subscriptions = new File(topicsS + "About SSociety/subscriptions.txt");
		BufferedWriter writerS = new BufferedWriter(new FileWriter(subscriptions, true));
		
		writerS.append(u + "\n");
		writerS.close();
		
		// If the new account is for an admin, the username will be written to the 'admins.txt' file.
		if(t.equals("admins"))
		{
			BufferedWriter writer4 = new BufferedWriter(new FileWriter(adminsS, true));
			writer4.append(u + "\n");
			writer4.close();
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
		String postOwner = readerPost.readLine();
		readerPost.close();
		
		File receivedLikes = new File(allUsersS + postOwner + "/received likes.txt");
		BufferedReader readerRL = new BufferedReader(new FileReader(receivedLikes));
		
		String nLikesS = readerRL.readLine();
		int nLikes = Integer.parseInt(nLikesS);
		readerRL.close();
		
		BufferedWriter writerRL = new BufferedWriter(new FileWriter(receivedLikes));
		
		nLikes--;
		
		writerRL.write(Integer.toString(nLikes));
		writerRL.close();
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
	}
	
	//-----------------------------------------------banAccount------------------------------------------------
	// The method 'banAccount' will be called when an admin bans an account.
	// This method creates the text file with name "(username).txt" with the ban message and password as content.
	
	public static void banAccount(String u, String p, String m) throws IOException
	{
		// The file will go to the 'Banned' folder.
		String fileUserS = bannedS + u + ".txt";
		File fileUser = new File(fileUserS);
		fileUser.createNewFile();
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(fileUserS));
		writer1.write(m + "\n" + p);
		writer1.close();
	}
	
	//------------------------------------------------newTopic-------------------------------------------------
	// The method 'newTopic' will be called when an admin creates a new topic.
	// This method creates the default files of a new topic with the topic title, description and maximum number
	//   of posts permitted in the topic, that receives as arguments (String's and int).
	
	public static void newTopic(String topicName, String topicDescription, int maxPosts) throws IOException
	{
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(allTopics, true));
		writer1.append(topicName + "\n");
		writer1.close();
		
		String topicS = topicsS + topicName + "/";
		File topic = new File(topicS);
		topic.mkdir();
		
		File description = new File(topicS + "description.txt");
		description.createNewFile();
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(description));
		writer2.write(Integer.toString(maxPosts) + "\n" + topicDescription);
		writer2.close();
		
		File subs = new File(topicS + "subscriptions.txt");
		subs.createNewFile();
		
		File posts = new File(topicS + "Posts/");
		posts.mkdir();
	}
	
	//------------------------------------------------deleteTopic----------------------------------------------
	// The method 'deleteTopic' will be called when an admin deletes a topic.
	// It requires to delete all the posts and subscriptions associated with the topic.
	
	public static void deleteTopic(String topicName, FileFilter filterDir) throws Exception
	{
		String topicS = topicsS + topicName + "/";
		File topic = new File(topicS);
		
		// Deleting all posts in that topic.
		
		File posts = new File(topicS + "Posts/");
		File[] listOfPosts = posts.listFiles(filterDir);
		
		for(File post : listOfPosts)
			deletePost(topicName, post.getName());
		
		// Deleting all subscriptions associated with the topic.
		
		File subs = new File(topicS + "subscriptions.txt");
		BufferedReader readerSubs = new BufferedReader(new FileReader(subs));
		
		// Passing the lines of the 'subscriptions.txt' file to an Array List because it will be constantly modifying:
		//   we can't simply read each line and delete the subscription in a loop because the file will change.
		ArrayList<String> subsToDel = new ArrayList<String>();
		
		String line;
		
		while((line = readerSubs.readLine()) != null)
			subsToDel.add(line);
		
		readerSubs.close();
		
		for(String username : subsToDel)
			deleteSub(topicName, username);
		
		// Deleting the actual directory of the topic and updating the 'topics.txt' file.
		
		deleteFolder(posts);
		deleteFolder(topic);
		
		// 'temptopics.txt' will be the new 'topics.txt' file, now without the deleted topic.
		File tempTopics = new File (topicsS + "temptopics.txt");
		tempTopics.createNewFile();
		BufferedWriter writerT = new BufferedWriter(new FileWriter(tempTopics, true));
		
		BufferedReader readerT = new BufferedReader(new FileReader(allTopics));
		
		while((line = readerT.readLine()) != null)
			if(!line.equals(topicName))
				writerT.append(line + "\n");
		
		readerT.close();
		writerT.close();
		
		allTopics.delete();
		tempTopics.renameTo(allTopics);
	}
}
