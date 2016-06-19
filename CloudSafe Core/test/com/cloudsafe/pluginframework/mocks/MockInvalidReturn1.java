package com.cloudsafe.pluginframework.mocks;

public class MockInvalidReturn1 extends AbstractMock {

    private static final Integer NAME = 0;
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidReturn1 newInstance() {
        return new MockInvalidReturn1();
    }

    public static Integer getName() {
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
