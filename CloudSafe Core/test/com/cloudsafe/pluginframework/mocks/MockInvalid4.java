package com.cloudsafe.pluginframework.mocks;

public class MockInvalid4 extends AbstractMock {

    private static final String NAME = "Invalid4";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    static MockValid newInstance() {
        return new MockValid();
    }

    static String getName() {
        return NAME;
    }

    static String getVersion() {
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
