package com.cloudsafe.pluginframework.mocks;

public class MockInvalidReturn2 extends AbstractMock {

    private static final String NAME = "InvalidReturn2";
    private static final Integer VERSION = 0;
    private static final String DESCRIPTION = "";

    public static MockInvalidReturn2 newInstance() {
        return new MockInvalidReturn2();
    }

    public static String getName() {
        return NAME;
    }

    public static Integer getVersion() {
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
