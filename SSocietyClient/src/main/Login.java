package main;

import java.io.*;
import util.Screen;

public class Login {
	private String userName; //variable that saves the username entered by the user
	private String password; //variable that saves password entered by the user 
	private String pathHome = System.getProperty("user.home"); //variable that saves the path to user homefolder
	
	Login (String u, char[] p)
	{//constructor that initializes the the variables above
		userName = u;
		password = new String(p);
	}
	
	public boolean checkLogin() throws IOException
	{	
		String userDirectory = pathHome + "/SSociety_data/Users/AllUsers/" + userName; //possible path to user folder
		String userPassword = pathHome + "/SSociety_data/Users/AllUsers/" + userName + "/password.txt"; //possible path to user password
		String pendingFile = pathHome + "/SSociety_data/Users/PendingOthers/" + userName + ".txt"; //possible path to file in pending users folder
		
		File checkPending = new File(pendingFile); //file to check pending
		File userDir = new File(userDirectory); //file to check existence of user directory
		
		
		if (userDir.exists() && userDir.getCanonicalPath().equals(userDirectory)) 
		{//check if user directory exists in accepted users folder
			BufferedReader getContent = new BufferedReader(new FileReader(userPassword));
			String toCheckPassword = getContent.readLine(); //gets password from password folder
			getContent.close();
			
			if(password.equals(toCheckPassword)) 
			{//if password is right login is successful
				return true;
			}
			else 
			{	//what happens when the password is wrong but username is correct
				Screen.clear();
				System.out.println("Wrong password! Try again");
				return false;
			}
		}
		else 
		{	
			if(checkPending.exists() && checkPending.getCanonicalPath().equals(pendingFile))
			{//if the user is waiting in pendings folder. waits for admin
				Screen.clear();
				System.out.println("This user is waiting admin's approval.");
				System.out.println("Please wait for the decision...");
			}
			else
			{ //when the user is not registered
				Screen.clear();
				System.out.println("User not registered!");
				System.out.println("Go back and do your registration...");
			}
			return false;
		}		
	}
}
