package com.cloudsafe.test;

import com.cloudsafe.client.CloudConnection;

public final class CloudConnectionTest {

	private static CloudConnection cc;
	
	private static final void dropboxTest () {
		 cc = CloudConnection.getInstance(CloudConnection.DROPBOX, null);
		 cc.uploadFile("D:/Downloads/qrcode.png");
	}
	
	public static void main (String[] args) {
		dropboxTest();
	}
}
