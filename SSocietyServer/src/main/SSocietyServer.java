package main;

import java.io.Console;
import java.io.IOException;
import util.FileSystem;
import util.Help;
import util.Screen;

public class SSocietyServer
{	
	// A Console object ('cons') will be used to read the user input.
	private static Console cons = System.console();
	
	// First screen - this is the initial "screen".
	// Here, the user can choose between 4 options that will display their respective screen.
	
	public static void firstScreen()
	{	
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			System.out.println("Welcome to SSociety!");
			System.out.println();
			System.out.println("1 - Login");
			System.out.println("2 - Register");
			System.out.println("3 - Help");
			System.out.println("4 - Exit");
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1, it leaves this screen (leaving the loop) and opens the login screen.
			if(chosenOption == 1)
			{
				loginScreen();
				break;
			}
			
			// If the input is 2, it leaves this screen (leaving the loop) and opens the registration screen.
			else if(chosenOption == 2)
			{
				registrationScreen();
				break;
			}
			
			// If the input is 3, it opens the help screen corresponding to the current one (first screen).
			else if(chosenOption == 3)
				helpScreen("firstScreen");
			
			// If the input is 4, the program closes.
			else if(chosenOption == 4)
			{
				Screen.clear();
				System.exit(0);
			}
			
			// Else, if the input wasn't 1, 2, 3 or 4, it is invalid (and it doesn't leave the while loop).
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("--------------");
				System.out.println();
			}
		}
	}
	
	// Login Screen - this is the screen that displays when the user wants to log in.
	// Here, the user can choose between 3 options that will display their respective screen.
	
	public static void loginScreen()
	{	
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			System.out.println("1 - Continue to login");
			System.out.println("2 - Help");
			System.out.println("3 - Back");
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1, the program will be waiting for username and password inputs.
			if(chosenOption == 1)
			{
				Screen.clear();
				
				String username;
				char[] password;
				// Boolean value of login success/fail.
				boolean success = false;
				
				// Reads the username.
				username = cons.readLine("Username: ");
				// Reads the password.
				password = cons.readPassword("Password: ");
				
				// Creates a Login object.
				Login userLogin = new Login(username, password);
				
				// Calls 'checkLogin' and returns true/false, meaning if the login was valid or not.
				try { success = userLogin.checkLogin(); }
				catch(IOException e) { }
				
				// In case of login success, the user gets in the system. ACABAR COMMENT
				if(success)
				{
					Screen.clear();
					System.out.println("You are logged in!"); //test
					//login animation
					//displays new screen AND BREAKS THIS LOOP (MISSING)
				}
			}
			
			// If the input is 2, it opens the help screen corresponding to the current one (login screen).
			else if(chosenOption == 2)
				helpScreen("loginScreen");
			
			// If the input is 3, it leaves this screen (leaving the loop) and goes back to the previous screen (first screen).
			else if(chosenOption == 3)
			{
				firstScreen();
				break;
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
	
	public static void registrationScreen()
	{	
		Screen.clear();
		
		int chosenOption = 0;
		
		// This loop is constantly displaying the options and waiting for input.
		// The only way to leave the loop is to insert a valid input.
		while(true)
		{
			System.out.println("1 - Continue to registration");
			System.out.println("2 - Help");
			System.out.println("3 - Back");
			
			// In case of input not being a number, the 'chosenOption' will be an invalid one (zero, in this case).
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			// If the input is 1, the program will be waiting for username and password inputs.
			if(chosenOption == 1)
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
				Registration userRegistration = new Registration(username, password);
				
				// Calls 'checkRegistration' and returns true/false, meaning if the registration has succeeded or not.
				try { success = userRegistration.checkRegistration(); }
				catch(IOException e) { }
				
				// In case of registration success, it stays on the registration screen.
				if(success)
				{
					Screen.clear();
					System.out.println("Your account was successfully registered! :)");
					System.out.println("In order to be able to login, you have to wait until your account is validated by an admin.");
					System.out.println("We promise you it will be fast!");
					System.out.println("See you soon!");
					System.out.println("-------------------------------------------------------------------------------------------");
					System.out.println();
				}
			}
			
			// If the input is 2, it opens the help screen corresponding to the current one (registration screen).
			else if(chosenOption == 2)
				helpScreen("registrationScreen");
			
			// If the input is 3, it leaves this screen (leaving the loop) and goes back to the previous screen (first screen).
			else if(chosenOption == 3)
			{
				firstScreen();
				break;
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
	
	// Help screen - this is the "screen" that can be called in any other screen, when the user chooses the 'Help' option.
	// This screen prints instructions on how to interact with the screen where it is called.
	
	public static void helpScreen(String current)
	{
		Screen.clear();
		// Calls 'screen' method of 'Help' class for the current screen, that will display instructions.
		Help.screen(current);
		// In order to leave the help screen, the user has to press Enter.
		// For that, we use the method readPassword and that way nothing that the user tries to write will appear in the screen.
		cons.readPassword("Press Enter to leave the help screen and return to the previous menu.");
		Screen.clear();
	}
	
	// Main
	
	public static void main(String[] args)
	{
		// When the server starts, it needs to check if the default file system of the network data exists.
		// If any file is missing, it is created and the program can finally run.
		try { FileSystem.setup(); }
		catch(Exception e) { }
		
		// Now that the default file system of the network data exists, the first screen displays.
		firstScreen();
	}
}
