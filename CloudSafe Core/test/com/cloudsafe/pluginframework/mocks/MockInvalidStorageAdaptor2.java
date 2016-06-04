package com.cloudsafe.pluginframework.mocks;

public class MockInvalidStorageAdaptor2 {

    private static final String NAME = "Invalid2";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockValidStorageAdaptor newInstance() {
        return new MockValidStorageAdaptor();
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
