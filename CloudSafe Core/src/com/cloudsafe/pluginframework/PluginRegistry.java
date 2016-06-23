package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Creatable;
import com.cloudsafe.shared.Registrable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Registry for storing plugins (Registrable and Creatable), mapped by name and version.
 */
public final class PluginRegistry<T extends Registrable & Creatable> extends Registry<T> {

    /**
     * @param tclass The class of the generic used to create the PluginRegistry.
     */
    public PluginRegistry(Class<T> tclass) {
        super(tclass);
    }

    /**
     * Given a Class, attempts to register it.
     * Checks that the class is a subclass of the generic, tclass, and that it is satisfies the implicit interface of
     * Creatable before registering it through protectedRegister.
     * If multiple classes with the same associated name and version are registered, the first will succeed and the
     * subsequent ones will fail.
     *
     * @param klazz The Class to be registered.
     * @return Returns true if registration is successful, false otherwise.
     */
    @Override
    public boolean register(Class klazz) {
        if (klazz == null) {
            System.out.println("Registering a null class");
            return false;
        }
        if (!tclass.isAssignableFrom(klazz)) {
            System.out.println(klazz + " is not assignable to " + tclass);
            return false;
        }
        if (!isCreatable(klazz)) {
            return false;
        }
        return protectedRegister(klazz);
    }

    /**
     * Given a name and a version, returns an instance of the associated plugin.
     *
     * @param name    The String representing the name of the plugin to get.
     * @param version The String representing the version of the plugin to get.
     *                Relies on Register's protectedGet method to validate name and version.
     * @return Returns an instance of the plugin with the corresponding name and version or null if not registered.
     * Casting is safe as adherence to the plugin's type and interfaces is checked at registration.
     */
    public T get(String name, String version) {
        Bundle<T> bundle = protectedGet(name, version);
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
     * Given a Class implementing Creatable, validates that it contains the public static method newInstance and that it
     * returns an instance of itself.
     * This is required because Creatable cannot enforce the implementation of its static method (newInstance)
     * because there cannot be abstract static methods.
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
