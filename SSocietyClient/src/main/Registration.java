package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Registration {
	private String username;
	private String firstPassword;
	private String secondPassword;
	private String pathHome = System.getProperty("user.home");
	
	Registration(String u, char[] fPassword, char[] sPassword)
	{
		username = u;
		firstPassword = new String(fPassword);
		secondPassword = new String(sPassword);
	}
	
	public int checkRegistration() throws IOException
	{
		String allUsersDirectory = pathHome + "/SSociety_data/Users/AllUsers/";
		String pending = pathHome + "/SSociety_data/Users/PendingOthers/";
		
		File userDir = new File(allUsersDirectory + username);
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
