package main;

import java.io.Console;
import logged.Home;
import util.Screen;


public class SSocietyClient
{
	private static Console cons = System.console();
	static String pathHome = System.getProperty("user.home");
	
	static final String BOLD = "\033[1m";
	static final String RESET = "\033[0m";
	
	public static void helpScreen()
	{
		//open help file
		
		System.out.print("Press Enter to continue...");
	}
	
	public static void registrationScreen() throws Exception
	{
		int chosenOption = 0;
		
		while(true)
		{
			System.out.println("1 - Back");
			System.out.println("2 - Continue");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; Screen.clear();}
			
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
				
				System.out.println("--------------------------");
				System.out.println(BOLD + "Registration" + RESET);
				System.out.println("--------------------------");
				username = cons.readLine("Choose your username: ");
				firstPassword = cons.readPassword("Choose your password: ");
				secondPassword = cons.readPassword("Enter chosen password again: ");
				
				Registration userRegistration = new Registration(username, firstPassword, secondPassword);
				
				if (userRegistration.checkRegistration() == 0) 
				{
					Screen.clear();
					System.out.println("Congratulations! Registration concluded...");
					System.out.println("Wait for admin's decision.");
					cons.readLine("Press Enter to continue...");
					Screen.clear();
					firstScreen();
					return;
					
				}
				else if (userRegistration.checkRegistration() == 1)
				{
					Screen.clear();
					System.out.println("A user with this name already exists...");
					System.out.println("Choose another username and try again.");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 2)
				{
					Screen.clear();
					System.out.println("Entered passwords don't match");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 3)
				{
					Screen.clear();
					System.out.println("Registration request already done.");
					System.out.println("Please wait for admin's decision...");
					cons.readLine("Press Enter to continue...");
					
				}
				else if (userRegistration.checkRegistration() == 4)
				{
					Screen.clear();
					System.out.println("Your username can only contain letters and numbers.");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 5)
				{
					Screen.clear();
					System.out.println("Your username can not be empty. Try again!");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 6)
				{
					Screen.clear();
					System.out.println("You have to choose a password that is not empty. Try again!");
					cons.readLine("Press Enter to continue...");
				}
				Screen.clear();
			}
		}
	}
	
	public static void loginScreen () throws Exception

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
				Screen.clear();
				firstScreen();
				return;
			}
			else if (chosenOption == 2)
			{	
				Screen.clear();
				boolean success = false;
				
				String username;
				char[] password;
				
				System.out.println("--------------------------");
				System.out.println(BOLD + "Login" + RESET);
				System.out.println("--------------------------");
				username = cons.readLine("Username: ");
				password = cons.readPassword("Password: ");
				
				Login userLogin = new Login(username, password);
				
				success = userLogin.checkLogin();
				
				if(success)
				{
					Screen.clear();
					System.out.println("You are logged in!");
					cons.readLine("Press Enter to continue...");
					Screen.clear();
					
					Home userAccount = new Home(username);
					userAccount.homeScreen();
					return;
				}
				else
				{
					cons.readLine("Press Enter to continue...");
					Screen.clear();
				}
			}	
			Screen.clear();
		}
	}
	
	
	public static void firstScreen() throws Exception
	{	
		int chosenOption = 0;
		
		while(true)
		{
			System.out.println("--------------------------");
			System.out.println(BOLD + "Welcome to SSociety!" + RESET);
			System.out.println("--------------------------");
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
				return;
			}
			else if(chosenOption == 2) {
				Screen.clear();
				registrationScreen();
				return;
			}
			else if(chosenOption == 3)
			{
				helpScreen();
				return;
				//cat file with help instruction --> toko18
			}
			else if(chosenOption == 4)
			{	
				Screen.clear();
				System.exit(0);
			}
			Screen.clear();
		}
	}
	
	
	public static void main(String[] args) throws Exception 
	{	
		if(!FileSystem.checkFileSystem())
		{
			cons.readLine("Press Enter to continue...");
			Screen.clear();
			System.exit(0);
		}
		else
		{	
			Screen.clear();
			firstScreen();
		}
	}
}
