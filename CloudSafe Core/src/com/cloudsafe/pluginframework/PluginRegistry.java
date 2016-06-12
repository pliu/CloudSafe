package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Creatable;
import com.cloudsafe.shared.Registrable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.TreeMap;

/**
 * A registry for storing plugins (Registrable and Creatable), mapped by name and version.
 */
public final class PluginRegistry<T extends Registrable & Creatable> extends Registry<T> {

    /**
     * @param tclass The class of the generic used to create the PluginRegistry.
     */
    public PluginRegistry(Class<T> tclass) {
        super(tclass);
    }

    /**
     *
     * @param klazz The Class to be registered.
     * @return Returns true if registration is successful, false otherwise.
     */
    @Override
    public boolean register(Class klazz) {
        if (!tclass.isAssignableFrom(klazz)) {
            System.out.println(klazz + " is not assignable to " + tclass);
            return false;
        }

        Bundle bundle = isRegistrable(klazz);
        if (bundle == null) {
            return false;
        }
        if (!isCreatable(klazz)) {
            return false;
        }

        String name = bundle.getName();
        TreeMap<String, Bundle<T>> versions;
        if (registry.containsKey(name)) {
            versions = registry.get(name);
        } else {
            versions = new TreeMap<>();
        }

        String version = bundle.getVersion();
        if (versions.containsKey(version)) {
            System.out.println(name + "-" + version + " already registered");
            return false;
        }
        versions.put(version, bundle);
        registry.put(name, versions);
        return true;
    }

    /**
     * Given a name and a version, returns an instance of the associated plugin.
     * @param name The String representing the name of the plugin to get.
     * @param version The String representing the version of the plugin to get.
     * @return Returns an instance of the plugin with the corresponding name and version or null if not registered.
     */
    @Override
    public T get(String name, String version) {
        TreeMap<String, Bundle<T>> versions = registry.get(name);
        if (versions == null) {
            return null;
        }
        Bundle<T> bundle = versions.get(version);
        if (bundle == null) {
            return null;
        }
        try {
            Method newInstance = bundle.getTClass().getDeclaredMethod(Creatable.NEW_INSTANCE);
            return (T) newInstance.invoke(null);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     *
     */
    private boolean isCreatable(Class<T> klazz) {
        try {
            Method newInstance = klazz.getDeclaredMethod(Creatable.NEW_INSTANCE);
            if (!Modifier.isStatic(newInstance.getModifiers())) {
                System.out.println("getDescription must be static");
                return false;
            }
            Object obj = newInstance.invoke(null);
            if (!klazz.isInstance(obj)) {
                System.out.println("newInstance must return a non-null instance of " + klazz);
                return false;
            }
            return true;
        } catch (NoSuchMethodException e) {
            System.out.println("Invalid implementation of Creatable: " + e);
            return false;
        } catch (IllegalAccessException e) {
            System.out.println("Must be public: " + e);
            return false;
        } catch (InvocationTargetException e) {
            System.out.println("Must be static: " + e);
            return false;
        }
    }

}
