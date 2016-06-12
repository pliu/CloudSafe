package com.cloudsafe.pluginframework.mocks;

public class MockInvalid7 extends AbstractMock {

    private static final Integer NAME = 0;
    private static final Integer VERSION = 0;
    private static final Integer DESCRIPTION = 0;

    public static String newInstance() {
        return "GG";
    }

    public static Integer getName() {
        return NAME;
    }

    public static Integer getVersion() {
        return VERSION;
    }

    public static Integer getDescription() {
        return DESCRIPTION;
    }

    @Override
    public boolean testMethod() {
        System.out.println("Success!");
        return true;
    }
}
