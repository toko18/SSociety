package logged;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import util.FileSystem;
import util.Help;
import util.Screen;

// This is the class that has the screens of the Account Management's menu.

public class AccountsManagement
{
	// A Console object ('cons') will be used to read the user input.
	private static Console cons = System.console();
	
	// Finds the path of user's computer home directory
	private static String pathHome = System.getProperty("user.home");
	
	// This FileFilter will be used to filter files that have ".txt" suffix.
	private static FilenameFilter filter = new FilenameFilter()
	{
        public boolean accept(File file, String name)
        {
           return name.endsWith(".txt");
        }
     };
	
	// Creation Screen - this is the screen that displays when the admin wants to directly create accounts.
	// Accounts created here are automatically activated.
	// Here, the admin can choose between 4 options that will display their respective screen.
	
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
			System.out.println();
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1 or 2, the program will be waiting for username and password inputs.
			if(chosenOption == 1 || chosenOption == 2)
			{
				Screen.clear();
				
				String username;
				char[] password;
				char[] password2;
				// Boolean value of registration success/fail.
				boolean success = false;
				
				// Reads the username.
				username = cons.readLine("Username: ");
				// Reads the password.
				password = cons.readPassword("Password: ");
				// Reads the password once again.
				password2 = cons.readPassword("Enter chosen password again: ");
				
				String type;
				if(chosenOption == 1)
					type = "admins";
				else
					type = "others";
					
				// Creates a DirectRegistration object.
				DirectRegistration userRegistration = new DirectRegistration(username, password, password2, type);
				
				// Calls 'checkRegistration' and returns true/false, meaning if the registration has succeeded or not.
				try { success = userRegistration.checkRegistration(); }
				catch(IOException e) { }
				
				// In case of registration success, it goes back to the previous screen (Accounts Management Screen).
				if(success)
				{
					Screen.clear();
					System.out.println("Account successfully created!");
					System.out.println("---------------------------");
					cons.readPassword("Press Enter to continue...");
					Screen.clear();
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
			
			// Else, if the input wasn't 1, 2, 3 or 4, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
			}
		}
	}
	
	// Pending Screen - this is the screen that displays when the admin wants to see the pending accounts (and accept/reject them).
	// Here, the admin can choose between 4 options that will display their respective screen.
	
	public static void pendingScreen()
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			System.out.println("Pending accounts validation");
			System.out.println();
			System.out.println("1 - Validate admin accounts");
			System.out.println("2 - Validate normal user accounts");
			System.out.println("3 - Help");
			System.out.println("4 - Back");
			System.out.println();
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1, it opens the Pending Users Screen (that will display admin accounts).
			if(chosenOption == 1)
				pendingUScreen("admins");
			
			// If the input is 2, it opens the Pending Users Screen (that will display normal user accounts).
			else if(chosenOption == 2)
				pendingUScreen("others");
			
			// If the input is 3, it opens the help screen corresponding to the current one (Pending Screen).
			else if(chosenOption == 3)
				helpScreen("Home.AccountsManagement.pendingScreen");
			
			// If the input is 4, it leaves this screen and goes back to the previous screen (Accounts Management Screen).
			else if(chosenOption == 4)
			{
				Screen.clear();
				// By returning, it will go back to the Accounts Management Screen.
				return;
			}
			
			// Else, if the input wasn't 1, 2, 3 or 4, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
			}
		}
	}
	
	// Pending Users Screen - this is the screen that displays when the admin wants to see the pending user accounts (and accept/reject them).
	// Depending on the String argument that the method receives, it will either display admin or normal user accounts.
	// Here, the admin can choose the user that he wants to accept/reject and then do the pretended action.
	
	public static void pendingUScreen(String type)
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		String pendingS;
		File pending;
		File[] listOfUsers;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			// If the argument is "admins", it will display admin accounts.
			if(type.equals("admins"))
			{
				// Creating an array of files (folders that correspond to the pending admin accounts) from 'PendingAdmins' folder.
				pendingS = pathHome + "/SSociety_data/Users/PendingAdmins/";
				pending = new File(pendingS);
				// This array will only contain files with ".txt" suffix, thanks to the FilenameFilter 'filter'.
				listOfUsers = pending.listFiles(filter);
			}
			
			// Else, if the argument is "others", it will display normal user accounts.
			else
			{
				// Creating an array of files (folders that correspond to the pending normal user accounts) from 'PendingOthers' folder.
				pendingS = pathHome + "/SSociety_data/Users/PendingOthers/";
				pending = new File(pendingS);
				// This array will only contain files with ".txt" suffix, thanks to the FilenameFilter 'filter'.
				listOfUsers = pending.listFiles(filter);
			}
			
			// If there isn't any pending account, a message appears and it goes back to the previous screen (Pending Screen).
			if(listOfUsers.length == 0)
			{
				// The message depends on the type of user (admins/others).
				if(type.equals("admins"))
				{
					System.out.println("Pending Admins");
					System.out.println();
					System.out.println("There are no pending admin accounts!");
				}
				else //"others"
				{
					System.out.println("Pending Others");
					System.out.println();
					System.out.println("There are no pending normal user accounts!");
				}
				
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
				// By returning, it will go back to the Pending Screen.
				return;
			}
			
			// Displays the list of pending accounts.
			
			// The title depends on the type of user (admins/others).
			if(type.equals("admins"))
				System.out.println("Pending Admins");
			else //"others"
				System.out.println("Pending Others");
			
			System.out.println();
			System.out.println("------------------------------------------------");
			for(int i = 1; i <= listOfUsers.length; i++)
			{
				String fileName = listOfUsers[i-1].getName();
				// 'substring' called, in order not to print the ".txt".
				System.out.println(i + " - " + fileName.substring(0, fileName.length()-4));
			}
			System.out.println("------------------------------------------------");
			System.out.println();
			System.out.printf("%d - Help\n", listOfUsers.length+1);
			System.out.printf("%d - Back\n", listOfUsers.length+2);
			System.out.println();
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input corresponds to an account option number, it opens a "minibuffer" where the admin
			//   can choose to validate the account, delete it or close the "minibuffer".
			if(chosenOption > 0 && chosenOption <= listOfUsers.length)
			{
				Screen.clear();
				
				int option = 0;
				
				File account = listOfUsers[chosenOption-1];
				
				String fileName = account.getName();
				String username = fileName.substring(0, fileName.length()-4);
				
				while(true)
				{
					// 'substring' called, in order not to print the ".txt".
					System.out.println("Account's username: " + username);
					System.out.println();
					System.out.println("1 - Validate the account");
					System.out.println("2 - Delete the account");
					System.out.println("3 - Back");
					System.out.println();
					
					try { option = Integer.parseInt(cons.readLine("Insert option number: ")); }
					catch(Exception e) { option = 0; }
					
					// If the input is 1, the default files of the new user will be created (by calling FileSystem's
					//   'newAccount' method.
					if(option == 1)
					{
						// Reading the choosen password.
						String password = null;
						try
						{
							BufferedReader reader = new BufferedReader(new FileReader(account));
							password = reader.readLine();
							reader.close();
						}
						catch(IOException e) { }
						
						// Creating the account.
						try { FileSystem.newAccount(username, password, type); }
						catch(IOException e) { }
						
						// Account not pending anymore, so the file 'account' is deleted.
						account.delete();
						
						Screen.clear();
						System.out.println("Account successfully validated!");
						System.out.println("---------------------------");
						cons.readPassword("Press Enter to continue...");
						Screen.clear();
						// By breaking, it will go back to the Pending Users Screen.
						break;
					}
					
					// If the input is 2, the account is deleted (by deleting the respective "pending file").
					else if(option == 2)
					{
						account.delete();
						
						Screen.clear();
						System.out.println("Account successfully deleted!");
						System.out.println("---------------------------");
						cons.readPassword("Press Enter to continue...");
						Screen.clear();
						// By breaking, it will go back to the Pending Users Screen.
						break;
					}
					
					/// If the input is 3, it closes this minibuffer and goes back to the previous screen (Pending Users Screen).
					else if(option == 3)
					{
						Screen.clear();
						// By breaking, it will close the minibuffer and go back to the Pending Users Screen.
						break;
					}
					
					// Else, if the input wasn't 1, 2 or 3, it is invalid (and it doesn't leave the while loop).
					else
					{
						Screen.clear();
						System.out.println("Invalid input!");
						System.out.println("---------------------------");
						cons.readPassword("Press Enter to continue...");
						Screen.clear();
					}
				}
			}
			
			// If the input corresponds to "Help" option number,
			//   it opens the help screen corresponding to the current one (Pending Users Screen).
			else if(chosenOption == listOfUsers.length+1)
				helpScreen("Home.AccountsManagement.pendingUScreen");
			
			// If the input corresponds to "Back" option number,
			//   it leaves this screen and goes back to the previous screen (Pending Screen).
			else if(chosenOption == listOfUsers.length+2)
			{
				Screen.clear();
				// By returning, it will go back to the Pending Screen.
				return;
			}
			
			// Else, if the input didn't correspond to any option number, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				Screen.clear();
			}
		}
	}
	
	public static void banishmentScreen(String loggedA)
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
