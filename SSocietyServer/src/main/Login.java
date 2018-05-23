package main;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import util.Screen;

//-------------------------------------------------*Login*-----------------------------------------------------
// This class is called when the user tries to log in.

public class Login
{
	// A Console object ('cons') will be used to read the user input.
	// In this class, we only use it to wait for an interrupt (Enter key press).
	private static Console cons = System.console();
	
	private String username;
	private String password;
	private String pathHome = System.getProperty("user.home");
	
	// The constructor of Login object receives the username (String) and the password (array of char's).
	
	Login(String u, char[] p)
	{
		username = u;
		password = new String(p);
	}
	
	//----------------------------------------------checkLogin-------------------------------------------------
	// The method 'checkLogin' checks if the username and the password are correct.
	
	public boolean checkLogin() throws IOException
	{	
		// Creating File objects, that the method needs to check if they exist, in order to determine if the login is valid or not.
		
		String dirUserS = pathHome + "/SSociety_data/Users/AllUsers/" + username;
		File dirUser = new File(dirUserS + "/");
		
		String fileUserPendingAS = pathHome + "/SSociety_data/Users/PendingAdmins/" + username + ".txt";
		File fileUserPendingA = new File(fileUserPendingAS);
		
		String fileUserPendingOS = pathHome + "/SSociety_data/Users/PendingOthers/" + username + ".txt";
		File fileUserPendingO = new File(fileUserPendingOS);
		
		String fileUserBannedS = pathHome + "/SSociety_data/Users/Banned/" + username + ".txt";
		File fileUserBanned = new File(fileUserBannedS);
		
		// Here we used the method 'getCanonicalPath' because the 'exists' method isn't case sensitive
		//   and so, without using the second condition of the statements, a user with username "admin"
		//   could log in with the username "AdMin", for example.
		// Even though it is impossible to exist two users with the same username but different case characters
		//   we decided to keep those conditions.
		
		// Checks if the account is activated.
		if (dirUser.exists() && dirUser.getCanonicalPath().equals(dirUserS))
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
				System.out.println("You should do your login on Client.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}

			// At this point, the program knows that the account is a valid one and that it belongs to an admin
			//   so it checks if the password is correct.
			
			String userPassword = dirUserS + "/password.txt";
			BufferedReader reader = new BufferedReader(new FileReader(userPassword));
			
			String toCheckPassword = reader.readLine();
			
			reader.close();
			
			// If the password is correct, the login will succeed.
			if(password.equals(toCheckPassword))
				return true;
			
			// Else, the password is wrong and it returns false (login failed).
			else
			{
				Screen.clear();
				System.out.println("Wrong password!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}
		}
		
		// The account is not activated,
		//   so it checks if the account is sill waiting to be validated by an admin.
		else if((fileUserPendingA.exists() && fileUserPendingA.getCanonicalPath().equals(fileUserPendingAS)) || (fileUserPendingO.exists() && fileUserPendingO.getCanonicalPath().equals(fileUserPendingOS)))
		{
			Screen.clear();
			System.out.println("Your account is not activated yet! :(");
			System.out.println("Please wait until an admin validate your registration.");
			System.out.println("Sorry for any inconvenience.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
		
		// The account is neither registered nor activated,
		//   so it checks if the account was banned by an admin.
		else if(fileUserBanned.exists() && fileUserBanned.getCanonicalPath().equals(fileUserBannedS))
		{
			Screen.clear();
			System.out.println("Your account was banned by an admin! :(");
			System.out.println();
			
			BufferedReader reader = new BufferedReader(new FileReader(fileUserBanned));
			System.out.println("Ban message: " + reader.readLine());
			reader.close();
			
			System.out.println();
			System.out.println("You won't be able to join SSociety unless an admin unbans your account.");
			System.out.println("Sorry for any inconvenience.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
		
		// The account is neither registered nor activated nor banned, which means that it doesn't exist.
		else
		{
			Screen.clear();
			System.out.println("This account doesn't exist!");
			System.out.println("Please make sure you are entering the correct username.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
	}
}
