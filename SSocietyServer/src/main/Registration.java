package main;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import util.FileSystem;
import util.Screen;

//This class is called when the user tries to register.

public class Registration
{
	// A Console object ('cons') will be used to read the user input.
	// In this class, we only use it to wait for an interrupt (Enter key press).
	private static Console cons = System.console();
	
	private String username;
	private String password;
	private String password2;
	private String pathHome = System.getProperty("user.home");
	
	// The constructor of Registration object receives the username (String) and the password (array of char's).
	
	Registration(String u, char[] p, char[] p2)
	{
		username = u;
		password = new String(p);
		password2 = new String(p2);
	}
	
	// The method 'checkRegistration' checks if the username already exists and if it doesn't, registers the new account.
	
	public boolean checkRegistration() throws IOException
	{	
		// Creating File objects, that the method needs to check if they exist, in order to determine if the username is valid or not.
		
		String dirUserS = pathHome + "/SSociety_data/Users/AllUsers/" + username;
		File dirUser = new File(dirUserS + "/");
		
		String fileUserPendingAS = pathHome + "/SSociety_data/Users/PendingAdmins/" + username + ".txt";
		File fileUserPendingA = new File(fileUserPendingAS);
		
		String fileUserPendingOS = pathHome + "/SSociety_data/Users/PendingOthers/" + username + ".txt";
		File fileUserPendingO = new File(fileUserPendingOS);
		
		String fileUserBannedS = pathHome + "/SSociety_data/Users/Banned/" + username + ".txt";
		File fileUserBanned = new File(fileUserBannedS);
		
		// Because the 'exists' method isn't case sensitive, with those if statements, we guarantee that
		//   it is impossible to exist two users with the same username but different case characters.
		
		// Checks if the account is already registered and activated.
		if(dirUser.exists() && !username.isEmpty())
		{
			Screen.clear();
			System.out.println("That username is already in use!");
			System.out.println("Try to register using another username.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
		
		// The account is not activated,
		//   so it checks if the account is sill waiting to be validated by an admin.
		else if(fileUserPendingA.exists() || fileUserPendingO.exists())
		{
			Screen.clear();
			System.out.println("Your account is not activated yet! :(");
			System.out.println("It looks like you have already registered your account.");
			System.out.println("Please wait until an admin validate your registration.");
			System.out.println("Sorry for any inconvenience.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
		
		// The account is neither registered nor activated,
		//   so it checks if the account was banned by an admin.
		else if(fileUserBanned.exists())
		{
			Screen.clear();
			System.out.println("Oops! :(");
			System.out.println("It looks like an account with that same username was banned.");
			System.out.println("Try to register using another username.");
			System.out.println("Sorry for any inconvenience.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
		
		// The account is neither registered nor activated nor banned, which means that it doesn't exist already,
		//   so it checks if the entered username is valid and entered passwords match.
		// If everything is ok, the account will be registered (by calling 'FileSystem.registAdmin') and the registration will succeed.
		else
		{
			// Checking if the entered username is valid (if it doesn't have special characters and isn't null).
			
			if(username.isEmpty())
			{
				Screen.clear();
				System.out.println("Null username!");
				System.out.println("Try to register using a sequence of characters as username.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}
			
			for(int i = 0; i < username.length(); i++)
			{
				if(username.charAt(i) == '|' || username.charAt(i) == ':')
				{
					Screen.clear();
					System.out.println("Your username cannot contain the special characters '|' and ':'.");
					System.out.println("Try to register using another username.");
					System.out.println("---------------------------");
					cons.readPassword("Press Enter to continue...");
					Screen.clear();
					return false;
				}
			}
			
			// Checking if the entered passwords match and aren't null.
			
			if(!password.equals(password2))
			{
				Screen.clear();
				System.out.println("Entered passwords don't match!");
				System.out.println("Try to register again.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}
			
			if(password.isEmpty())
			{
				Screen.clear();
				System.out.println("Null password!");
				System.out.println("Try to register using a sequence of characters as password.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}
			
			// The username is available, valid and the entered passwords match. The registration succeeds.
			FileSystem.registAdmin(username, password);
			return true;
		}
	}
}
