package com.cloudsafe.pluginframework;

import com.cloudsafe.pluginframework.mocks.MockInvalidStorageAdaptor1;
import com.cloudsafe.pluginframework.mocks.MockInvalidStorageAdaptor2;
import com.cloudsafe.pluginframework.mocks.MockValidStorageAdaptor;
import com.cloudsafe.shared.AbstractStorageAdaptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PluginRegistryTest {

    private PluginRegistry<AbstractStorageAdaptor> storageRegistry = new PluginRegistry<>(AbstractStorageAdaptor.class);

    @Before
    public void setUp() throws Exception {
        storageRegistry.reset();
    }

    @Test
    public void registerValid() throws Exception {
        Assert.assertTrue(storageRegistry.register(MockValidStorageAdaptor.class));
    }

    @Test
    public void registerInvalid1() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor1.class));
    }

    @Test
    public void registerInvalid2() throws Exception {
        Assert.assertFalse(storageRegistry.register(MockInvalidStorageAdaptor2.class));

    }

}