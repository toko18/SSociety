package main;

import java.util.Scanner;

import util.Help;
import util.Screen;

public class SSocietyServer
{
	private static Scanner stdin = new Scanner(System.in);
	
	public static void firstScreen()
	{	
		int chosenOption = 0;
		
		while(true)
		{
			Screen.clear();
			System.out.println("Welcome to SSociety!");
			System.out.println();
			System.out.println("1 - Login");
			System.out.println("2 - Register");
			System.out.println("3 - Help");
			System.out.println("4 - Exit");
			
			try
			{
				chosenOption = Integer.parseInt(stdin.nextLine());
			}
			catch(Exception e)
			{
				chosenOption = 0;
			}
			
			if(chosenOption == 1) 
			{
				Screen.clear();
				//loginScreen();
			}
			else if(chosenOption == 2)
			{
				Screen.clear();
				//registrationScreen();
			}
			else if(chosenOption == 3)
			{
				Screen.clear();
				Help.screen("firstScreen");
				System.out.println("Press Enter to leave the help screen and return to the previous menu.");
				stdin.nextLine();
				continue;
			}
			else if(chosenOption == 4)
			{
				Screen.clear();
				System.exit(0);
			}
			
			Screen.clear();
			System.out.println("Invalid input!");
			System.out.println("--------------");
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		firstScreen();
	}
}
