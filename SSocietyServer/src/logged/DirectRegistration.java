package logged;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import util.FileSystem;
import util.Screen;

//--------------------------------------------*DirectRegistration*---------------------------------------------
// This class is called when the admin wants to directly create a new account.

public class DirectRegistration
{
	// A Console object ('cons') will be used to read the user input.
	// In this class, we only use it to wait for an interrupt (Enter key press).
	private static Console cons = System.console();
	
	private String username;
	private String password;
	private String password2;
	// Type of account: admins or others.
	private String type;
	private String pathHome = System.getProperty("user.home");
	
	// The constructor of Registration object receives the username (String), the password (array of char's) and
	//   the type of account (admin or normal/other account).
	
	DirectRegistration(String u, char[] p, char[] p2, String t)
	{
		username = u;
		password = new String(p);
		password2 = new String(p2);
		type = t;
	}
	
	//----------------------------------------------checkRegistration----------------------------------------------
	// The method 'checkRegistration' checks if the username already exists and if it doesn't, creates the new account.
	
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
			System.out.println("Try to create it using another username.");
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
			System.out.println("This account was already registered and is waiting for admin's validation.");
			System.out.println("Try to create it using another username.");
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
			System.out.println("An account with that same username was banned.");
			System.out.println("Try to create it using another username.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			return false;
		}
		
		// The account is neither registered nor activated nor banned, which means that it doesn't exist already,
		//   so it checks if the entered username is valid and entered passwords match.
		// If everything is ok, the account will be created (by calling 'FileSystem.newAccount').
		else
		{
			// Checking if the entered username is valid (if it only has letters and digits and isn't null).
			
			if(username.isEmpty())
			{
				Screen.clear();
				System.out.println("Null username!");
				System.out.println("Try to create it using a sequence of letters and digits as username.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}
			
			for(int i = 0; i < username.length(); i++)
			{
				if(!Character.isLetter(username.charAt(i)) && !Character.isDigit(username.charAt(i)))
				{
					Screen.clear();
					System.out.println("Usernames can only contain letters and digits.");
					System.out.println("Try to create it using another username.");
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
				System.out.println("Try to create the account again.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}

			if(password.isEmpty())
			{
				Screen.clear();
				System.out.println("Null password!");
				System.out.println("Try to create it using a sequence of characters as password.");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				return false;
			}
			
			// The username is available, valid and the entered passwords match. The creation succeeds.
			FileSystem.newAccount(username, password, type);
			return true;
		}
	}
}
