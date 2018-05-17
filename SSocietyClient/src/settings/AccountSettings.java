package settings;

import java.io.File;
import java.io.IOException;

import logged.Home;

import java.io.FileWriter;
//import java.io.FileReader;
//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;

import util.Screen;

public class AccountSettings {
	
	private static Console cons = System.console();
	
	private String username;
	private String userFolder;
	private String pathHome = System.getProperty("user.home");
	
	public AccountSettings(String u)
	{
		username = u;
		userFolder = pathHome + "/SSociety_data/Users/Allusers/" + username;
	}
	
	public void settingsScreen() throws IOException
	{
		while (true)
		{
			int chosenOption = 0;
			
			System.out.println("-------------------------");
			System.out.println("Settings");
			System.out.println("-------------------------");
			System.out.println("In this section you can change your username, change your password and delete your account.");
			System.out.println("-------------------------");
			System.out.println("1 - Change username");
			System.out.println("2 - Change password");
			System.out.println("3 - Delete account");
			System.out.println("4 - Back");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) 
			{ 
				chosenOption = 0;
				Screen.clear();
			}
			
			if(chosenOption == 1)
			{
				changeUsername();
			}
			else if(chosenOption == 2)
			{
				changePassword();
			}
			else if(chosenOption == 4)
			{
				Home back = new Home(username);
				Screen.clear();
				back.homeScreen();
				break;
			}
		}
	}
	
	private void changeUsername() throws IOException
	{
		Screen.clear();
		String newUsername = cons.readLine("Choose new username: ");
		File currentDir = new File(userFolder);
		File newDir = new File(pathHome + "/SSociety_data/Users/AllUsers/" + newUsername);
		currentDir.renameTo(newDir);
		
		username = newUsername;
		
		Screen.clear();
		System.out.println("Username changed successfully!");
		cons.readLine("Press Enter to return...");
		Screen.clear();
		
		settingsScreen();
	}
	
	private void changePassword() throws IOException
	{
		while(true)
		{
			String newPassword = cons.readLine("Choose new Password: ");
			String repeatPassword = cons.readLine("Write again the password: ");
			
			if(newPassword.equals(repeatPassword))
			{
				File currentPasswordFile = new File(userFolder + "/password.txt");
				File newPasswordFile = new File(userFolder + "new password.txt");
				newPasswordFile.createNewFile();
				
				FileWriter write = new FileWriter(newPasswordFile);
				BufferedWriter writeNewPassword = new BufferedWriter(write);
				writeNewPassword.write(newPassword);
				
				currentPasswordFile.delete();
				newPasswordFile.renameTo(currentPasswordFile);
				writeNewPassword.close();
				
				Screen.clear();
				System.out.println("Password changed successfully!");
				cons.readLine("Press Enter to return...");
				Screen.clear();
				
				settingsScreen();
			}
			else {
				Screen.clear();
				System.out.println("Passwords dont't match!");
				System.out.println("Press Enter to try again");
			}
		}
	}
}