package com.cloudsafe.client;

import com.cloudsafe.shared.Logger;

public abstract class CloudConnection {
	
	public static final String DROPBOX = "Dropbox";
	public static final String GDRIVE = "GDrive";
	
	public static final CloudConnection getInstance (String cloudProvider, String authToken) {
		if (cloudProvider == null) {
			Logger.log ("Cloud provider was null.");
			return null;
		}
		
		switch (cloudProvider) {
		case DROPBOX:
			return DropboxConnection.getInstance(authToken);
		case GDRIVE:
			return null;
		default:
			Logger.log (cloudProvider + " is not a valid cloud provider.");
			return null;
		}
	}
	
	protected CloudConnection () {}
	
	public abstract boolean deleteFile (String path);
	
	public abstract boolean uploadFile ();
	
	public abstract boolean downloadFile ();
	
	public abstract boolean closeConnection ();
	
	protected abstract String openConnection ();

}
