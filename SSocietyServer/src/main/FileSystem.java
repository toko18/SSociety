package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystem
{
	private static String pathHome = System.getProperty("user.home");
	
	public static void setup() throws IOException
	{
		String dir1 = pathHome + "/" + "SSociety_data/";
		File d1 = new File(dir1);
		if(!d1.exists())
			d1.mkdir();
		
		String dir2 = dir1 + "Topics/";
		File d2 = new File(dir2);
		if(!d2.exists())
			d2.mkdir();
		
		String dir3 = dir2 + "About SSociety/";
		File d3 = new File(dir3);
		if(!d3.exists())
			d3.mkdir();
		
		String file1 = dir3 + "description.txt";
		File f1 = new File(file1);
		if(!f1.exists())
		{
			f1.createNewFile();
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(file1));
			writer1.write("Descriçao sobre a SSociety\nou\nsobre o projeto\nou\nambos");
			writer1.append("\n(Possivelmente com parágrafos)");
			writer1.close();
		}
		
		String dir4 = dir1 + "Users/";
		File d4 = new File(dir4);
		if(!d4.exists())
			d4.mkdir();
		
		String dir5 = dir4 + "Banned/";
		File d5 = new File(dir5);
		if(!d5.exists())
			d5.mkdir();
		
		String dir6 = dir4 + "PendingAdmins/";
		File d6 = new File(dir6);
		if(!d6.exists())
			d6.mkdir();
		
		String dir7 = dir4 + "PendingOthers/";
		File d7 = new File(dir7);
		if(!d7.exists())
			d7.mkdir();
		
		String file2 = dir4 + "admins.txt";
		File f2 = new File(file2);
		if(!f2.exists())
		{
			f2.createNewFile();
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2));
			writer2.write("admin|");
			writer2.close();
		}
		
		String dir8 = dir4 + "AllUsers/";
		File d8 = new File(dir8);
		if(!d8.exists())
			d8.mkdir();
		
		String dir9 = dir8 + "admin/";
		File d9 = new File(dir9);
		if(!d9.exists())
			d9.mkdir();
		
		String file3 = dir9 + "password.txt";
		File f3 = new File(file3);
		if(!f3.exists())
		{
			f3.createNewFile();
			BufferedWriter writer3 = new BufferedWriter(new FileWriter(file3));
			writer3.write("elonmusk|");
			writer3.close();
		}
		
		String file4 = dir9 + "likes.txt";
		File f4 = new File(file4);
		if(!f4.exists())
			f4.createNewFile();
		
		String file5 = dir9 + "posts.txt";
		File f5 = new File(file5);
		if(!f5.exists())
			f5.createNewFile();
		
		String file6 = dir9 + "subscriptions.txt";
		File f6 = new File(file6);
		if(!f6.exists())
		{
			f6.createNewFile();
			BufferedWriter writer4 = new BufferedWriter(new FileWriter(file6));
			writer4.write("About SSociety| |");
			writer4.close();
		}
	}
}
