package logged;

import java.io.File;
import java.io.IOException;
import util.FileSystem;
import util.Screen;

// This class is called when the admin wants to directly register a new account.

public class DirectRegistration
{
	private String username;
	private String password;
	// Type of account: admin or other.
	private String type;
	private String pathHome = System.getProperty("user.home");
	
	// The constructor of Registration object receives the username (String), the password (array of char's) and
	//   the type of account (admin or normal/other account).
	
	DirectRegistration(String u, char[] p, String t)
	{
		username = u;
		password = new String(p);
		type = t;
	}
	
	// The method 'checkRegistration' checks if the username already exists and if it doesn't, registers the new account.
	
	public boolean checkRegistration() throws IOException
	{	
		// Creating File objects, that the method needs to check if exist, in order to determine if the username is valid or not.
		
		String dirUserS = pathHome + "/SSociety_data/Users/AllUsers/" + username;
		File dirUser = new File(dirUserS + "/");
		
		String dirUserPendingAS = pathHome + "/SSociety_data/Users/PendingAdmins/" + username;
		File dirUserPendingA = new File(dirUserPendingAS + "/");
		
		String dirUserPendingOS = pathHome + "/SSociety_data/Users/PendingOthers/" + username;
		File dirUserPendingO = new File(dirUserPendingOS + "/");
		
		String dirUserBannedS = pathHome + "/SSociety_data/Users/Banned/" + username;
		File dirUserBanned = new File(dirUserBannedS + "/");
		
		// Because the 'exists' method isn't case sensitive, with those if statements, we guarantee that
		//   it is impossible to exist two users with the same username but different case characters.
		
		// Checks if the account is already registered and activated.
		if(dirUser.exists())
		{
			Screen.clear();
			System.out.println("That username is already in use!");
			System.out.println("Try to create with another username.");
			System.out.println("------------------------------------");
			System.out.println();
			return false;
		}
		
		// The account is not activated,
		//   so it checks if the account is sill waiting to be validated by an admin.
		else if(dirUserPendingA.exists() || dirUserPendingO.exists())
		{
			Screen.clear();
			System.out.println("This account was already registered and is waiting for admin's validation.");
			System.out.println("Try to create with another username.");
			System.out.println("--------------------------------------------------------------------------");
			System.out.println();
			return false;
		}
		
		// The account is neither registered nor activated,
		//   so it checks if the account was banned by an admin.
		else if(dirUserBanned.exists())
		{
			Screen.clear();
			System.out.println("An account with that same username was banned.");
			System.out.println("Try to create with another username.");
			System.out.println("----------------------------------------------");
			System.out.println();
			return false;
		}
		
		// The account is neither registered nor activated nor banned, which means that it doesn't exist.
		// The account will be created (calling 'FileSystem.newDirect') and the registration will succeed.
		else
		{
			FileSystem.newDirect(username, password, type);
			return true;
		}
	}
}
