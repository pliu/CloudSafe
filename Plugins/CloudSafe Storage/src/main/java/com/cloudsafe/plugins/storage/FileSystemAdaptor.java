package com.cloudsafe.plugins.storage;

import com.cloudsafe.shared.AbstractStorageAdaptor;

/**
 *
 */
public class FileSystemAdaptor extends AbstractStorageAdaptor {

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
        return false;
    }

    @Override
    public boolean closeConnection() {
        return false;
    }

}
