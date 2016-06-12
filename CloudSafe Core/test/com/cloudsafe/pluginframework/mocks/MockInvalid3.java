package com.cloudsafe.pluginframework.mocks;

public class MockInvalid3 extends AbstractMock {

    private static final String NAME = "Invalid3";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public MockInvalid3 newInstance() {
        return new MockInvalid3();
    }

    public String getName() {
        return NAME;
    }

    public String getVersion() {
        return VERSION;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }

}
