package com.cloudsafe.pluginframework;

import com.cloudsafe.utlities.Logger;

/**
 * A component of the simple plugin framework.
 *
 *
 */
public final class ServiceLoader {

    public static <T extends Service & Registerable> void loadServices(String path, Registry<T> registry) {

    }

}
