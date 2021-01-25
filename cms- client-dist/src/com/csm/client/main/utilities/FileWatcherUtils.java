package com.csm.client.main.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileWatcherUtils {

	static Calendar date = Calendar.getInstance();
	static SimpleDateFormat FileWatcherDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

	public static String convertDate(Date date) {
		return FileWatcherDateFormat.format(date);
	}



	public static void checkandCreateConfigFile() throws Exception {
		String classPath = System.getProperty("java.class.path");
		String[] paths = classPath.split(":");
		if(paths.length>0) {
			classPath=paths[0];
		}
		String[] classPaths = classPath.split("/");
		String path = "";
		// navigate one path above bin folder
		for (int i = 1; i < classPaths.length - 1; i++) {
			path = path + "/" + classPaths[i];
		}
		path += "/DBWatchProperties";
		File file = new File(path);

		if (!file.exists()) {
			System.out.println("creating the required file..");
			if (file.mkdir()) {
				path += "/dbWatchConfig.properties";
				file = new File(path);
				try {
					file.createNewFile();
					System.out.println("created.");
				} catch (IOException e) {
					throw e;
				}
			} else {
				throw new Exception("DBWatchProperties file could not be created.");
			}
		} else {
			System.out.println("Required dir is present, checking further..");
			File[] files = file.listFiles();
			boolean contains = false;
			if (files.length > 0) {
				for (File lFile : files) {
					if (lFile.getName().equals("dbWatchConfig.properties")) {
						contains = true;
						System.out.println("already contains the required file");
					}

				}
				if (!contains) {

					path += "/dbWatchConfig.properties";
					file = new File(path);
					try {
						file.createNewFile();
						System.out.println("required file was not present, hence created");
					} catch (IOException e) {
						throw e;
					}
				}

			} else {
				System.out.println("no file was not present, hence created");
				path += "/dbWatchConfig.properties";
				file = new File(path);
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw e;
				}
			}

		}

	}

}
