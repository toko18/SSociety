package util;

// The method ('screen') of this class can be called on any screen (whose name is given as argument)
//   and will print instructions on how to interact with that screen.

public class Help
{
	public static void screen(String current)
	{
		// Help screens of 'SSocietyServer' class.
		
		if(current.equals("SSocietyServer.firstScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla SSocietyServer.firstScreen");
			System.out.println("------------------------------------------------");
		}
		
		else if(current.equals("SSocietyServer.loginScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla SSocietyServer.loginScreen");
			System.out.println("------------------------------------------------");
		}
		
		else if(current.equals("SSocietyServer.registrationScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla SSocietyServer.registrationScreen");
			System.out.println("------------------------------------------------");
		}
		
		// Help screens of 'Home' class.
		
		else if(current.equals("Home.firstScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla Home.firstScreen");
			System.out.println("------------------------------------------------");
		}
		
		else if(current.equals("Home.accountsScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla Home.accountsScreen");
			System.out.println("------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.creationScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla Home.AccountsManagement.creationScreen");
			System.out.println("------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.pendingScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla Home.AccountsManagement.pendingScreen");
			System.out.println("------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.pendingUScreen"))
		{
			System.out.println("------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla Home.AccountsManagement.pendingUScreen");
			System.out.println("------------------------------------------------");
		}
	}
}
