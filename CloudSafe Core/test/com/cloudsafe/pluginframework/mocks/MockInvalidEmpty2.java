package com.cloudsafe.pluginframework.mocks;

public class MockInvalidEmpty2 extends AbstractMock {

    private static final String NAME = "InvalidEmpty2";
    private static final String VERSION = "";
    private static final String DESCRIPTION = "";

    public static MockInvalidEmpty2 newInstance() {
        return new MockInvalidEmpty2();
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
