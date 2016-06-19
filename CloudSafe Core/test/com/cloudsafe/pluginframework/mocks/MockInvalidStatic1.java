package com.cloudsafe.pluginframework.mocks;

public class MockInvalidStatic1 extends AbstractMock {

    private static final String NAME = "InvalidStatic1";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public MockInvalidStatic1 newInstance() {
        return new MockInvalidStatic1();
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
