package com.cloudsafe.pluginframework;

import java.util.HashMap;

/**
 * A component of the simple plugin framework.
 *
 * Registry registers instances of Registerable and stores them in a HashMap, keyed by a String (the name of the
 * Registerable). If multiple Registerables have the same name, only one version is stored. Applications can retrieve
 * a list of the names of registered Registerables or the instance, the description, or version of a given Registerable.
 *
 * Considerations:
 * - the validity of Registerables and other higher-level logic should be handled by a control class that composes
 *   Registry (e.g.: ServiceLoader)
 *
 * Next Steps:
 * - store different versions of a given Registerable
 */
public final class Registry<T extends Registerable> {

    private final HashMap<String, T> registry;

    public Registry() {
        registry = new HashMap<>();
    }

    /**
     * Registers an instance of a given Registerable, keyed by its name, in the HashMap. If the Registerable's name is
     * null or blank, registration fails. If a Registerable with the same name is already registered, the old
     * Registerable is overwritten.
     *
     * @param service The Registerable to be registered.
     * @return Returns true if the Registerable is successfully registered; false otherwise.
     */
    public boolean register(T service) {
        if (service == null || service.getName() == null || service.getName() == "") {
            return false;
        }
        registry.put(service.getName(), service);
        return true;
    }

    /**
     * Returns the instance of the Registerable with the given name, if there is one registered.
     *
     * @param name The name of the Registerable to be returned.
     * @return Returns the instance of the Registerable if it is registered; null otherwise.
     */
    public T getRegistrable(String name) {
        if (name == null || name == "") {
            return null;
        }
        return registry.get(name);
    }

    /**
     * @return Returns an Iterable of Strings containing the names of registered Registerables.
     */
    public Iterable<String> getNames() {
        return registry.keySet();
    }

}
