package com.cloudsafe.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import com.cloudsafe.shared.Logger;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public final class DropboxConnection extends CloudConnection {
	
	private static final String APP_KEY = "qvkej7tre69bpda";
	private static final String APP_SECRET = "e0ziz5ditzxq084";
	
	private DbxClient dropboxClient = null;
	
	public static final DropboxConnection getInstance (String authToken) {
		return new DropboxConnection(authToken);
	}
	
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
			openConnection();
		}
		
		try {
			dropboxClient.delete (path);
		}
		catch (DbxException e) {
			Logger.log (e.toString());
			return false;
		}
		return true;
	}
	
	public final boolean uploadFile () {
		if (dropboxClient == null) {
			openConnection();
		}
		
		return true;
	}
	
	public final boolean downloadFile () {
		if (dropboxClient == null) {
			openConnection();
		}
		
		return true;
	}
	
	protected final String openConnection () {
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
        	return null;
        }
        String accessToken = authFinish.accessToken;
        
        dropboxClient = new DbxClient(config, accessToken);
        
		return accessToken;
	}
	
	public final boolean closeConnection () {
		dropboxClient = null;
		return true;
	}
}
