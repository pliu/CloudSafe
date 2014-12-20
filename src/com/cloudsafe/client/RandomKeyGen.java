package com.cloudsafe.client;

import java.security.SecureRandom;

public class RandomKeyGen {
	
	public static final Key getRandomKey (int keyLength) {
		return new Key (generateKey (keyLength));
	}
	
	private static final byte[] generateKey (int keyLength) {
		SecureRandom rand = new SecureRandom();
		byte[] randBytes = new byte [keyLength];
		rand.nextBytes (randBytes);
		return randBytes;
	}
}
