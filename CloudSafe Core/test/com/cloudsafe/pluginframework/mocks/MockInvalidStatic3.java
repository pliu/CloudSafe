package com.cloudsafe.pluginframework.mocks;

public class MockInvalidStatic3 extends AbstractMock {

    private static final String NAME = "InvalidStatic3";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidStatic3 newInstance() {
        return new MockInvalidStatic3();
    }

    public static String getName() {
        return NAME;
    }

    public String getVersion() {
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
