package com.cloudsafe.client;

import org.junit.*;

public class SymmetricCryptoTest {

    public String passphrase = "This is a test.";
    public byte[] salt = "1234567812345678".getBytes();
    public byte[] validKey = ByteGenerator.getBytes (ByteGenerator.PBKDF2, 256, passphrase, salt,
            (int) Math.pow (2, 17));

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass: SymmetricCryptoTest");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("@AfterClass: SymmetricCryptoTest");
    }

    @Test
    public void invalidAlgorithm() throws Exception {
        SymmetricCrypto sc = SymmetricCrypto.getInstance("GCM255", validKey);
        Assert.assertEquals(null, sc);
    }
}