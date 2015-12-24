package com.cloudsafe.storageprovider;

import com.cloudsafe.pluginframework.Registerable;
import com.cloudsafe.pluginframework.Service;
import com.cloudsafe.utlities.Logger;
import com.dropbox.core.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Root (abstract) class for storage providers. Classes that are loadable by ServiceLoader must extend this class.
 */
public abstract class AbstractStorageProvider extends Service implements Registerable {

    /**
     *
     *
     * @param path
     * @return
     */
    public abstract boolean deleteFile (String path);

    /**
     *
     *
     * @param path
     * @return
     */
    public abstract boolean uploadFile (String path);

    /**
     *
     *
     * @param path
     * @return
     */
    public abstract boolean downloadFile (String path);

    /**
     *
     *
     * @return
     */
    public abstract boolean openConnection ();

    /**
     *
     *
     * @return
     */
    public abstract boolean closeConnection ();

}
