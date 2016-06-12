package com.cloudsafe;

import com.cloudsafe.pluginframework.PluginRegistryTest;
import com.cloudsafe.shared.ImmutableBytesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PluginRegistryTest.class,
        ImmutableBytesTest.class,
})
public class CloudSafeCoreTestSuite {
}
