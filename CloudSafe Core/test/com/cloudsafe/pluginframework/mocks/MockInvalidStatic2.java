package com.cloudsafe.pluginframework.mocks;

public class MockInvalidStatic2 extends AbstractMock {

    private static final String NAME = "InvalidStatic2";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidStatic2 newInstance() {
        return new MockInvalidStatic2();
    }

    public String getName() {
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
