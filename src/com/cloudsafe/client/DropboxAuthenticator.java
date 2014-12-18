package com.cloudsafe.client;

import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

public class DropboxAuthenticator {
	
	private static final String APP_KEY = "qvkej7tre69bpda";
	private static final String APP_SECRET = "e0ziz5ditzxq084";
	private static String ACCESS_TOKEN = null;
	
	public final String getToken () {
		DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig(
            "CloudSafe/0.01", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        String authorizeUrl = webAuth.start();
		
	}
	
	public static void main (String[] args) throws Exception {
        
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;
        
        DbxClient client = new DbxClient(config, accessToken);
        System.out.println("Linked account: " + client.getAccountInfo().displayName);
	}

}
