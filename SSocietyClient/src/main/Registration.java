package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//----------------------------------------------Registration class----------------------------
public class Registration {
	private String username; //variable that saves the username received by this class
	private String firstPassword; //variable that saves the first password received by this class
	private String secondPassword; //variable that saves the second password received by this class
	private String pathHome = System.getProperty("user.home"); //variable that saves the path to user homefolder
	
	Registration(String u, char[] fPassword, char[] sPassword)
	{//constructor that initializes the variables above
		username = u;
		firstPassword = new String(fPassword);
		secondPassword = new String(sPassword);
	}
	
	public int checkRegistration() throws IOException
	{
		String allUsersDirectory = pathHome + "/SSociety_data/Users/AllUsers/"; //folder that has folder dedicated to all users
		String pending = pathHome + "/SSociety_data/Users/PendingOthers/"; //folder that has folders of all users§
		
		File userDir = new File(allUsersDirectory + username); //
		File pendingFile = new File(pending + username + ".txt");
		
		
		if(userDir.exists() && userDir.isDirectory() && !username.isEmpty())
		{
			return 1;
		}
		else if (pendingFile.exists() && pendingFile.isFile())
		{
			return 3;
		}
		
		if (!firstPassword.equals(secondPassword)) {
			return 2;
		}
		
		for(int i = 0; i < username.length(); i++)
		{
			if(!(Character.isDigit(username.charAt(i)) || Character.isLetter(username.charAt(i))))
			{
				return 4;
			}
		}
		
		if(username.isEmpty())
		{
			return 5;
		}
		
		
		if(firstPassword.equals("") && secondPassword.equals(""))
		{
			return 6;
		}
		
		pendingFile.createNewFile();
		FileWriter write = new FileWriter(pendingFile);
		BufferedWriter addContent = new BufferedWriter(write);
		
		addContent.write(firstPassword);
		addContent.close();
		
		return 0;
	}
}
