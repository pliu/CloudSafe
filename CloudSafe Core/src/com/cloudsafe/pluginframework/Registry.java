package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 *
 */
public abstract class Registry<T extends Registrable> {

    protected final HashMap<String, HashMap<String, Class<? extends Registrable>>> registry;
    protected final Class<T> tclass;

    protected Registry(Class<T> tclass) {
        registry = new HashMap<>();
        this.tclass = tclass;
    }

    public abstract boolean register(Class<T> klazz);

    public void reset() {
        registry.clear();
    }

    public Iterable<String> getNames() {
        return registry.keySet();
    }

    protected final Method[] isRegistrable(Class<T> klazz) {
        try {
            Method[] methods = new Method[3];
            methods[0] = klazz.getDeclaredMethod(Registrable.GET_NAME);
            methods[1] = klazz.getDeclaredMethod(Registrable.GET_VERSION);
            methods[2] = klazz.getDeclaredMethod(Registrable.GET_DESCRIPTION);
            return methods;
        }
        catch (NoSuchMethodException e) {
            System.out.println("Invalid implementation of Registrable: " + e);
            return null;
        }
    }

}
