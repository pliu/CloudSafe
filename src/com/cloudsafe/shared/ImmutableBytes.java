package com.cloudsafe.shared;

import java.io.Serializable;

public final class ImmutableBytes implements Serializable {
	
	private static final long serialVersionUID = 0L;
	
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
		System.arraycopy(tBytes, 0, bytes, 0, tBytes.length);
	}
	
	public final byte[] getBytes () {
		byte[] tBytes = new byte [bytes.length];
		System.arraycopy(bytes, 0, tBytes, 0, bytes.length);
		return tBytes;
	}
	
	public static void main (String[] args) throws Exception {
		//Testing byte[] and String immutability
		byte[] a = "123".getBytes();
		byte[] b = a;
		b[1] = 12;
		System.out.println(new String (a, "UTF-8"));
		
		String c = "abc";
		String d = c;
		d = d + "!";
		System.out.println(c+ d);
	}
}
