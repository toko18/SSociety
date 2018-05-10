package logged;

import java.io.Console;
import java.io.IOException;

import util.Help;
import util.Screen;

// This is the class that has the screens of the Account Management's menu.

public class AccountsManagement
{
	// A Console object ('cons') will be used to read the user input.
	private static Console cons = System.console();
	
	// Creation Screen - this is the screen that displays when the admin wants to directly create accounts.
	// Accounts created here are automatically activated.
	// Here, the admin can choose between 5 options that will display their respective screen.
	
	public static void creationScreen()
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			System.out.println("Accounts Creation");
			System.out.println();
			System.out.println("1 - Create admin account");
			System.out.println("2 - Create normal user account");
			System.out.println("3 - Help");
			System.out.println("4 - Back");
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1 or 2, the program will be waiting for username and password inputs.
			if(chosenOption == 1 || chosenOption == 2)
			{
				Screen.clear();
				
				String username;
				char[] password;
				// Boolean value of registration success/fail.
				boolean success = false;
				
				// Reads the username.
				username = cons.readLine("Username: ");
				// Reads the password.
				password = cons.readPassword("Password: ");
				
				// Creates a Registration object.
				String type;
				if(chosenOption == 1)
					type = "admin";
				else
					type = "other";
					
				DirectRegistration userRegistration = new DirectRegistration(username, password, type);
				
				// Calls 'checkRegistration' and returns true/false, meaning if the registration has succeeded or not.
				try { success = userRegistration.checkRegistration(); }
				catch(IOException e) { }
				
				// In case of registration success, it goes back to the previous screen (Accounts Management Screen).
				if(success)
				{
					Screen.clear();
					System.out.println("Account successfully created!");
					System.out.println("-----------------------------");
					System.out.println();
					// By returning, it will go back to the Accounts Management Screen.
					return;
				}
			}
			
			// If the input is 3, it opens the help screen corresponding to the current one (Creation Screen).
			else if(chosenOption == 3)
				helpScreen("Home.AccountsManagement.creationScreen");
			
			// If the input is 4, it leaves this screen and goes back to the previous screen (Accounts Management Screen).
			else if(chosenOption == 4)
			{
				Screen.clear();
				// By returning, it will go back to the Accounts Management Screen.
				return;
			}
			
			// Else, if the input wasn't 1, 2 or 3, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("--------------");
				System.out.println();
			}
		}
	}
	
	public static void pendingScreen()
	{
		
	}
	
	public static void banishmentScreen()
	{
		
	}
	
	public static void helpScreen(String current)
	{
		Screen.clear();
		System.out.println("Help");
		System.out.println();
		// Calls 'screen' method of 'Help' class for the current screen, that will display instructions.
		Help.screen(current);
		// In order to leave the help screen, the user has to press Enter.
		// For that, we use the method readPassword and that way nothing that the user tries to write will appear in the screen.
		cons.readPassword("Press Enter to leave the help screen and return to the previous menu.");
		Screen.clear();
	}
}
