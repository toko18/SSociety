package main;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import util.Screen;

public class SSocietyClient
{
	private static Console cons = System.console();
	private static String pathHome = System.getProperty("user.home");
	
	public static void helpScreen()
	{
		//open help file
		
		System.out.print("Press Enter to continue...");
	}
	
	public static void registrationScreen() throws IOException
	{
		int chosenOption = 0;
		
		System.out.println("1 - Back");
		System.out.println("2 - Continue");
		
		try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
		catch(Exception e) { chosenOption = 0; }
		
		if(chosenOption == 1)
		{
			Screen.clear();
			firstScreen();
		}
		else if (chosenOption == 2)
		{	
			Screen.clear();
			String username;
			char[] firstPassword;
			char[] secondPassword;
			
			while(true)
			{
				username = cons.readLine("Choose your username: ");
				firstPassword = cons.readPassword("Choose your password: ");
				secondPassword = cons.readPassword("Enter chosen password agin: ");
				
				Registration userRegistration = new Registration(username, firstPassword, secondPassword);
				
				if (userRegistration.check() == 0) 
				{
					System.out.println("Registration concluded... Wait for admin's decision.");
					cons.readLine("Press Enter to continue...");
					firstScreen();
					
				}
				else if (userRegistration.check() == 1)
				{
					System.out.println("A user with this name already exists...");
					System.out.println("Try again");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.check() == 2)
				{
					System.out.println("Entered passwords don't match");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.check() == 3)
				{
					System.out.println("Registration request already done.");
					System.out.println("Please wait for admin's decision...");
					cons.readLine("Press Enter to continue...");
					
				}
				Screen.clear();
			}
		}
	}
	
	public static void loginScreen () throws IOException

	{	
		int chosenOption = 0;
		
		while (true)
		{
			System.out.println("1 - Back");
			System.out.println("2 - Continue");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			if(chosenOption == 1)
			{
				firstScreen();
			}
			else if (chosenOption == 2)
			{	
				boolean success = false;
				
				while(success == false)
				{ 
					String username;
					char[] password;
					
					username = cons.readLine("Username: ");
					password = cons.readPassword("Password: ");
					
					Login userLogin = new Login(username, password);
					
					try { success = userLogin.checkLogin(); }
					catch(IOException e) { }
					
					if(success)
					{
						Screen.clear();
						System.out.println("You are logged in!");
						//login animation
						//displays new screen -- inicio de sessao
					}
				}
			}	
			Screen.clear();
		}
	}
	
	
	public static void firstScreen() throws IOException
	{	
		int chosenOption = 0;
		
		while(true)
		{
			System.out.println("Welcome to SSociety!");
			System.out.println();
			System.out.println("1 - Login");
			System.out.println("2 - Register");
			System.out.println("3 - Help");
			System.out.println("4 - Exit");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			if(chosenOption == 1) 
			{
				Screen.clear();
				loginScreen();
			}
			else if(chosenOption == 2) {
				Screen.clear();
				registrationScreen();
			}
			else if(chosenOption == 3)
			{
				helpScreen();
				//cat file with help instruction --> toko18
			}
			else if(chosenOption == 4)
			{
				System.exit(0);
			}
			Screen.clear();
		}
	}
	
	
	public static void main(String[] args) throws IOException 
	{	
		String programData = pathHome + "/SSociety_data";
		File directoryCheck = new File(programData);
		
		if(directoryCheck.exists())
		{
			firstScreen();
		}
		else
		{
			System.out.println("Problem found while trying to start the program...");
			System.out.println("Wait for an admin to fix it. Sorry for the trouble...");
			System.out.println();
			cons.readLine("Press Enter key to quit... ");
			System.exit(0);
		}
	}
}
