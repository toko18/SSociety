package main;

import java.io.File;
import java.io.IOException;
import util.Screen;

public class FileSystem
{
	private static String pathHome = System.getProperty("user.home");
	
	public static boolean checkFileSystem() throws IOException
	{
		String dir1 = pathHome + "/" + "SSociety_data/";
		File d1 = new File(dir1);
		
		String dir2 = dir1 + "Topics/";
		File d2 = new File(dir2);
		
		String dir3 = dir2 + "About SSociety/";
		File d3 = new File(dir3);
		
		String file1 = dir3 + "description.txt";
		File f1 = new File(file1);
		
		String file9 = dir3 + "subscriptions.txt";
		File f9 = new File(file9);
		
		String dir4 = dir1 + "Users/";
		File d4 = new File(dir4);
		
		String dir5 = dir4 + "Banned/";
		File d5 = new File(dir5);
		
		String dir6 = dir4 + "PendingAdmins/";
		File d6 = new File(dir6);
		
		String dir7 = dir4 + "PendingOthers/";
		File d7 = new File(dir7);
		
		String file2 = dir4 + "admins.txt";
		File f2 = new File(file2);
		
		String dir8 = dir4 + "AllUsers/";
		File d8 = new File(dir8);
		
		String dir9 = dir8 + "admin/";
		File d9 = new File(dir9);
		
		String file3 = dir9 + "password.txt";
		File f3 = new File(file3);
		
		String file4 = dir9 + "likes.txt";
		File f4 = new File(file4);
		
		String file5 = dir9 + "posts.txt";
		File f5 = new File(file5);
		
		String file6 = dir9 + "subscriptions.txt";
		File f6 = new File(file6);
		
		String file7 = dir2 + "topics.txt";
		File f7 = new File(file7);
		
		String dir10 = dir3 + "Posts/";
		File d10 = new File(dir10);

		
		if (!d1.exists() || !d2.exists() || !d3.exists() || !f1.exists() || !d4.exists() || !d5.exists() || !d6.exists() || !d7.exists() || !f2.exists() || !d8.exists() || !d9.exists() || !f3.exists() || !f4.exists() || !f5.exists() || !f6.exists() || !f7.exists() || !d10.exists() || !f9.exists())
		{	
			Screen.clear();
			System.out.println("Problem found while trying to start the program.");
			System.out.println("Wait for an admin to fix it. Sorry for the trouble...");
			return false;
		}
		return true;
	}
}
