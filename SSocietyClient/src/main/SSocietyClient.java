package main;

import java.util.Scanner;

public class SSocietyClient
{
	
	public static boolean login (String userName, String password)
	{
		Login client = new Login (userName, password);
		return client.checkLogin();
	}
	
	
	public static void main(String[] args) 
	{
		Scanner userInput = new Scanner (System.in);
		String userName = " ";
		String password = " ";
		
		System.out.println("Welcome to SSociety!");
		System.out.println("Enter your User Name: ");
		System.out.println("Enter your password: ");
		
		while (true)
		{
			userName = userInput.next();
			password = userInput.next();
			
			boolean success = login(userName, password);
			
			if (success)
			{
				break;
			}
		}
	}
}
