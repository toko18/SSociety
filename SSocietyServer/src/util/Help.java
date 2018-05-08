package util;

// The method ('screen') of this class can be called on any screen (whose name is given as argument)
//   and will print instructions on how to interact with that screen.

public class Help
{
	public static void screen(String current)
	{
		if(current.equals("firstScreen"))
		{
			System.out.println("-----------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla firstScreen");
			System.out.println("-----------------------------------------");
		}
		
		else if(current.equals("loginScreen"))
		{
			System.out.println("-----------------------------------------");
			System.out.println("You are in the help screen.");
			System.out.println();
			System.out.println("A \"tutorial\" will display here");
			System.out.println("blablabla loginScreen");
			System.out.println("-----------------------------------------");
		}
	}
}
