package com.cloudsafe.plugins.storage;

import com.cloudsafe.shared.AbstractStorageAdaptor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Storage adaptor for storing data to the local file system.
 */
public class FileSystemAdaptor extends AbstractStorageAdaptor {

    /**
     * Deletes the data at the given path.
     *
     * @param path The path at which data is deleted.
     * @return Returns true if the data is successfully deleted and false otherwise.
     */
    @Override
    public boolean deleteData(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    /**
     * Checks if the path is occupied.
     *
     * @param path The path to be checked.
     * @return Returns true if the path is already occupied and false otherwise.
     */
    @Override
    public boolean pathOccupied(String path) {
        return new File(path).exists();
    }

    /**
     * Uploads the data to the given path.
     *
     * @param path The path at which to upload data.
     * @param data A byte[] representation of the data to upload.
     * @return Returns true if the data is successfully uploaded to the path or false otherwise.
     */
    @Override
    public boolean uploadData(String path, byte[] data) {
        if (pathOccupied(path)) {
            return false;
        }
        File file = new File(path);
        try {
            FileUtils.writeByteArrayToFile(file, data);
            return true;
        } catch (IOException e) {
            file.delete();
            return false;
        }
    }

    /**
     * Downloads the data at the given path.
     *
     * @param path The path from which to download the data from.
     * @return Returns the data as a byte[] array if successful or null if the path is inaccessible.
     */
    @Override
    public byte[] downloadData(String path) {
        try {
            return FileUtils.readFileToByteArray(new File(path));
        } catch (IOException e) {
            return null;
        }

    }

    /**
     * Always returns true as there is nothing to setup.
     */
    @Override
    public boolean setup() {
        return true;
    }

    /**
     * Always returns true as there is nothing to clean up.
     */
    @Override
    public boolean cleanup() {
        return true;
    }

}
