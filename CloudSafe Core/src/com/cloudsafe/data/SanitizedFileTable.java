package com.cloudsafe.data;

import com.cloudsafe.shared.ImmutableBytes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class SanitizedFileTable implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private final String keyGenAlg;
	private final ImmutableBytes salt;
	private final int iterations;
	private final String encAlg;
	private final ImmutableBytes IV;
	private final Hashtable<Long, ImmutableBytes> encFileTable = new Hashtable<>();
	
	public static SanitizedFileTable getInstance (String keyGenAlg, byte[] salt, int iterations,
			String encAlg, byte[] IV) {
		if (keyGenAlg == null) {
			// Logger.log("Key generation algorithm was null.");
			return null;
		}
		if (salt == null) {
			// Logger.log ("Salt was null.");
			return null;
		}
		if (iterations < 1) {
			// Logger.log ("Iterations input was " + iterations + " (iterations must be greater than 1).");
			return null;
		}
		if (encAlg == null) {
			// Logger.log ("Encryption algorithm was null.");
			return null;
		}
		if (IV == null) {
			// Logger.log ("IV was null.");
			return null;
		}
		
		return new SanitizedFileTable (keyGenAlg, salt, iterations, encAlg, IV);
	}
	
	protected SanitizedFileTable (String keyGenAlg, byte[] salt, int iterations, String encAlg, byte[] IV) {
		this.keyGenAlg = keyGenAlg;
		this.salt = ImmutableBytes.getInstance (salt);
		this.iterations = iterations;
		this.encAlg = encAlg;
		this.IV = ImmutableBytes.getInstance (IV);
	}
	
	public final boolean addFileMeta (long index, byte[] encFileMeta) {
		if (encFileMeta == null) {
			// Logger.log ("Encrypted file metadata was null.");
			return false;
		}
		
		encFileTable.put(index, ImmutableBytes.getInstance (encFileMeta));
		return true;
	}
	
	public final byte[] getEncFileMeta (long index) {
		return encFileTable.get (index).getBytes();
	}
	
	public final void removeFileMeta (long index) {
		encFileTable.remove (index);
	}
	
	public final boolean indexExists (long index) {
		return encFileTable.containsKey(index);
	}
	
	public final String getKeyGenAlg () {
		return keyGenAlg;
	}
	
	public final byte[] getSalt () {
		return salt.getBytes();
	}
	
	public final int getIterations () {
		return iterations;
	}
	
	public final String getEncAlg () {
		return encAlg;
	}
	
	public final byte[] getIV () {
		return IV.getBytes();
	}
	
	public final ArrayList<Long> getIndices () {
		return new ArrayList<> (encFileTable.keySet());
	}
}
