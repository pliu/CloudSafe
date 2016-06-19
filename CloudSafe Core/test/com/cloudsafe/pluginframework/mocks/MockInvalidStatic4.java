package com.cloudsafe.pluginframework.mocks;

public class MockInvalidStatic4 extends AbstractMock {

    private static final String NAME = "InvalidStatic4";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidStatic4 newInstance() {
        return new MockInvalidStatic4();
    }

    public static String getName() {
        return NAME;
    }

    public static String getVersion() {
        return VERSION;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }

}
