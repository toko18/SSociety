package main;

import java.io.*;

import util.Screen;

public class Login {
	private String userName;
	private String password;
	private String pathHome = System.getProperty("user.home");
	
	Login (String u, char[] p)
	{
		userName = u;
		password = new String(p);
	}
	
	public boolean checkLogin() throws IOException
	{	
		String userDirectory = pathHome + "/SSociety_data/Users/AllUsers/" + userName;
		String userPassword = pathHome + "/SSociety_data/Users/AllUsers/" + userName + "/password.txt";
		String pendingFile = pathHome + "/SSociety_data/Users/PendingOthers/" + userName + ".txt";
		
		File checkPending = new File(pendingFile);
		File userDir = new File(userDirectory);
		
		
		if (userDir.exists()) 
		{
			BufferedReader getContent = new BufferedReader(new FileReader(userPassword));
			String toCheckPassword = getContent.readLine();
			getContent.close();
			
			if(password.equals(toCheckPassword)) 
			{
				return true;
			}
			else 
			{	
				Screen.clear();
				System.out.println("Wrong password! Try again");
				return false;
			}
		}
		else 
		{	
			if(checkPending.exists())
			{
				Screen.clear();
				System.out.println("This user is waiting admin's approval.");
				System.out.println("Please wait for the decision...");
			}
			else
			{
				Screen.clear();
				System.out.println("User not registered!");
				System.out.println("Go back and do your registration...");
			}
			return false;
		}		
	}
}
