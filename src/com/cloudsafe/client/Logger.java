package com.cloudsafe.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

public final class Logger {
	
	public static final void log (String errorMsg) {
		File logFile = new File (Settings.getLogPath());
		logFile.getParentFile().mkdirs();
		try {
			if (logFile.exists()) {
				logFile.createNewFile();
			}
			PrintWriter out = new PrintWriter (new BufferedWriter (new FileWriter (logFile, true)));
		    out.println("(" + new Timestamp (new Date().getTime()).toString() + ") " + errorMsg);
		    out.close();
		}
		catch (IOException e) {
		    System.out.println("Logger.log: " + e);
		}
	}
	
	public static void main (String[] args) {
		log ("testing");
	}
	
}
