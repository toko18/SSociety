package main;

import java.io.*;

public class Registration {
	private String userName;
	private String firstPassword;
	private String secondPassword;
	private String pathHome = System.getProperty("user.home");
	
	Registration(String u, String fPassword, String sPassword)
	{
		userName = u;
		firstPassword = fPassword;
		secondPassword = sPassword;
	}
	
	public int check() throws IOException
	{
		String allUsersDirectory = pathHome + "SSociety_data/Users/AllUsers/";
		String pendingDirectory = pathHome + "SSociety_data/Users/PendingUsers";
		
		File userDir = new File(allUsersDirectory + "/" + userName);
		File pendingDir = new File(pendingDirectory + "/" + userName);
		
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

		pendingDir.mkdir();
		File passwordFile = new File(pendingDirectory + "/" + userName);
		FileWriter addContent = new FileWriter(passwordFile);
		
		addContent.write(firstPassword);
		addContent.close();
		
		return 0;
	}

}
