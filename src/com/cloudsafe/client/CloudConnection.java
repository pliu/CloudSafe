package com.cloudsafe.client;

import com.cloudsafe.shared.Logger;

/**
 * This abstract class represents a connection to a generic cloud storage service. Subclasses
 * represent connections to specific cloud storage service (i.e.: Dropbox). This class contains a
 * getInstance static method that allows it to create instances of specific cloud storage
 * connections.
 */
public abstract class CloudConnection {

	/**
	 * The supported cloud storage services. These are used as arguments for getInstance.
	 */
	public static final String DROPBOX = "Dropbox";
	public static final String GDRIVE = "GDrive";

	/**
	 * getInstance gets a connection to the specified cloud storage service. Validation and/or usage
	 * or the authorization token is performed by the specific connection subclasses.
	 *
	 * @param cloudProvider The cloud storage service to connect to. Must be one of the constants in
	 *                      this class.
	 * @param authToken The authorization token, if one exists, for a given cloud storage service
	 *                  that enables connection without having to re-enter credentials.
	 * @return Returns null if cloudProvider is null or not supported.
	 */
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

	/**
	 * Constructor should only be used by subclasses.
	 */
	protected CloudConnection () {}
	
	public abstract boolean deleteFile (String path);
	
	public abstract boolean uploadFile (String path);
	
	public abstract boolean downloadFile (String path);
	
	public abstract boolean closeConnection ();
	
	public abstract String getAuthToken ();
	
	protected abstract boolean openConnection ();
}
