package util;

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
			System.out.println("blablabla");
			System.out.println("-----------------------------------------");
		}
	}
}
