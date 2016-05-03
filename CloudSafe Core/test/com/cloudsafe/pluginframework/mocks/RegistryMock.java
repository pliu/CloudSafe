package com.cloudsafe.pluginframework.mocks;

import com.cloudsafe.pluginframework.Registry;
import com.cloudsafe.shared.Registrable;

import java.util.Set;

/**
 * Created by pengl on 5/29/2016.
 */
public class RegistryMock<T extends Registrable> extends Registry<T> {

    public RegistryMock(Class<T> tclass) {
        super(tclass);
    }

    @Override
    public boolean register(Object obj) {
        T service;

        if (tclass.isInstance(obj)) {
            service = (T) obj;
        } else {
            System.out.println(obj.getClass() + " is not an instance of the correct template");
            return false;
        }

        registry.put(service.getName(), null);
        return true;
    }

    public Set<String> getKeys () {
        return registry.keySet();
    }
}
