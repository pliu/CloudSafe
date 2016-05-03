package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

import java.util.HashMap;

/**
 * Created by pengl on 5/29/2016.
 */
public abstract class Registry<T extends Registrable> {

    protected final HashMap<String, HashMap<String, Class<? extends Registrable>>> registry;
    protected final Class<T> tclass;

    protected Registry(Class<T> tclass) {
        registry = new HashMap<>();
        this.tclass = tclass;
    }

    public abstract boolean register(Object service);

    public void reset() {
        registry.clear();
    }

}
