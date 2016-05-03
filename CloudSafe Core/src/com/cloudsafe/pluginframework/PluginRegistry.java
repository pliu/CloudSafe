package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

import java.util.HashMap;

/**
 * A component of the simple plugin framework.
 *
 * Registry registers instances of Registrable and stores them in a HashMap, keyed by a String (the name of the
 * Registrable). If multiple Registrables have the same name, only one version is stored. Applications can retrieve
 * a list of the names of registered Registrables or the instance, the description, or version of a given Registrable.
 *
 * Considerations:
 * - the validity of Registrables and other higher-level logic should be handled by a control class that composes
 *   Registry (e.g.: ServiceLoader)
 *
 * Next Steps:
 * - store different versions of a given Registrable
 */
public final class PluginRegistry<T extends Registrable> extends Registry<T> {

    public PluginRegistry(Class<T> tclass) {
        super(tclass);
    }

    /**
     * Registers an instance of a given Registrable, keyed by its name, in the HashMap. If the Registrable's name is
     * null or blank, registration fails. If a Registrable with the same name is already registered, the old
     * Registrable is overwritten.
     *
     * @param obj The Registrable to be registered.
     * @return Returns true if the Registrable is successfully registered; false otherwise.
     */
    @Override
    public boolean register(Object obj) {
        T service;

        if (tclass.isInstance(obj)) {
            service = (T) obj;
        } else {
            System.out.println(obj.getClass() + " is not an instance of the correct template");
            return false;
        }
        if (service == null || service.getName() == null || service.getName().equals("")) {
            return false;
        }

        if (registry.containsKey(service.getName())) {
            HashMap<String, Class<? extends Registrable>> versions = registry.get(service.getName());
        } else {
            HashMap<String, Class<? extends Registrable>> versions = new HashMap<>();
            versions.put(service.getVersion(), service.getClass());
            registry.put(service.getName(), versions);
        }
        return true;
    }

    // public T get

    /**
     * @return Returns an Iterable of Strings containing the names of registered Registrables.
     */
    public Iterable<String> getNames() {
        return registry.keySet();
    }

    private static <E> boolean hasNameandVersion(E service) {
        return true;
    }

}
