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
		
		File userDir = new File(allUsersDirectory + username); //folder of user in accepted users folder... checks if user is registered
		File pendingFile = new File(pending + username + ".txt"); //file that has the password in the pending folder
		
		
		if(userDir.exists() && userDir.isDirectory() && !username.isEmpty())
		{//if the user directory exists in accepted users - the user is already registered
			return 1;
		}
		else if (pendingFile.exists() && pendingFile.isFile())
		{//user is waiting admins decision 
			return 3;
		}
		
		if (!firstPassword.equals(secondPassword)) {
			//passwords are not equal so registration is not concluded
			return 2;
		}
		
		for(int i = 0; i < username.length(); i++)
		{//checks if the username contains only numbers and letters
			if(!(Character.isDigit(username.charAt(i)) || Character.isLetter(username.charAt(i))))
			{
				return 4;
			}
		}
		
		if(username.isEmpty())
		{//checks if username is empty
			return 5;
		}
		
		
		if(firstPassword.equals("") && secondPassword.equals(""))
		{//check if passwords are not empty
			return 6;
		}
		//-----------------creates pending file with password written on it------------
		pendingFile.createNewFile();
		FileWriter write = new FileWriter(pendingFile);
		BufferedWriter addContent = new BufferedWriter(write);
		
		addContent.write(firstPassword); //add password to file
		addContent.close(); 
		
		return 0;
	}
}
