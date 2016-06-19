package com.cloudsafe.pluginframework.mocks;

public class MockInvalidReturn3 extends AbstractMock {

    private static final String NAME = "InvalidReturn3";
    private static final String VERSION = "1.0.0";
    private static final Integer DESCRIPTION = 0;

    public static MockInvalidReturn3 newInstance() {
        return new MockInvalidReturn3();
    }

    public static String getName() {
        return NAME;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static Integer getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }
}
