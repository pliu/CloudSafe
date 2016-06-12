package com.cloudsafe.pluginframework.mocks;

public class MockInvalid5 extends AbstractMock {

    private static final String NAME = null;
    private static final String VERSION = null;
    private static final String DESCRIPTION = null;

    public static MockInvalid5 newInstance() {
        return new MockInvalid5();
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
