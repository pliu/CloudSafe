package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 */
public abstract class Registry<T extends Registrable> {

    protected final TreeMap<String, TreeMap<String, Bundle<T>>> registry;
    protected final Class<T> tclass;

    protected Registry(Class<T> tclass) {
        registry = new TreeMap<>();
        this.tclass = tclass;
    }

    public final void loadPluginsFromDir(String path) {
        PluginLoader loader = new PluginLoader(this);
        loader.loadPluginsFromDir(path);
    }

    public abstract boolean register(Class klazz);

    public abstract T get(String name, String version);

    public final void reset() {
        registry.clear();
    }

    public final Iterable<String> getNames() {
        return registry.keySet();
    }

    public final Iterable<Bundle<T>> getBundles(String name) {
        TreeMap<String, Bundle<T>> versions = registry.get(name);
        if (versions != null) {
            return versions.values();
        }
        return new ArrayList<>();
    }

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
