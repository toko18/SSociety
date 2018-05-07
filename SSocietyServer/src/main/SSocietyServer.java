package main;

import java.io.Console;
import java.io.IOException;
import util.Help;
import util.Screen;

public class SSocietyServer
{	
	private static Console cons = System.console();
	
	public static void firstScreen()
	{	
		Screen.clear();
		
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
				loginScreen();
				
			else if(chosenOption == 2) {}
				//registrationScreen();
				
			else if(chosenOption == 3)
				helpScreen("firstScreen");
				
			else if(chosenOption == 4)
			{
				Screen.clear();
				System.exit(0);
			}
			
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("--------------");
				System.out.println();
			}
		}
	}
	
	public static void loginScreen()
	{	
		Screen.clear();
		
		int chosenOption = 0;
		
		while(true)
		{
			System.out.println("1 - Login");
			System.out.println("2 - Help");
			System.out.println("3 - Back");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; }
			
			if(chosenOption == 1)
			{
				Screen.clear();
				
				String username;
				char[] password;
				boolean success = false;
				
				username = cons.readLine("Username: ");
				password = cons.readPassword("Password: ");
				
				Login userLogin = new Login(username, password);
				
				try { success = userLogin.checkLogin(); }
				catch(IOException e) { }
				
				if(success)
				{
					Screen.clear();
					System.out.println("You are logged in!"); //test
					//login animation
					//displays new screen
				}
			}
			
			else if(chosenOption == 2)
				helpScreen("loginScreen");
			
			else if(chosenOption == 3)
				firstScreen();
			
			else
			{
				Screen.clear();
				System.out.println("Invalid input!");
				System.out.println("--------------");
				System.out.println();
			}
		}
	}
	
	public static void helpScreen(String current)
	{
		Screen.clear();
		Help.screen(current);
		cons.readPassword("Press Enter to leave the help screen and return to the previous menu.");
		Screen.clear();
	}
	
	public static void main(String[] args)
	{
		try
		{
			FileSystem.setup();
		}
		catch (Exception e)
		{
		}
		
		firstScreen();
	}
}
