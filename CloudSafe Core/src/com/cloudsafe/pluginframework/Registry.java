package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A registry for storing Registrables, mapped by name and version.
 */
public abstract class Registry<T extends Registrable> {

    // A TreeMap mapping the name of the Registrables to a TreeMap mapping the versions of those Registrables to Bundles
    // of the registered classes.
    protected final TreeMap<String, TreeMap<String, Bundle<T>>> registry;

    // Maintains the class of the generic used to create the Registry (for runtime type-checking and casting to the
    // generic).
    protected final Class<T> tclass;

    protected Registry(Class<T> tclass) {
        registry = new TreeMap<>();
        this.tclass = tclass;
    }

    /**
     * Given a path, creates a Loader object and attempts to register classes from jars in the path.
     * @param path The String representing the path to the jars from which to register classes.
     */
    public final void loadPluginsFromDir(String path) {
        Loader loader = new Loader(this);
        loader.loadPluginsFromDir(path);
    }

    /**
     * Given a Class, attempts to register it. The implementation should check
     * @param klazz The Class to be registered.
     * @return Returns true if registration is successful, false otherwise.
     */
    public abstract boolean register(Class klazz);

    /**
     * Given a name and a version, returns an instance of the associated Registrable. The implementation should cast the
     * returned object to the generic's class.
     * @param name The String representing the name of the Registrable to get.
     * @param version The String representing the version of the Registrable to get.
     * @return Returns an instance of the Registrable with the corresponding name and version or null if not registered.
     */
    public abstract T get(String name, String version);

    /**
     * Removes all Registrables from the Registry.
     */
    public final void reset() {
        registry.clear();
    }

    /**
     *
     * @return Returns an Iterable (in ascending order) of Strings of the names of Registrables in the Registry.
     */
    public final Iterable<String> getNames() {
        return new TreeSet<>(registry.keySet());
    }

    /**
     *
     * @param name The String representing the name of the Registrables to get.
     * @return Returns an Iterable (in ascending order, ordered by version) of Bundles of the versions of Registrables
     * with the given name in the Registry.
     */
    public final Iterable<Bundle<T>> getBundles(String name) {
        TreeMap<String, Bundle<T>> versions = registry.get(name);
        if (versions != null) {
            return new TreeSet<>(versions.values());
        }
        return new TreeSet<>();
    }

    /**
     * Returns a Bundle
     */
    protected final Bundle isRegistrable(Class<T> klazz) {
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
                System.out.println("getVersion must return non-null String");
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
