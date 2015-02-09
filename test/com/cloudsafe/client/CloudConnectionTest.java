package com.cloudsafe.client;

import org.junit.*;
import org.junit.rules.ExpectedException;

public class CloudConnectionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass: CloudConnectionTest");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("@AfterClass: CloudConnectionTest");
    }

    @Test
    public void CloudConnectionValid() throws Exception {
        //CloudConnection cc = CloudConnection.getInstance(CloudConnection.DROPBOX, null);
        //cc.uploadFile("D:/Downloads/qrcode.png");
    }
}