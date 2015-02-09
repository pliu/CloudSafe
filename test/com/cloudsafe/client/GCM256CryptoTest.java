package com.cloudsafe.client;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GCM256CryptoTest {

    public String passphrase = "This is a test.";
    public byte[] salt = "1234567812345678".getBytes();
    public byte[] validIV = "1234567812345678".getBytes();
    public byte[] validKey = ByteGenerator.getBytes (ByteGenerator.PBKDF2, 256, passphrase, salt,
            (int) Math.pow (2, 17));

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass: GCM256CryptoTest");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("@AfterClass: GCM256CryptoTest");
    }

    @Test
    public void decryptsCorrectly() throws Exception {
        SymmetricCrypto sc = SymmetricCrypto.getInstance (SymmetricCrypto.GCM256, validKey);
        byte[] ciphertext = sc.encrypt (passphrase.getBytes(), validIV);
        byte[] plaintext = sc.decrypt (ciphertext, validIV);
        Assert.assertEquals(passphrase, new String(plaintext, "UTF-8"));
    }

    @Test
    public void tamperProtection() throws Exception {
        SymmetricCrypto sc = SymmetricCrypto.getInstance (SymmetricCrypto.GCM256, validKey);
        byte[] ciphertext = sc.encrypt (passphrase.getBytes(), validIV);
        ciphertext[2] = 12;
        byte[] plaintext = sc.decrypt (ciphertext, validIV);
        Assert.assertEquals(SymmetricCrypto.TAMPERED, plaintext);
    }

    @Test
    public void incorrectKeyLength() throws Exception {
        byte[] invalidKey = "123456781234567".getBytes();
        SymmetricCrypto sc = SymmetricCrypto.getInstance (SymmetricCrypto.GCM256, invalidKey);
        Assert.assertEquals(null, sc);
    }
}