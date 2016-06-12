package com.cloudsafe.pluginframework.mocks;

public class MockInvalid2 {

    private static final String NAME = "Invalid2";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalid2 newInstance() {
        return new MockInvalid2();
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
