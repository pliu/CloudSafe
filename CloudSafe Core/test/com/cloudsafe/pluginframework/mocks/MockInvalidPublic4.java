package com.cloudsafe.pluginframework.mocks;

public class MockInvalidPublic4 extends AbstractMock {

    private static final String NAME = "InvalidPublic4";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidPublic4 newInstance() {
        return new MockInvalidPublic4();
    }

    public static String getName() {
        return NAME;
    }

    public static String getVersion() {
        return VERSION;
    }

    static String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }

}
