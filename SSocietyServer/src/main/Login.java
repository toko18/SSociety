package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import util.Screen;

// This class is called when the user tries to log in.

public class Login
{
	private String username;
	private String password;
	private String pathHome = System.getProperty("user.home");
	
	// The constructor of Login object receives the username (String) and the password (array of char's).
	
	Login(String u, char[] p)
	{
		username = u;
		password = new String(p);
	}
	
	// The method 'checkLogin' checks if the username and the password are correct.
	
	public boolean checkLogin() throws IOException
	{	
		// Creating File objects, that the method needs to check if exist, in order to determine if the login is valid or not.
		
		String userDirectory = pathHome + "/SSociety_data/Users/AllUsers/" + username;
		File userDir = new File(userDirectory + "/");
		
		String userDirPending = pathHome + "/SSociety_data/Users/PendingAdmins/" + username;
		File userDirPend = new File(userDirPending + "/");
		
		String userDirPending2 = pathHome + "/SSociety_data/Users/PendingOthers/" + username;
		File userDirPend2 = new File(userDirPending2 + "/");
		
		String userDirBanned = pathHome + "/SSociety_data/Users/Banned/" + username;
		File userDirBan = new File(userDirBanned + "/");
		
		// Here we used the method 'getCanonicalPath' because the 'exists' method isn't case sensitive
		//   and so, without using the second condition of the statements, a user with username "admin"
		//   could log in with the username "AdMin", for example.
		// Even though it is impossible to exist two users with the same username but different case characters
		//   we decided to keep those conditions.
		
		// Checks if the account is activated.
		if (userDir.exists() && userDir.getCanonicalPath().equals(userDirectory))
		{
			File admins = new File(pathHome + "/SSociety_data/Users/admins.txt");
			BufferedReader getAdmins = new BufferedReader(new FileReader(admins));
			String adminName = null;
			
			// Only admins have access to the server.
			
			// This while loop will stop when it finds the username on the admins list
			//   (or the value of 'adminName' will be 'null' in case of not finding any admin with that username).
			while((adminName = getAdmins.readLine()) != null && !adminName.equals(username)) { }
			getAdmins.close();
			
			// That user is not an admin so he doesn't have permission to access the server (can only log in on client).
			if(adminName == null)
			{
				Screen.clear();
				System.out.println("You need to be an admin to have access to the server!");
				System.out.println("You should do your login on client.");
				System.out.println("-----------------------------------------------------");
				System.out.println();
				return false;
			}

			// At this point, the program knows that the account is a valid one and that it belongs to an admin
			//   so it checks if the password is correct.
			
			String userPassword = userDirectory + "/password.txt";
			BufferedReader getContent = new BufferedReader(new FileReader(userPassword));
			
			String toCheckPassword = getContent.readLine();
			
			// If the password is correct, the login will succeed.
			if(password.equals(toCheckPassword))
			{
				getContent.close();
				return true;
			}
			// Else, the password is wrong and it returns false (login failed).
			else
			{
				getContent.close();
				Screen.clear();
				System.out.println("Wrong password!");
				return false;
			}
		}
		
		// The account is not activated,
		//   so it checks if the account is sill waiting to be validated by an admin.
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
		
		// The account is neither registered nor activated,
		//   so it checks if the account was banned by an admin.
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
		
		// The account is neither registered nor activated nor banned, which means that it doesn't exist.
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
