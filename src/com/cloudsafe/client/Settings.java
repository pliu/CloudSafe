package com.cloudsafe.client;

public final class Settings {
	
	private static boolean REMEMBER_ME = false;
	private static String PATH_LOG = System.getProperty ("user.home")
									   + "\\Desktop\\CloudSafe\\cs.log";
	
	public static final void setRememberMe (boolean flag) {
		REMEMBER_ME = flag;
	}
	
	public static final boolean getRememberMe () {
		return REMEMBER_ME;
	}
	
	public static final void setLogPath (String path) {
		PATH_LOG = path;
	}
	
	public static final String getLogPath () {
		return PATH_LOG;
	}
}
