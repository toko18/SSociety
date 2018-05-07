package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import util.Screen;

public class Login
{
	private String username;
	private String password;
	private String pathHome = System.getProperty("user.home");
	
	Login(String u, char[] p)
	{
		username = u;
		password = new String(p);
	}
	
	public boolean checkLogin() throws IOException
	{	
		String userDirectory = pathHome + "/SSociety_data/Users/AllUsers/" + username;
		File userDir = new File(userDirectory + "/");
		String userPassword = userDirectory + "/password.txt";
		
		String userDirPending = pathHome + "/SSociety_data/Users/PendingAdmins/" + username;
		File userDirPend = new File(userDirPending + "/");
		
		String userDirPending2 = pathHome + "/SSociety_data/Users/PendingOthers/" + username;
		File userDirPend2 = new File(userDirPending2 + "/");
		
		String userDirBanned = pathHome + "/SSociety_data/Users/Banned/" + username;
		File userDirBan = new File(userDirBanned + "/");
		
		if (userDir.exists() && userDir.getCanonicalPath().equals(userDirectory))
		{
			File admins = new File(pathHome + "/SSociety_data/Users/admins.txt");
			BufferedReader getAdmins = new BufferedReader(new FileReader(admins));
			String adminName = null;
			
			while((adminName = getAdmins.readLine()) != null && !adminName.equals(username)) { }
			getAdmins.close();
			
			if(adminName == null)
			{
				Screen.clear();
				System.out.println("You need to be an admin to have access to the server!");
				System.out.println("You should do your login on client.");
				System.out.println("-----------------------------------------------------");
				System.out.println();
				return false;
			}
			
			BufferedReader getContent = new BufferedReader(new FileReader(userPassword));
			
			String toCheckPassword = getContent.readLine();
			
			if(password.equals(toCheckPassword))
			{
				getContent.close();
				return true;
			}
			else
			{
				getContent.close();
				Screen.clear();
				System.out.println("Wrong password!");
				return false;
			}
		}
		else if((userDirPend.exists() && userDirPend.getCanonicalPath().equals(userDirPending)) || (userDirPend2.exists() && userDirPend2.getCanonicalPath().equals(userDirPending2)))
		{
			Screen.clear();
			System.out.println("Your account is not activated yet! :(");
			System.out.println("Please wait until an admin validate your registration.");
			System.out.println("Sorry for any inconvenience.");
			System.out.println("------------------------------------------------------");
			System.out.println();
			return false;
		}
		else if((userDirBan.exists() && userDirBan.getCanonicalPath().equals(userDirBanned)))
		{
			Screen.clear();
			System.out.println("Your account was banned by an admin! :(");
			System.out.println("You won't be able to join SSociety again.");
			System.out.println("Sorry for any inconvenience.");
			System.out.println("-----------------------------------------");
			System.out.println();
			return false;
		}
		else
		{
			Screen.clear();
			System.out.println("This account doesn't exist!");
			System.out.println("Please make sure you are entering the correct username.");
			System.out.println("-------------------------------------------------------");
			System.out.println();
			return false;
		}
	}
}
