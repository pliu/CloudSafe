package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

/**
 * The unit of registration in a Registry. Bundles relevant information (class, name, version, and description) into a
 * single object. Comparable by version.
 */
public final class Bundle<T extends Registrable> implements Comparable<Bundle<T>> {

    private final Class<T> tclass;
    private final String name, version, description;

    /**
     * The constructor is package local as Bundles should only be created by the Registry upon successful registration.
     */
    Bundle(Class<T> tclass, String name, String version, String description) {
        this.tclass = tclass;
        this.name = name;
        this.version = version;
        this.description = description;
    }

    /**
     * getTClass is package local as reflection on the Registrable (e.g.: instantiation) is handled by the Registry.
     */
    Class<T> getTClass() {
        return tclass;
    }

    /**
     * Returns the name associated with the bundled Registrable.
     * @return Returns a non-null, non-empty String (verified by the Registry at registration).
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the version associated with the bundled Registrable.
     * @return Returns a non-null, non-empty String (verified by the Registry at registration).
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns the description associated with the bundled Registrable.
     * @return Returns a non-null String (verified by the Registry at registration).
     */
    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Bundle<T> bundle) {
        return this.version.compareTo(bundle.version);
    }

}
