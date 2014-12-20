package com.cloudsafe.client;

import java.security.SecureRandom;

public class RandomByteGen {
	
	protected static final byte[] getRandomKey (int keyLength) {
		return generateKey (keyLength);
	}
	
	private static final byte[] generateKey (int keyLength) {
		SecureRandom rand = new SecureRandom();
		byte[] randBytes = new byte [keyLength/8];
		rand.nextBytes (randBytes);
		return randBytes;
	}
}
