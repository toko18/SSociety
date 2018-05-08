package util;

// The method ('clear') is used to "clear" the terminal

public class Screen
{
	public static void clear()
	{
		System.out.print("\033[H\033[2J");  
		System.out.flush();
	}	  	
}
