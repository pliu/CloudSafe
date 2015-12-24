package com.cloudsafe.pluginframework;

/**
 * A component of the simple plugin framework.
 *
 * Root interface all classes registerable in Registry must implement.
 */
public interface Registerable {

    /**
     * @return Returns a non-null, non-empty String containing the name of the Registerable.
     */
    public String getName();

    /**
     * @return Returns a non-null String containing the version of the Registerable.
     */
    public String getVersion();

    /**
     * @return Returns a non-null String containing the description of the Registerable.
     */
    public String getDescription();
}
