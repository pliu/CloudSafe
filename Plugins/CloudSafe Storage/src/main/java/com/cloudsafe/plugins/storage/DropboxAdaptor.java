package com.cloudsafe.plugins.storage;

import com.cloudsafe.shared.AbstractStorageAdaptor;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.v2.DbxClientV2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 *
 */
public class DropboxAdaptor extends AbstractStorageAdaptor {

    private static final String NAME = "Dropbox";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "This adaptor allows you to store and retrieve files from your Dropbox account.";
    private static final String APP_KEY = "qvkej7tre69bpda";
    private static final String APP_SECRET = "e0ziz5ditzxq084";

    private DbxClientV2 dropboxClient;

    public static DropboxAdaptor newInstance() {
        return new DropboxAdaptor();
    }

    @Override
    public boolean deleteData(String path) {
        return false;
    }

    @Override
    public boolean pathOccupied(String path) {
        return false;
    }

    @Override
    public boolean uploadData(String path, byte[] data) {
        return false;
    }

    @Override
    public byte[] downloadData(String path) {
        return new byte[0];
    }

    @Override
    public boolean openConnection() {
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
            return false;
        }
        String accessToken = authFinish.getAccessToken();

        dropboxClient = new DbxClientV2(config, accessToken);

        return true;
    }

    @Override
    public boolean closeConnection() {
        return false;
    }

    public static String getName() {
        return NAME;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getDescription() {
        return DESCRIPTION;
    }
}
