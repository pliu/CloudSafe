package com.cloudsafe.pluginframework.mocks;

public class MockInvalidPublic3 extends AbstractMock {

    private static final String NAME = "InvalidPublic3";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidPublic3 newInstance() {
        return new MockInvalidPublic3();
    }

    public static String getName() {
        return NAME;
    }

    static String getVersion() {
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
