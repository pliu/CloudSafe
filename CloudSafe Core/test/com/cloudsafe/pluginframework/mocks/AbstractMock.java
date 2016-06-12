package com.cloudsafe.pluginframework.mocks;

import com.cloudsafe.shared.Creatable;
import com.cloudsafe.shared.Registrable;

public abstract class AbstractMock implements Registrable, Creatable {

    public abstract boolean testMethod();

}
