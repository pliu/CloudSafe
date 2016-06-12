package com.cloudsafe.shared;

/**
 * The interface that must be implemented for PluginRegistry to be able to return an instance of the given plugin. The
 * validity of the expected static method is checked at registration by PluginRegistry.
 */
public interface Creatable {

    String NEW_INSTANCE = "newInstance";

    /**
     * @return Returns an instance of the concrete class that implements the Creatable interface
     */
    // public static Creatable newInstance();

}
