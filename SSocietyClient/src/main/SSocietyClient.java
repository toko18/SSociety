package main;

import java.util.Scanner;

public class SSocietyClient
{
	
	public static void login (Scanner userInput)
	{	
		int chosenOption = 0;
		
		while (chosenOption == 1 || chosenOption == 2)
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
				String userName = " ";
				String password = " ";
				
				System.out.print("Enter your username: ");
				userName = userInput.next();
				System.out.print("Enter your password: ");
				System.out.println();
			}
			//clean screen
		}
	}
	
	public static void firstScreen(Scanner userInput)
	{
		System.out.println("Welcome to SSociety!");
		System.out.println();
		System.out.println("1 - Login");
		System.out.println("2 - Register");
		System.out.println("3 - Help");
		System.out.println("4 - Exit");
		
		int chosenOption = 0;
		chosenOption = userInput.nextInt();
		
		if(chosenOption == 1) 
		{
			//clean screen
			login(userInput);
		}
	}
	
	
	public static void main(String[] args) 
	{	
		Scanner userInput = new Scanner (System.in);
		firstScreen(userInput);
	}
}
