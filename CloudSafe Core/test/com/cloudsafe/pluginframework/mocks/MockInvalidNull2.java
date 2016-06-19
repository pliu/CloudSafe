package com.cloudsafe.pluginframework.mocks;

public class MockInvalidNull2 extends AbstractMock {

    private static final String NAME = "InvalidNull2";
    private static final String VERSION = null;
    private static final String DESCRIPTION = "";

    public static MockInvalidNull2 newInstance() {
        return new MockInvalidNull2();
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
