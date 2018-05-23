package logged;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import util.FileSystem;
import util.Help;
import util.Screen;

//----------------------------------------------*AccountSettings*-----------------------------------------------
// This is the class that has the screens of the Account Settings's menu.

public class AccountSettings
{
	// A Console object ('cons') will be used to read the user input.
	private static Console cons = System.console();
	
	// Finds the path of user's computer home directory
	private static String pathHome = System.getProperty("user.home");
	
	//-----------------------------------------Change Password Screen-------------------------------------------
	// Change Password Screen - this is the screen that displays when the admin wants to change his account's password.
	// It returns true/false meaning that the user will log out or not after he tries to change the password.
	
	public static boolean passwordScreen(String loggedA) throws Exception
	{
		Screen.clear();
		
		if(loggedA.equals("admin"))
		{
			System.out.println("The super admin's password can't be changed!");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning false, the user won't be forced to log out.
			return false;
		}
		
		String accountS = pathHome + "/SSociety_data/Users/AllUsers/" + loggedA + "/";
		
		// Getting the current account's password.
		String userPassword = accountS + "password.txt";
		BufferedReader readerUP = new BufferedReader(new FileReader(userPassword));
		String curPassword = readerUP.readLine();
		readerUP.close();
		
		char[] curPasswordTest;
		char[] newPassword;
		char[] newPassword2;
		
		System.out.println("Change Password");
		System.out.println();
		
		// Reads the current password and checks if it is correct.
		curPasswordTest = cons.readPassword("Enter the current password: ");
		String curPasswordTestS = new String(curPasswordTest);
		
		if(!curPasswordTestS.equals(curPassword))
		{
			Screen.clear();
			System.out.println("Wrong password!");
			System.out.println("You will be logged out.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			// By returning true, the user will be forced to log out.
			return true;
		}
		
		// Reads the new password.
		newPassword = cons.readPassword("Choose a new password: ");
		String newPasswordS = new String(newPassword);
		
		// Reads the new password once again.
		newPassword2 = cons.readPassword("Enter chosen new password again: ");
		String newPassword2S = new String(newPassword2);
		
		// Checking if the new password is valid.
		
		if(!newPasswordS.equals(newPassword2S))
		{
			Screen.clear();
			System.out.println("Entered passwords don't match!");
			System.out.println("Try to change the password again.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning false, the user won't be forced to log out.
			return false;
		}

		if(newPasswordS.isEmpty())
		{
			Screen.clear();
			System.out.println("Null password!");
			System.out.println("The password has to be a sequence of characters.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning false, the user won't be forced to log out.
			return false;
		}
		
		if(newPasswordS.equals(curPassword))
		{
			Screen.clear();
			System.out.println("Password didn't change!");
			System.out.println("The new password is the same as the old one.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			Screen.clear();
			// By returning false, the user won't be forced to log out.
			return false;
		}
		else // The new password is valid.
		{
			BufferedWriter writerUP = new BufferedWriter(new FileWriter(userPassword));
			writerUP.write(newPasswordS);
			writerUP.close();
			
			Screen.clear();
			System.out.println("Password successfully changed!");
			System.out.println("You will be logged out.");
			System.out.println("---------------------------");
			cons.readPassword("Press Enter to continue...");
			// By returning true, the user will be forced to log out.
			return true;
		}
	}
	
	//-----------------------------------------Delete Account Screen-------------------------------------------
	// Delete Account Screen - this is the screen that displays when the admin wants to delete his account.
	// It returns true/false meaning that the user will log out or not (depending on if the account was successfully deleted or not).
		
	public static boolean deletionScreen(String loggedA) throws Exception
	{
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			System.out.println("Delete Account");
			System.out.println();
			System.out.println("Are you sure that you want to delete your account? All your data will be lost!");
			System.out.println();
			System.out.println("1 - Yes, I'm a pussy, delete my account forever!");
			System.out.println("2 - No, this was a mistake, I love SSociety!");
			System.out.println();
		
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1, the account will be deleted and the user will logout.
			if(chosenOption == 1)
			{
				// The super admin's account can't be deleted.
				if(loggedA.equals("admin"))
				{
					Screen.clear();
					System.out.println("Sometimes life is cruel, but don't go away, sir!");
					System.out.println("The super admin's account can't be deleted!");
					System.out.println("---------------------------");
					cons.readPassword("Press Enter to continue...");
					Screen.clear();
					return false;
				}
				
				// Deleting all the account data.
				FileSystem.deleteAccountData(loggedA);
				
				// As the account belongs to an admin, his username is deleted from the 'admins.txt' file.
				
				File admins = new File (pathHome + "/SSociety_data/Users/admins.txt");
				BufferedReader readerA = new BufferedReader(new FileReader(admins));
				
				// 'tempadmins.txt' will be the new 'admins.txt' file, now without the deleted account.
				File tempAdmins = new File (pathHome + "/SSociety_data/Users/tempadmins.txt");
				tempAdmins.createNewFile();
				BufferedWriter writerA = new BufferedWriter(new FileWriter(tempAdmins, true));
				
				String line;
				while((line = readerA.readLine()) != null)
					if(!line.equals(loggedA))
						writerA.append(line + "\n");
				
				readerA.close();
				writerA.close();
				
				admins.delete();
				tempAdmins.renameTo(admins);
				
				// Account successfully deleted.
				
				Screen.clear();
				System.out.println("Account successfully deleted!");
				System.out.println("It's sad to see you go :'(");
				System.out.println("---------------------------");
				cons.readPassword("Press Enter to continue...");
				return true;
			}
			
			// If the input is 2, it will go back to the Account Settings Screen.
			else if(chosenOption == 2)
			{
				Screen.clear();
				return false;
			}
			
			// Else, if the input wasn't 1 or 2, it is invalid (and it doesn't leave the while loop).
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
	
	//----------------------------------------------Help Screen------------------------------------------------
	// Help Screen - this is the screen that can be called in any other screen, when the user chooses the 'Help' option.
	// This screen prints instructions on how to interact with the screen where it is called.
	
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