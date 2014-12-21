package com.cloudsafe.client;

public final class ImmutableBytes {
	
	private final byte[] bytes;
	
	public static final ImmutableBytes getInstance (byte[] bytes) {
		if (bytes == null) {
			Logger.log ("Bytes were null.");
			return null;
		}
		return new ImmutableBytes (bytes);
	}
	
	private ImmutableBytes (byte[] tBytes) {
		bytes = new byte [tBytes.length];
		System.arraycopy(bytes, 0, tBytes, 0, tBytes.length);
	}
	
	public final byte[] getBytes () {
		byte[] tBytes = new byte [bytes.length];
		System.arraycopy(bytes, 0, tBytes, 0, bytes.length);
		return tBytes;
	}
}
