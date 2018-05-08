package main;

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
		String pendingDirectory = pathHome + "/SSociety_data/Users/PendingOthers/";
		
		File userDir = new File(allUsersDirectory + username);
		File pendingDir = new File(pendingDirectory + username);
		
		if(userDir.exists() && userDir.isDirectory())
		{
			return 1;
		}
		else if (pendingDir.exists() && pendingDir.isDirectory())
		{
			return 3;
		}
		
		if (!firstPassword.equals(secondPassword)) {
			return 2;
		}
		
		for (int i = 0; i < username.length(); i++)
		{
			if(username.charAt(i) == '|')
			{
				return 4;
			}
		}
		
		pendingDir.mkdir();
		File passwordFile = new File(pendingDirectory + username + "/password.txt");
		FileWriter addContent = new FileWriter(passwordFile);
		
		addContent.write(firstPassword);
		addContent.close();
		
		return 0;
	}
}
