package com.cloudsafe.utlities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

public final class Logger {
	
	private static String LOG_PATH = System.getProperty ("user.home") + "\\Desktop\\CloudSafe\\cs.log";
	private static Logger logger = null;
	
	public static final Logger getInstance () {
		if (logger == null) {
			logger = new Logger();
			return logger;
		}
		return null;
	}
	
	private Logger () {}
	
	public static final void log (String errorMsg) {
		StackTraceElement stacktrace = Thread.currentThread().getStackTrace()[2]; // The function that
																				  // called log
		if (errorMsg == null) {
			errorMsg = "Error message was null.";
		}
		
		File logFile = new File (LOG_PATH);
		logFile.getParentFile().mkdirs();
		try {
			logFile.createNewFile(); // Already checks if file exists
			PrintWriter out = new PrintWriter (new BufferedWriter (new FileWriter (logFile, true)));
		    out.println("(" + new Timestamp (new Date().getTime()).toString() + ") " + 
		    		stacktrace.getClassName() + "." + stacktrace.getMethodName() + ": " + errorMsg);
		    out.close();
		}
		catch (IOException e) {
		    System.out.println("Logger.log: " + e);
		}
	}
	
	public final boolean changeLogPath (String newPath) {
		if (isValidPath (newPath)) {
			File oldLog = new File (LOG_PATH);
		    oldLog.renameTo (new File (newPath));
			LOG_PATH = newPath;
			return true;
		}
		return false;
	}
	
	private final boolean isValidPath (String path) {
		if (path == null) {
			log ("Path was null.");
			return false;
		}
		
		return true;
	}
	
	public static void main (String[] args) {
		//Testing logging event
		log ("Testing");
	}
	
}
