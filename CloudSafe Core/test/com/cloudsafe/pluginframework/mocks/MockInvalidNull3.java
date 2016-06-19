package com.cloudsafe.pluginframework.mocks;

public class MockInvalidNull3 extends AbstractMock {

    private static final String NAME = "InvalidNull3";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = null;

    public static MockInvalidNull3 newInstance() {
        return new MockInvalidNull3();
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
