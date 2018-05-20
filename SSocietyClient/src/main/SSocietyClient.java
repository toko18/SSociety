package main;

import java.io.Console;
import logged.Home;
import util.Screen;


public class SSocietyClient
{
	private static Console cons = System.console(); //allows use of java console in the program
	static String pathHome = System.getProperty("user.home"); //gets the path to the user home folder were will be the SSociety_data folder
	
	static final String BOLD = "\033[1m"; //starts use of bold
	static final String RESET = "\033[0m"; //stops writing in bold
	
	public static void helpScreen() //help screen that will appear wen the program starts
	{
		//open help file
		
		System.out.print("Press Enter to continue...");
	}
	
	public static void registrationScreen() throws Exception //called when the 
	{
		int chosenOption = 0; //option that chekcs if the user wants to proceed with the registration or go back
		
		while(true)
		{
			System.out.println("1 - Back");
			System.out.println("2 - Continue");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; Screen.clear();} //ensures that the chosen option is an integer
			
			if(chosenOption == 1) //if user wants to go back
			{
				Screen.clear();
				firstScreen();
			}
			else if (chosenOption == 2) //if user wants to continue
			{	
				Screen.clear();
				String username;
				char[] firstPassword; //array of characters that saves the first password chosen by the user
				char[] secondPassword; //arrays to characters that saves the second password chosen by the user
				
				System.out.println("--------------------------");
				System.out.println(BOLD + "Registration" + RESET);
				System.out.println("--------------------------");
				username = cons.readLine("Choose your username: "); //gets the first username that will be used by the user
				firstPassword = cons.readPassword("Choose your password: "); //gets the first password
				secondPassword = cons.readPassword("Enter chosen password again: "); //gets the second password 
				
				Registration userRegistration = new Registration(username, firstPassword, secondPassword);
				//creates new object that then will carry on with the registration
				
				//------------------------------messages that can appear when the user is trying to register---------------------
				
				if (userRegistration.checkRegistration() == 0) 
				{//user sees this if the registration is concluded with success
					Screen.clear();
					System.out.println("Congratulations! Registration concluded...");
					System.out.println("Wait for admin's decision.");
					cons.readLine("Press Enter to continue...");
					Screen.clear();
					firstScreen();
					return;
					
				}
				else if (userRegistration.checkRegistration() == 1)
				{//user sees this if the username the user is trying to use is already in use
					Screen.clear();
					System.out.println("A user with this name already exists...");
					System.out.println("Choose another username and try again.");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 2)
				{//user sees this if the passords dont match
					Screen.clear();
					System.out.println("Entered passwords don't match");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 3)
				{//user sees this if the registration is already done and is waiting approval by the admin
					Screen.clear();
					System.out.println("Registration request already done.");
					System.out.println("Please wait for admin's decision...");
					cons.readLine("Press Enter to continue...");
					
				}
				else if (userRegistration.checkRegistration() == 4)
				{ //user sees this if the username contains strange characters
					Screen.clear();
					System.out.println("Your username can only contain letters and numbers.");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 5)
				{//user sees this if it is trying to use empty username
					Screen.clear();
					System.out.println("Your username can not be empty. Try again!");
					cons.readLine("Press Enter to continue...");
				}
				else if (userRegistration.checkRegistration() == 6)
				{//user sees this if he is trying to use empty passwords
					Screen.clear();
					System.out.println("You have to choose a password that is not empty. Try again!");
					cons.readLine("Press Enter to continue...");
				}
				Screen.clear();
				//------------------------------------------------------------------------------------------------
			}
		}
	}
	
	public static void loginScreen () throws Exception

	{	
		int chosenOption = 0; //variable that saves the choice made by the user
		
		while (true)
		{
			System.out.println("1 - Back");
			System.out.println("2 - Continue");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; } //ensures that the user rights an integer
			
			if(chosenOption == 1) //if user wants to go back
			{	
				Screen.clear();
				firstScreen(); //goes back to the first screen
				return;
			}
			else if (chosenOption == 2)
			{	
				Screen.clear();
				boolean success = false; //checks user login
				
				String username; //string that save the username entered by the user
				char[] password; //array of chars that contains the password
				
				//what the user sees when proceeds to login
				System.out.println("--------------------------");
				System.out.println(BOLD + "Login" + RESET);
				System.out.println("--------------------------");
				username = cons.readLine("Username: "); //console waits entering the username
				password = cons.readPassword("Password: "); //console waits entering password
				
				Login userLogin = new Login(username, password);
				//creates object login that verifies the login
				
				success = userLogin.checkLogin(); //receives boolean that indicates if the login was successful
				
				if(success)//if the login is successful
				{
					Screen.clear();
					System.out.println("You are logged in!");
					cons.readLine("Press Enter to continue...");
					Screen.clear();
					
					Home userAccount = new Home(username); //creates object of type home
					userAccount.homeScreen();//goes to homescreen - user is inside is account
					return;
				}
				else
				{
					//prints message from the Home Class
					cons.readLine("Press Enter to continue...");
					Screen.clear();
				}
			}	
			Screen.clear();
		}
	}
	
	
	public static void firstScreen() throws Exception
	{	//what the user sees when the program starts
		//from here the user can login, register, exit the program or see the help screen
		int chosenOption = 0;
		//option chosen by the user
		while(true)
		{
			//what the users sees and reads
			System.out.println("--------------------------");
			System.out.println(BOLD + "Welcome to SSociety!" + RESET);
			System.out.println("--------------------------");
			System.out.println("1 - Login");
			System.out.println("2 - Register");
			System.out.println("3 - Help");
			System.out.println("4 - Exit");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) { chosenOption = 0; } //ensures that the user chooses an Integer
			
			if(chosenOption == 1) 
			{//goes to login
				Screen.clear();
				loginScreen();
				return;
			}
			else if(chosenOption == 2) 
			{
				//goes to registration
				Screen.clear();
				registrationScreen();
				return;
			}
			else if(chosenOption == 3)
			{
				//goes to help screen
				helpScreen();
				return;
			}
			else if(chosenOption == 4)
			{	
				//exit the program
				Screen.clear();
				System.exit(0);
			}
			Screen.clear();
		}
	}
	
	
	public static void main(String[] args) throws Exception 
	{	//checks if the filesystem is correct. if it is the program starts. if not appears error message.
		if(!FileSystem.checkFileSystem())
		{
			cons.readLine("Press Enter to continue...");
			Screen.clear();
			System.exit(0);
		}
		else
		{	//filesystem is correct and the program starts - goes to the first screen
			Screen.clear();
			firstScreen();
		}
	}
}
