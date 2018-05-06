package main;

import java.io.*;

public class Login {
	private String userName;
	private String password;
	private String pathHome = System.getProperty("user.home");
	
	Login (String u, String p)
	{
		userName = u;
		password = p;
	}
	
	public boolean checkLogin() throws IOException
	{	
		String userDirectory = pathHome + "/" + "SSociety_data/Users/AllUsers/" + userName;
		String userPassword = pathHome + "/" + "SSociety_data/Users/AllUsers/" + userName + "/password.txt";
		
		File userDir = new File(userDirectory);
		BufferedReader getContent = new BufferedReader(new FileReader(userPassword));
		
		if (userDir.exists()) 
		{
			String toCheckPassword = getContent.readLine();
			if(password.equals(toCheckPassword)) 
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
		else 
		{
			return false;
		}		
	}
}
