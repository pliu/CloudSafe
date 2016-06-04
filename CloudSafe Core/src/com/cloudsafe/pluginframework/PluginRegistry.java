package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Creatable;
import com.cloudsafe.shared.Registrable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Registry registers instances of Registrable and stores them in a HashMap, keyed by a String (the name of the
 * Registrable). If multiple Registrables have the same name, only one version is stored. Applications can retrieve
 * a list of the names of registered Registrables or the instance, the description, or version of a given Registrable.
 */
public final class PluginRegistry<T extends Registrable & Creatable> extends Registry<T> {

    public PluginRegistry(Class<T> tclass) {
        super(tclass);
    }

    @Override
    public boolean register(Class klazz) {
        if (!tclass.isAssignableFrom(klazz)) {
            System.out.println(klazz + " is not assignable to " + tclass);
            return false;
        }

        Method[] methods = isRegistrable(klazz);

        if (methods == null) {
            return false;
        }
        if (isCreatable(klazz) == null) {
            return false;
        }

        Method getName = methods[0];
        Method getVersion = methods[1];
        Method getDescription = methods[2];
        String name, version, description;

        try {
            name = (String) getName.invoke(null);
            version = (String) getVersion.invoke(null);
            description = (String) getDescription.invoke(null);
        }
        catch (IllegalAccessException e) {
            System.out.println("Illegal access: " + e);
            return false;
        }
        catch (InvocationTargetException e) {
            return false;
        }

        HashMap<String, Class<? extends Registrable>> versions;
        if (registry.containsKey(name)) {
            versions = registry.get(name);
        } else {
            versions = new HashMap<>();
        }

        if (versions.containsKey(version)) {
            System.out.println(name + "-" + version + " already registered");
            return false;
        }
        versions.put(version, klazz);
        registry.put(name, versions);
        return true;
    }

    // public T get

    private Method isCreatable(Class<T> klazz) {
        try {
            Method method = klazz.getDeclaredMethod(Creatable.NEW_INSTANCE);
            return method;
        }
        catch (NoSuchMethodException e) {
            System.out.println("Invalid implementation of Creatable: " + e);
            return null;
        }
    }

}
