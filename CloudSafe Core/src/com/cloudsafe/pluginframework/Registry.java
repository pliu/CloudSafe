package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Registry for storing Registrables, mapped by name and version.
 */
public abstract class Registry<T extends Registrable> {

    // A TreeMap mapping the name of the Registrables to a TreeMap mapping the versions of those Registrables to Bundles
    // of the registered classes.
    private final TreeMap<String, TreeMap<String, Bundle<T>>> registry;

    // Maintains the class of the generic used to create the Registry (for runtime type-checking and casting to the
    // generic).
    protected final Class<T> tclass;

    protected Registry(Class<T> tclass) {
        registry = new TreeMap<>();
        this.tclass = tclass;
    }

    /**
     * Given a Class, attempts to register it.
     * The implementation should check that the class is a subclass of the generic, tclass, that it satisfies other
     * interfaces' implicit interfaces, and use protectedRegister for registration.
     *
     * @param klazz The Class to be registered.
     * @return Returns true if registration is successful, false otherwise.
     */
    public abstract boolean register(Class klazz);

    /**
     * Given a path, creates a Loader object and attempts to register classes from jars in the path.
     *
     * @param path The String representing the path to the jars from which to register classes.
     */
    public final void loadPluginsFromDir(String path) {
        Loader loader = new Loader(this);
        loader.loadPluginsFromDir(path);
    }

    /**
     * Removes all Registrables from the Registry.
     */
    public final void reset() {
        registry.clear();
    }

    /**
     * Returns the names of Registrables in the Registry.
     *
     * @return Returns an Iterable (in ascending order) of Strings of the names of Registrables in the Registry.
     * Mutating the returned Iterable does not affect the Registry.
     */
    public final Iterable<String> getNames() {
        return new TreeSet<>(registry.keySet());
    }

    /**
     * Returns Bundles of Registrables with the given name in the Registry.
     *
     * @param name The String representing the name of the Registrables to get.
     * @return Returns an Iterable (in ascending order, ordered by version) of Bundles of Registrables with the given
     * name in the Registry. Mutating the returned Iterable does not affect the Registry.
     */
    public final Iterable<Bundle<T>> getBundles(String name) {
        TreeMap<String, Bundle<T>> versions = registry.get(name);
        if (versions != null) {
            return new TreeSet<>(versions.values());
        }
        return new TreeSet<>();
    }

    /**
     * Given a Registrable, validates that it implements the implicit interface and then attempts to register it.
     * If multiple Registrables with the same associated name and version are registered, the first will succeed and the
     * subsequent ones will fail.
     * Classes extending Registry should use this method to register Registrables into the Registry.
     *
     * @param klazz The Class to be registered.
     * @return Returns true if registration is successful, false otherwise.
     */
    protected final boolean protectedRegister(Class<T> klazz) {
        Bundle bundle = isRegistrable(klazz);
        if (bundle == null) {
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
     * Given a name and a version, returns the Bundle containing the associated Registrable.
     * Classes extending Registry should use this method to access Registrables in the Registry.
     *
     * @param name    The String representing the name of the Registrable to get.
     * @param version The String representing the version of the Registrable to get.
     * @return Returns the Bundle containing the Registrable with the corresponding name and version or null if not
     * registered.
     */
    protected final Bundle<T> protectedGet(String name, String version) {
        TreeMap<String, Bundle<T>> versions = registry.get(name);
        if (versions == null) {
            return null;
        }
        return versions.get(version);
    }

    /**
     * Given a Class implementing Registrable, validates that it contains the public static methods getName, getVersion,
     * and getDescription and that they return non-null (and in the case of getName and getVersion, also non-empty)
     * Strings. If validated, returns a Bundle containing the Registrable and its metadata; null otherwise.
     * This is required because Registrable cannot enforce the implementation of its three static methods (getName,
     * getVersion, and getDescription) because there cannot be abstract static methods.
     */
    private Bundle isRegistrable(Class<T> klazz) {
        try {
            Method getName = klazz.getDeclaredMethod(Registrable.GET_NAME);
            Method getVersion = klazz.getDeclaredMethod(Registrable.GET_VERSION);
            Method getDescription = klazz.getDeclaredMethod(Registrable.GET_DESCRIPTION);

            if (!Modifier.isStatic(getName.getModifiers())) {
                System.out.println("getName must be static");
                return null;
            }
            if (!Modifier.isStatic(getVersion.getModifiers())) {
                System.out.println("getVersion must be static");
                return null;
            }
            if (!Modifier.isStatic(getDescription.getModifiers())) {
                System.out.println("getDescription must be static");
                return null;
            }

            Object obj = getName.invoke(null);
            if (!String.class.isInstance(obj)) {
                System.out.println("getName must return non-null String");
                return null;
            }
            String name = (String) obj;
            if (name.equals("")) {
                System.out.println("getName must return a non-empty String");
                return null;
            }
            obj = getVersion.invoke(null);
            if (!String.class.isInstance(obj)) {
                System.out.println("getVersion must return non-null String");
                return null;
            }
            String version = (String) obj;
            if (version.equals("")) {
                System.out.println("getVersion must return a non-empty String");
                return null;
            }
            obj = getDescription.invoke(null);
            if (!String.class.isInstance(obj)) {
                System.out.println("getDescription must return non-null String");
                return null;
            }
            String description = (String) obj;
            return new Bundle<>(klazz, name, version, description);
        } catch (NoSuchMethodException e) {
            System.out.println("Invalid implementation of Registrable: " + e);
            return null;
        } catch (IllegalAccessException e) {
            System.out.println("Must be public: " + e);
            return null;
        } catch (InvocationTargetException e) {
            System.out.println(e);
            return null;
        }
    }

}
