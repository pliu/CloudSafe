package com.cloudsafe.pluginframework;

import com.cloudsafe.pluginframework.mocks.*;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Collection;
import java.util.Set;

public class PluginRegistryTest {

    private PluginRegistry<AbstractMock> storageRegistry = new PluginRegistry<>(AbstractMock.class);

    @Test
    public void registerValid() throws Exception {
        assertTrue(storageRegistry.register(MockValid.class));
        Set<String> names = (Set<String>) storageRegistry.getNames();
        assertEquals(1, names.size());
        String name = (String) names.toArray()[0];
        assertEquals("Valid", name);
        Iterable<Bundle<AbstractMock>> bundles = storageRegistry.getBundles(name);
        Bundle bundle = bundles.iterator().next();
        assertEquals("1.0.0", bundle.getVersion());
    }

    // Does not extend AbstractMock (but does implement Registrable and Creatable)
    @Test
    public void registerInvalidSuper() throws Exception {
        assertFalse(storageRegistry.register(MockInvalidSuper.class));
    }

    // Implements non-static getName, getVersion, getDescription, and newInstance methods
    @Test
    public void registerInvalidStatic() throws Exception {
        assertFalse(storageRegistry.register(MockInvalidStatic1.class));
        assertFalse(storageRegistry.register(MockInvalidStatic2.class));
        assertFalse(storageRegistry.register(MockInvalidStatic3.class));
        assertFalse(storageRegistry.register(MockInvalidStatic4.class));
    }

    // Implements non-public getName, getVersion, getDescription, and newInstance methods
    @Test
    public void registerInvalidPublic() throws Exception {
        assertFalse(storageRegistry.register(MockInvalidPublic1.class));
        assertFalse(storageRegistry.register(MockInvalidPublic2.class));
        assertFalse(storageRegistry.register(MockInvalidPublic3.class));
        assertFalse(storageRegistry.register(MockInvalidPublic4.class));
    }

    // Name, version, and description are null
    @Test
    public void registerInvalidNull() throws Exception {
        assertFalse(storageRegistry.register(MockInvalidNull1.class));
        assertFalse(storageRegistry.register(MockInvalidNull2.class));
        assertFalse(storageRegistry.register(MockInvalidNull3.class));
    }

    // Name and version are empty Strings
    @Test
    public void registerInvalidEmpty() throws Exception {
        assertFalse(storageRegistry.register(MockInvalidEmpty1.class));
        assertFalse(storageRegistry.register(MockInvalidEmpty2.class));
    }

    // getName, getVersion, and getDescription return non-Strings and newInstance returns a non-AbstractMock
    @Test
    public void registerInvalidReturn() throws Exception {
        assertFalse(storageRegistry.register(MockInvalidReturn1.class));
        assertFalse(storageRegistry.register(MockInvalidReturn2.class));
        assertFalse(storageRegistry.register(MockInvalidReturn3.class));
        assertFalse(storageRegistry.register(MockInvalidReturn4.class));
    }

    // Tests whether PluginLoader and PluginRegistry can successfully load valid plugins (and exclude missing or invalid
    // plugins) from jars in a directory using the jar's MANIFEST.MF to indicate which classes to load
    @Test
    public void loadPluginsFromDir() throws Exception {
        storageRegistry.loadPluginsFromDir("D:\\Work\\Programming\\CloudSafe\\test");
        Set<String> names = (Set<String>) storageRegistry.getNames();
        assertEquals(1, names.size());
        String name = (String) names.toArray()[0];
        assertEquals("Valid", name);
        Iterable<Bundle<AbstractMock>> bundles = storageRegistry.getBundles(name);
        Bundle bundle = bundles.iterator().next();
        assertEquals("1.0.0", bundle.getVersion());
    }

    @Test
    public void get() throws Exception {
        storageRegistry.loadPluginsFromDir("D:\\Work\\Programming\\CloudSafe\\test");
        AbstractMock test = storageRegistry.get("Valid", "1.0.0");
        assertTrue(test.testMethod());
    }

    // Tests registry isolation from the Iterable returned by getNames
    @Test
    public void immutableGetNames() throws Exception {
        storageRegistry.register(MockValid.class);
        Set<String> names = (Set<String>) storageRegistry.getNames();
        assertEquals(1, names.size());
        for (String name : names) {
            names.remove(name);
        }
        names = (Set<String>) storageRegistry.getNames();
        assertEquals(1, names.size());
    }

    // Tests registry isolation from the Iterable returned by getVersions
    @Test
    public void immutableGetBundles() throws Exception {
        storageRegistry.register(MockValid.class);
        Collection<Bundle<AbstractMock>> values = (Collection<Bundle<AbstractMock>>) storageRegistry.getBundles("Valid");
        assertEquals(1, values.size());
        for (Bundle<AbstractMock> value : values) {
            values.remove(value);
        }
        values = (Collection<Bundle<AbstractMock>>) storageRegistry.getBundles("Valid");
        assertEquals(1, values.size());
    }

    // TODO: Test loading of multiple versions of the same class

}