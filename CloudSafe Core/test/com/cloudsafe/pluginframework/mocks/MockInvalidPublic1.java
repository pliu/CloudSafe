package com.cloudsafe.pluginframework.mocks;

public class MockInvalidPublic1 extends AbstractMock {

    private static final String NAME = "InvalidPublic1";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    static MockInvalidPublic1 newInstance() {
        return new MockInvalidPublic1();
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
