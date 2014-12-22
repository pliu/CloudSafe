package com.cloudsafe.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public final class SanitizedProfile implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private final String alg;
	private final byte[] salt;
	private final byte[] IV;
	private final int iterations;
	private final ArrayList<EncryptedFileMeta> fileList;
	
	public static final SanitizedProfile getInstance (String alg, byte[] salt, byte[] IV, int iterations, Hashtable<String, EncryptedFileMeta> fileTable) {
		if (alg == null) {
			
		}
		if (salt == null) {
			
		}
		if (IV == null) {
			
		}
		if (iterations < 1) {
			
		}
		if (fileTable == null) {
			
		}
		
		byte[] encFileTable = null;
		
		return new SanitizedProfile (alg, salt, IV, iterations, encFileTable);
	}
	
	public final SanitizedProfile getInstance (Hashtable<String, EncryptedFileMeta> fileTable) {
		return SanitizedProfile.getInstance (alg, salt, IV, iterations, fileTable);
	}
	
	private SanitizedProfile (String alg, byte[] salt, byte[] IV, int iterations, ArrayList<EncryptedFileMeta> fileList) {
		this.alg = alg;
		this.salt = salt;
		this.IV = IV;
		this.iterations = iterations;
		this.fileList = fileList;
	}
}
