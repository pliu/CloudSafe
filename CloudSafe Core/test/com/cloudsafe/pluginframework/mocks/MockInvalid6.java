package com.cloudsafe.pluginframework.mocks;

public class MockInvalid6 extends AbstractMock {

    private static final String NAME = "";
    private static final String VERSION = "";
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
