package main;

import java.util.Scanner;

public class SSocietyClient
{
	public static void helpScreen(Scanner userInput)
	{
		//open help file
		
		System.out.print("Press Enter to continue...");
		
		String nextStep;
		nextStep = userInput.next();
	}
	
	public static void registrationScreen(Scanner userInput)
	{
		int chosenOption = 0;
		
		while (chosenOption != 1 || chosenOption != 2)
		{
			System.out.println("1 - Back");
			System.out.println("2 - Continue");
			
			System.out.print("Enter option: ");
			chosenOption = userInput.nextInt();
			
			if(chosenOption == 1)
			{
				firstScreen(userInput);
			}
			else if (chosenOption == 2)
			{	
				String userName;
				String firstPassword;
				String secondPassword;
				
				int success = 0;
				
				while(success == 0)
				{
					System.out.print("Choose your username: ");
					userName = userInput.next();
					System.out.print("Choose your password: ");
					firstPassword = userInput.next();
					System.out.println("Enter chosen password again:");
					secondPassword = userInput.next();
					
					Registration userRegistration = new Registration(userName, firstPassword, secondPassword);
					
					String nextStep;
					
					if (userRegistration.check() == 0) 
					{
						success = 1;
						System.out.println("Success! Welcome to SSociety!");
						System.out.println("Press Enter to continue...");
						
						nextStep = userInput.next();
						firstScreen(userInput);
						
					}
					else if (userRegistration.check() == 1)
					{
						System.out.println("A user with this name already exists...");
						System.out.println("Try again");
						System.out.println();
						System.out.println("Press Enter key to continue...");
						
						nextStep = userInput.next();
						//clear screen
					}
					else if (userRegistration.check() == 2)
					{
						System.out.println("Entered passwords don't match");
						System.out.print("Press Enter key to continue...");
						
						nextStep = userInput.next();
						//clear screen
					}
				}
			}
			//clean screen
		}
	}
	
	public static void loginScreen (Scanner userInput)

	{	
		int chosenOption = 0;
		
		while (chosenOption != 1 || chosenOption != 2)
		{
			System.out.println("1 - Back");
			System.out.println("2 - Continue");
			
			System.out.print("Enter option: ");
			chosenOption = userInput.nextInt();
			
			if(chosenOption == 1)
			{
				firstScreen(userInput);
			}
			else if (chosenOption == 2)
			{	
				boolean success = false;
				
				while(!success)
				{ 
					String userName;
					String password;
					
					System.out.print("Enter your username: ");
					userName = userInput.next();
					System.out.print("Enter your password: ");
					password = userInput.next();
					
					Login userLogin = new Login(userName, password);
					
					success = userLogin.checkLogin();
				}
			}
				
				
			//clean screen
		}
	}
	
	
	public static void firstScreen(Scanner userInput)
	{	
		int chosenOption = 0;
		
		while(chosenOption != 1 || chosenOption != 2 || chosenOption != 3 || chosenOption != 4)
		{
			System.out.println("Welcome to SSociety!");
			System.out.println();
			System.out.println("1 - Login");
			System.out.println("2 - Register");
			System.out.println("3 - Help");
			System.out.println("4 - Exit");
			
			chosenOption = userInput.nextInt();
			
			if(chosenOption == 1) 
			{
				//clean screen
				loginScreen(userInput);
			}
			else if(chosenOption == 2) {
				//clean screen
				registrationScreen(userInput);
			}
			else if(chosenOption == 3)
			{
				helpScreen(userInput);
				//cat file with help instruction (vais fazer esta merda paneleiro) --> toko18
			}
			else if(chosenOption == 4)
			{
				System.exit(0);
			}
			//clean screen
		}
	}
	
	
	public static void main(String[] args) 
	{	
		Scanner userInput = new Scanner (System.in);
		firstScreen(userInput);
	}
}
