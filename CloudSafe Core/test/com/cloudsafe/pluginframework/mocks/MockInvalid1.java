package com.cloudsafe.pluginframework.mocks;

public class MockInvalid1 extends AbstractMock {

    private static final String NAME = "Invalid1";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }

}
