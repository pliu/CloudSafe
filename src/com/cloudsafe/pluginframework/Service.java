package com.cloudsafe.pluginframework;

/**
 * A component of the simple plugin framework.
 *
 * Root (abstract) class of services. Classes that are loadable by ServiceLoader must extend this class.
 */
public abstract class Service {

    /**
     * All subclasses of Service must implement a static factory method with the following signature:
     * public static Service getInstance()
     * This method should return an instance of the extending class.
     */

}
