package com.cloudsafe;

import com.cloudsafe.data.FileMetadataTest;
import com.cloudsafe.pluginframework.PluginRegistryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PluginRegistryTest.class,
        FileMetadataTest.class,
})
public class CloudSafeCoreTestSuite {
}
