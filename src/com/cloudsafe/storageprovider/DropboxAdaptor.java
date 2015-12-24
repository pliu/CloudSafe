package com.cloudsafe.storageprovider;

import com.cloudsafe.pluginframework.Service;
import com.cloudsafe.utlities.Logger;
import com.dropbox.core.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Created by pengl on 1/20/2016.
 */
public final class DropboxAdaptor extends AbstractStorageProvider {

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

    public static Service getInstance() {
        return new DropboxAdaptor();
    }

    private DropboxAdaptor() {

    }

    private static final String name = "dropbox";
    private static final String description = "";
    private static final String version = "1.0.0";

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
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
            Logger.log(e.toString());
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

    public final boolean openConnection () {
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("CloudSafe/0.01", Locale.getDefault().toString());
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
