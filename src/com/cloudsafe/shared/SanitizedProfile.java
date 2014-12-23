package com.cloudsafe.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public final class SanitizedProfile implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private final String alg;
	private final ImmutableBytes salt;
	private final int iterations;
	private final ImmutableBytes IV;
	private final Hashtable<Integer, ImmutableBytes> encFileTable = new Hashtable<>();
	
	public static final SanitizedProfile getInstance (String alg, byte[] salt, int iterations, byte[] IV) {
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return null;
		}
		if (salt == null) {
			Logger.log ("Salt was null.");
			return null;
		}
		if (iterations < 1) {
			Logger.log ("Iterations input was " + iterations + " (iterations must be greater than 1");
			return null;
		}
		if (IV == null) {
			Logger.log ("IV was null.");
			return null;
		}
		
		return new SanitizedProfile (alg, salt, iterations, IV);
	}
	
	private SanitizedProfile (String alg, byte[] salt, int iterations, byte[] IV) {
		this.alg = alg;
		this.salt = ImmutableBytes.getInstance (salt);
		this.iterations = iterations;
		this.IV = ImmutableBytes.getInstance (IV);
	}
	
	public final boolean addFileMeta (int index, ImmutableBytes encFileMeta) {
		if (encFileMeta == null) {
			Logger.log ("Encrypted file metadata was null.");
			return false;
		}
		
		encFileTable.put(index, encFileMeta);
		return true;
	}
	
	public final void removeFileMeta (int index) {
		encFileTable.remove (index);
	}
	
	public final boolean indexExists (int index) {
		return encFileTable.containsKey(index);
	}
	
	public final String getAlgorithm () {
		return alg;
	}
	
	public final byte[] getSalt () {
		return salt.getBytes();
	}
	
	public final int getIterations () {
		return iterations;
	}
	
	public final byte[] getIV () {
		return IV.getBytes();
	}
	
	public final ArrayList<ImmutableBytes> getEncFileList () {
		return new ArrayList<> (encFileTable.values());
	}
}
