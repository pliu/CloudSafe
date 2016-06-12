package com.cloudsafe.shared;

/**
 * The interface that must be implemented for Registry to register a given class. The validity of the expected static
 * methods is checked at registration by Registry.
 */
public interface Registrable {

    String GET_NAME = "getName";
    String GET_VERSION = "getVersion";
    String GET_DESCRIPTION = "getDescription";

    /**
     * @return Returns a non-null, non-empty String containing the name associated to the Registrable.
     */
    // public static String getName();

    /**
     * @return Returns a non-null String containing the version associated to the Registrable.
     */
    // public static String getVersion();

    /**
     * @return Returns a non-null String containing the description associated to the Registrable.
     */
    // public static String getDescription();

}
