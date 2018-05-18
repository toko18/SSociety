package settings;

import java.io.File;
import java.io.IOException;

import logged.Home;

import java.io.FileWriter;
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
			System.out.println("1 - Change password");
			System.out.println("2 - Delete account");
			System.out.println("3 - Back");
			
			try { chosenOption = Integer.parseInt(cons.readLine("Insert option number: ")); }
			catch(Exception e) 
			{ 
				chosenOption = 0;
				Screen.clear();
			}
			
			if(chosenOption == 1)
			{
				changePassword();
			}
			else if(chosenOption == 3)
			{
				Home back = new Home(username);
				Screen.clear();
				back.homeScreen();
				return;
			}
			else
			{
				Screen.clear();
				System.out.println("Invalid input! Try again...");
				System.out.println();
			}
		}
	}
	
	private void changePassword() throws IOException
	{
		while(true)
		{
			Screen.clear();
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
				cons.readLine("Press Enter to try again...");
			}
		}
	}
}