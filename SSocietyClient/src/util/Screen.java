package util;

public class Screen
{//this class has only on method that clears the terminal and that is used all over the program
	public static void clear()
	{//clears the screen
		System.out.print("\033[H\033[2J");  
		System.out.flush();
	}	  	
}
