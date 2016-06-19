package com.cloudsafe.pluginframework.mocks;

public class MockInvalidReturn4 extends AbstractMock {

    private static final String NAME = "InvalidReturn4";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static String newInstance() {
        return "GG";
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
