package com.cloudsafe.shared;

/**
 * A component of the simple plugin framework.
 *
 * Root interface all classes registrable in Registry must implement.
 */
public interface Registrable {

    /**
     * @return Returns a non-null, non-empty String containing the name of the Registrable.
     */
    public String getName();

    /**
     * @return Returns a non-null String containing the version of the Registrable.
     */
    public String getVersion();

    /**
     * @return Returns a non-null String containing the description of the Registrable.
     */
    public String getDescription();
}
