package com.cloudsafe.pluginframework.mocks;

public class MockValid extends AbstractMock {

    private static final String NAME = "Valid";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockValid newInstance() {
        return new MockValid();
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

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }
}
