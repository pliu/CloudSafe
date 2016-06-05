package com.cloudsafe.pluginframework;

import com.cloudsafe.pluginframework.mocks.*;
import com.cloudsafe.shared.AbstractStorageAdaptor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class PluginRegistryTest {

    private PluginRegistry<AbstractStorageAdaptor> storageRegistry = new PluginRegistry<>(AbstractStorageAdaptor.class);

    @Test
    public void registerValid() throws Exception {
        Assert.assertTrue(storageRegistry.register(MockValidStorageAdaptor.class));
        Set<String> names = (Set<String>) storageRegistry.getNames();
        Assert.assertEquals(names.size(), 1);
        String name = (String) names.toArray()[0];
        Assert.assertEquals(name, "Valid");
        Iterable<Bundle<AbstractStorageAdaptor>> bundles = storageRegistry.getBundles(name);
        Bundle bundle = bundles.iterator().next();
        Assert.assertEquals(bundle.getVersion(), "1.0.0");
    }

    /**
     * Does not implement a newInstance method
     */
    @Test
    public void registerInvalid1() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor1.class));
    }

    /**
     * Does not extend AbstractStorageAdaptor
     */
    @Test
    public void registerInvalid2() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor2.class));
    }

    /**
     * Implements a non-static getDescription method
     */
    @Test
    public void registerInvalid3() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor3.class));
    }

    /**
     * Implements a non-public getVersion method
     */
    @Test
    public void registerInvalid4() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor4.class));
    }

    /**
     * Version is null
     */
    @Test
    public void registerInvalid5() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor5.class));
    }

    @Test
    public void get() {
        storageRegistry.register(MockValidStorageAdaptor.class);
        AbstractStorageAdaptor adaptor = storageRegistry.get("Valid", "1.0.0");
        Assert.assertTrue(adaptor.deleteData(""));
    }

    /**
     * Tests
     */
    @Test
    public void loadPluginsFromDir() throws Exception {
        storageRegistry.loadPluginsFromDir("D:\\Work\\Programming\\CloudSafe\\test");
        Set<String> names = (Set<String>) storageRegistry.getNames();
        Assert.assertEquals(names.size(), 1);
        String name = (String) names.toArray()[0];
        Assert.assertEquals(name, "ValidJar");
        Iterable<Bundle<AbstractStorageAdaptor>> bundles = storageRegistry.getBundles(name);
        Bundle bundle = bundles.iterator().next();
        Assert.assertEquals(bundle.getVersion(), "1.0.0");
    }

}