package com.cloudsafe.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SymmetricCryptoTest.class,
        CloudConnectionTest.class,
        GCM256CryptoTest.class
})
public class CloudSafeTestSuite {

}