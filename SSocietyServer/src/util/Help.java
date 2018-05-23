package util;

//-------------------------------------------------*Help*-------------------------------------------------------

// The method ('screen') of this class can be called on any screen (whose name is given as argument)
//   and will print instructions on how to interact with that screen.

public class Help
{
	public static void screen(String current)
	{
		// Help screens of 'SSocietyServer' class.
		
		if(current.equals("SSocietyServer.firstScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" As you have notice, there are lines with digits followed by text.");
			System.out.println(" You will see menus like this one in every screen.");
			System.out.println(" In order to select the pretended action, all you have to do is to ");
			System.out.println("write the option number in the terminal and then press Enter.");
			System.out.println();
			System.out.println(" In this particular case:");
			System.out.println("   -Write 1 if you already have an account and you want to log in.");
			System.out.println("   -Write 2 if you want to register a new (admin) account - remember ");
			System.out.println("  that if you don't want to be admin, you should do the registration ");
			System.out.println("  in the Client. Also, an admin can log in the client as if he is a ");
			System.out.println("  normal user.");
			System.out.println("   -Write 3 to...well, you are already here...");
			System.out.println("   -Write 4 to shut down the server.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("SSocietyServer.loginScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to continue to login - your username and password will be ");
			System.out.println("requested and you will log in the server.");
			System.out.println(" -Write 3 to return to the first screen.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("SSocietyServer.registrationScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to continue to registration - you will be asked to choose ");
			System.out.println("a username and password for your account and then you will only be ");
			System.out.println("able to use your account when an admin activates it.");
			System.out.println(" -Write 3 to return to the first screen.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		// Help screens of 'Home' class.
		
		else if(current.equals("Home.firstScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to manage accounts.");
			System.out.println(" -Write 2 to manage topics.");
			System.out.println(" -Write 3 to see network statistics.");
			System.out.println(" -Write 4 if you want to change your password or delete your account.");
			System.out.println(" -Write 6 to log out and return to the very first screen.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.accountsScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to directly create a new (admin or others) account.");
			System.out.println(" -Write 2 to validate pending (admin or others) accounts.");
			System.out.println(" -Write 3 to ban/unban/delete accounts.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.creationScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to directly create a new admin account - you will be asked ");
			System.out.println("to choose a username and password for the account.");
			System.out.println(" -Write 2 to directly create a new normal user account - you will be ");
			System.out.println("asked to choose a username and password for the account.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.pendingScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to validate/reject a pending admin account - a list of all ");
			System.out.println("pending admin accounts will display.");
			System.out.println(" -Write 2 to validate/reject a pending normal user account - a list of");
			System.out.println("all pending normal user accounts will display.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.pendingUScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("account that you want to validate/delete.");
			System.out.println(" After that, it will be asked if you want to validate or delete the ");
			System.out.println("account (or go back).");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.banishmentScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to ban accounts - a list of all active accounts will ");
			System.out.println("display.");
			System.out.println(" -Write 2 to unban/delete accounts - a list of all banned accounts ");
			System.out.println("will display.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.banUScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("account that you want to ban.");
			System.out.println(" After that, it will be asked for you to write a ban message. and the ");
			System.out.println("account will be banned.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.AccountsManagement.unbanUScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("account that you want to unban/delete.");
			System.out.println(" After that, it will be asked if you want to unban or delete the ");
			System.out.println("account (or go back).");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.topicsScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to create a new topic - it will be asked for you to choose ");
			System.out.println("the new topic's title, the maximum limit of posts that the topic can ");
			System.out.println("have and the topic's description.");
			System.out.println(" -Write 2 to edit existing topics - a list of all active topics will ");
			System.out.println("display.");
			System.out.println(" -Write 3 to delete existing topics - a list of all active topics will");
			System.out.println("display.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.TopicsManagement.editScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("topic that you want to edit.");
			System.out.println(" After that, it will be asked for you to write a the new topic's ");
			System.out.println("title, posts limit and description. In that stage, if you want to keep");
			System.out.println("the description, for example, you can simply press Enter and the topic");
			System.out.println("will keep the old description.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.TopicsManagement.deletionScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("topic that you want to delete and it will be IMMEDIATELY DELETED.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.statisticsScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to see statistics related to the active topics - it will be ");
			System.out.println("displayed some general statistics and also a list of all active topics");
			System.out.println("from which you can choose to see particular statistics about a ");
			System.out.println("specific topic.");
			System.out.println(" -Write 2 to see statistics related to the active accounts - it will ");
			System.out.println("be displayed some general statistics and also a list of all active ");
			System.out.println("accounts from which you can choose to see particular statistics about ");
			System.out.println("a specific topic.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.NetworkStatistics.topicsScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("topic from which you want to see the statistics.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.NetworkStatistics.accountsScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" Here you need to write the option number that corresponds to the ");
			System.out.println("account from which you want to see the statistics.");
			System.out.println("----------------------------------------------------------------------");
		}
		
		else if(current.equals("Home.accountScreen"))
		{
			System.out.println("----------------------------------------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println(" -Write 1 to change your account's password - it will be asked for you");
			System.out.println("to write the current password and then the new one and repeat the new ");
			System.out.println("one. If you fail to write the old password or if the password changes ");
			System.out.println("successfully, you will be logged out.");
			System.out.println(" -Write 2 to delete your account - it will be asked if you are sure ");
			System.out.println("that you want to delete your account, and you can choose to delete it ");
			System.out.println("FOREVER or cancel the operation. You will be logging out if the ");
			System.out.println("account is successfully deleted.");
			System.out.println("----------------------------------------------------------------------");
		}
	}
}
