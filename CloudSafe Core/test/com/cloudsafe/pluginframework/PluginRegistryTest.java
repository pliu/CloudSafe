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

    // Does not implement getName, getVersion, getDescription, or newInstance methods
    @Test
    public void registerInvalid1() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid1.class));
    }

    // Does not extend AbstractMock
    @Test
    public void registerInvalid2() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid2.class));
    }

    // Implements non-static getName, getVersion, getDescription, and newInstance methods
    @Test
    public void registerInvalid3() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid3.class));
    }

    // Implements non-public getName, getVersion, getDescription, and newInstance methods
    @Test
    public void registerInvalid4() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid4.class));
    }

    // Name, version, and description are null
    @Test
    public void registerInvalid5() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid5.class));
    }

    // Name and version are empty Strings
    @Test
    public void registerInvalid6() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid6.class));
    }

    // getName, getVersion, and getDescription return non-Strings and newInstance returns a non-AbstractMock
    @Test
    public void registerInvalid7() throws Exception {
        assertFalse(storageRegistry.register(MockInvalid7.class));
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
    public void getNames() throws Exception {
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
    public void getBundles() throws Exception {
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