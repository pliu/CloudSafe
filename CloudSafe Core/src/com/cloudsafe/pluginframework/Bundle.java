package com.cloudsafe.pluginframework;

import com.cloudsafe.shared.Registrable;

/**
 *
 */
public final class Bundle<T extends Registrable> implements Comparable<Bundle> {

    private final Class<T> tclass;
    private final String name, version, description;

    protected Bundle(Class<T> tclass, String name, String version, String description) {
        this.tclass = tclass;
        this.name = name;
        this.version = version;
        this.description = description;
    }

    protected Class<T> getTClass() {
        return tclass;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Bundle bundle) {
        return this.version.compareTo(bundle.version);
    }

}
