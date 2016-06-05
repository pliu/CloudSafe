package com.cloudsafe.pluginframework.mocks;

import com.cloudsafe.shared.AbstractStorageAdaptor;

public class MockInvalidStorageAdaptor5 extends AbstractStorageAdaptor {

    private static final String NAME = "Valid";
    private static final String VERSION = null;
    private static final String DESCRIPTION = "";

    public static MockValidStorageAdaptor newInstance() {
        return new MockValidStorageAdaptor();
    }

    @Override
    public boolean deleteData(String path) {
        System.out.println("Deleted data");
        return true;
    }

    @Override
    public boolean pathOccupied(String path) {
        System.out.println("Path occupied");
        return true;
    }

    @Override
    public boolean uploadData(String path, byte[] data) {
        System.out.println("Uploaded data");
        return true;
    }

    @Override
    public byte[] downloadData(String path) {
        System.out.println("Downloaded data");
        return new byte[0];
    }

    @Override
    public boolean openConnection() {
        System.out.println("Connection opened");
        return true;
    }

    @Override
    public boolean closeConnection() {
        System.out.println("Connection closed");
        return true;
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
