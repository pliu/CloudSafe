package com.cloudsafe.symmetriccrypto;

/**
 * Created by pengl on 1/20/2016.
 */
public final class AESGCM256 extends AbstractSymmetricCrypto {

    private static final String name = "aesgcm";
    private static final String description = "Symmetric encryption using AES in GCM mode.";
    private static final String version = "1.0.0";

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

}
