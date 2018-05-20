package util;

public class Help
{
	static final String BOLD = "\033[1m"; //line to start bold 
	static final String RESET = "\033[0m"; //line to stop bold 
	//these go to the System.out.println()
	
	public static void help()
	{//message to the user
		System.out.println("-------------------------");
		System.out.println(BOLD + "Help" + RESET);
		System.out.println("-------------------------");
		System.out.println("If you got here that is why you need help. I wouldn't say that is good because if you can't figure this out you are not very smart. Nevertheless, we are going to leave here some tips and warnings you have to pay attention to in order to enjoy your experience around SSociety.");
		System.out.println("-------------------------");
		System.out.println(BOLD + "1" + RESET + " - You must be registered to use the SSociety social network.");
		System.out.println("-------------------------");
		System.out.println(BOLD + "2" + RESET + " - After the registration process is complete you must wait for the adminstration team approval.");
		System.out.println("-------------------------");
		System.out.println(BOLD + "3" + RESET + " - You need a computer with terminal and that can run java code to use the SSociety Social Network.");
		System.out.println("-------------------------");
		System.out.println(BOLD + "4" + RESET + " - Be nice to other people and respect everyone. Insults are not allowed.");
		System.out.println("-------------------------");
		System.out.println(BOLD + "5" + RESET + " - Don't overuse SSociety. Go outside and play...");
		System.out.println("-------------------------");
		System.out.println(BOLD + "6" + RESET + " - Enjoy...");
		System.out.println("-------------------------");
	}
}
