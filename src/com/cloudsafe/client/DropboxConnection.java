package com.cloudsafe.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import com.cloudsafe.shared.Logger;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

/**
 * This class uses the Dropbox SDK to manage a Dropbox connection. This class contains a getInstance
 * static method that allows it to create instances of Dropbox connections.
 */
public final class DropboxConnection extends CloudConnection {

	/**
	 * Some app- and Dropbox-specific constants to identify the app to Dropbox.
	 */
	private static final String APP_KEY = "qvkej7tre69bpda";
	private static final String APP_SECRET = "e0ziz5ditzxq084";

	/**
	 * The Dropbox client. Is null if not authenticated. Once authenticated, by openConnection,
     * remains open until closeConnection is called.
	 */
	private DbxClient dropboxClient = null;

	/**
	 *
	 * @param authToken
	 * @return
	 */
	public static final DropboxConnection getInstance (String authToken) {
		return new DropboxConnection(authToken);
	}

	/**
	 *
	 * @param authToken
	 */
	private DropboxConnection (String authToken) {
		super();
		if (authToken != null) {
	        DbxRequestConfig config = new DbxRequestConfig ("CloudSafe/0.01", Locale.getDefault()
	        		.toString());
			dropboxClient = new DbxClient(config, authToken);
		}
	}

	public final boolean deleteFile (String path) {
		if (dropboxClient == null) {
			if (!openConnection()) {
				return false;
			}
		}
		
		try {
			dropboxClient.delete ("/" + path);
		}
		catch (DbxException e) {
			Logger.log (e.toString());
			return false;
		}
		return true;
	}
	
	public final boolean uploadFile (String path) {
		if (dropboxClient == null) {
			if (!openConnection()) {
				return false;
			}
		}
		
		try {
			File f = new File (path);
			dropboxClient.uploadFile("/" + "test", DbxWriteMode.add(), f.length(), new FileInputStream(path));
		}
		catch (Exception e) {
			Logger.log (e.toString());
			return false;
		}
		
		return true;
	}
	
	public final boolean downloadFile (String path) {
		if (dropboxClient == null) {
			if (!openConnection()) {
				return false;
			}
		}
		
		return true;
	}
	
	public final String getAuthToken () {
		if (dropboxClient == null) {
			return null;
		}
		
		return dropboxClient.getAccessToken();
	}
	
	protected final boolean openConnection () {
		DbxAppInfo appInfo = new DbxAppInfo (APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig ("CloudSafe/0.01", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect (config, appInfo);
        
        String authorizeUrl = webAuth.start();
        
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        
        DbxAuthFinish authFinish;
        
        try {
        	String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        	authFinish = webAuth.finish(code);
        }
        catch (Exception e) {
        	Logger.log (e.toString());
        	return false;
        }
        String accessToken = authFinish.accessToken;
        
        dropboxClient = new DbxClient(config, accessToken);
        
        return true;
	}
	
	public final boolean closeConnection () {
		dropboxClient = null;
		return true;
	}
}
