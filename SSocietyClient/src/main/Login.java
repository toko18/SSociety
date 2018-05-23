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
		String pendingUsers = pathHome + "/SSociety_data/Users/PendingOthers/" + userName + ".txt"; //possible path to file in pending users folder
		String pendingAdmins = pathHome + "/SSociety_data/Users/PendingAdmins/" + userName + ".txt"; //possible path to file in pending admins folder
		String banned = pathHome + "/SSociety_data/Users/Banned/" + userName + ".txt"; //possible path to file in banned folder
		
		File checkPendingUsers = new File(pendingUsers); //file to check pending user
		File checkPendingAdmins = new File(pendingAdmins); //file to check pending admin
		File userDir = new File(userDirectory); //file to check existence of user directory
		File checkBannedUser = new File(banned); //file to check if user has been banned
		
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
			if(checkPendingUsers.exists() && checkPendingUsers.getCanonicalPath().equals(pendingUsers))
			{//if the user is waiting in pending users folder. waits for admin
				Screen.clear();
				System.out.println("This user is waiting admin's approval.");
				System.out.println("Please wait for the decision...");
			}
			else if(checkPendingAdmins.exists() && checkPendingAdmins.getCanonicalPath().equals(pendingAdmins))
			{//if admin is waiting in pending admins folder. waits for super admin
				Screen.clear();
				System.out.println("This admin is waiting another admin's approval");
				System.out.println("Please wait for the decision...");
			}
			else if(checkBannedUser.exists() && checkBannedUser.getCanonicalPath().equals(banned))
			{//if user has been banned
				Screen.clear();
				System.out.println("Your account has been banned by an admin.");
				
				BufferedReader readReason = new BufferedReader(new FileReader(banned)); //buffer that is going to read the reason form the file
				String reason = readReason.readLine();
				readReason.close();
				
				System.out.println("Reason: " + reason); //print s reason string
				System.out.println();
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
