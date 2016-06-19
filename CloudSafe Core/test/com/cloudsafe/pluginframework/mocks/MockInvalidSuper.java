package com.cloudsafe.pluginframework.mocks;

import com.cloudsafe.shared.Creatable;
import com.cloudsafe.shared.Registrable;

public class MockInvalidSuper implements Registrable, Creatable {

    private static final String NAME = "InvalidSuper";
    private static final String VERSION = "1.0.0";
    private static final String DESCRIPTION = "";

    public static MockInvalidSuper newInstance() {
        return new MockInvalidSuper();
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

}
