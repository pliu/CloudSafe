package com.cloudsafe.client;

import java.util.ArrayList;
import java.util.Hashtable;

import com.cloudsafe.shared.ImmutableBytes;
import com.cloudsafe.shared.Logger;
import com.cloudsafe.shared.SanitizedFileTable;

public final class Session {
	
	private static final int SALT_LENGTH = 128; // in bits
	private static final int ITERATIONS = (int) Math.pow (2, 17);
	
	private final SanitizedFileTable sanitizedTable;
	private final SymmetricCrypto metaEncryptor;
	private final Hashtable <String, FileMetadata> fileTable = new Hashtable<>();
	private final Hashtable <String, String> remoteUsedNames = new Hashtable<>();
	private final Hashtable <Integer, String> usedIndices = new Hashtable<>();
	
	public static final Session getInstance (SanitizedFileTable sanitizedTable, String passphrase) {
		if (sanitizedTable == null) {
			Logger.log ("Sanitized profile was null.");
			return null;
		}
		if (passphrase == null) {
			Logger.log ("Passphrase was null.");
			return null;
		}
		
		return new Session (sanitizedTable, passphrase);
	}
	
	public static final Session getInstance (String keyGenAlg, String encAlg, String passphrase) {
		if (keyGenAlg == null) {
			Logger.log ("Key generation algorithm was null.");
			return null;
		}
		if (encAlg == null) {
			Logger.log ("Encryption algorithm was null.");
			return null;
		}
		if (passphrase == null) {
			Logger.log ("Passphrase was null.");
			return null;
		}
		
		byte[] salt = ByteGenerator.getBytes (ByteGenerator.RANDOM, SALT_LENGTH, null, null, 0);
		byte[] IV = ByteGenerator.getBytes (ByteGenerator.RANDOM, SymmetricCrypto.getBlockLength(encAlg),
				null, null, 0);
		return new Session (SanitizedFileTable.getInstance (keyGenAlg, salt, ITERATIONS, encAlg, IV),
				passphrase);
	}
	
	private Session (SanitizedFileTable sanitizedTable, String passphrase) {
		this.sanitizedTable = sanitizedTable;
		String keyGenAlg = sanitizedTable.getKeyGenAlg();
		byte[] salt = sanitizedTable.getSalt();
		int iterations = sanitizedTable.getIterations();
		String encAlg = sanitizedTable.getEncAlg();
		byte[] key = ByteGenerator.getBytes (keyGenAlg, SymmetricCrypto.getKeyLength (encAlg), passphrase,
				salt, iterations);
		byte[] IV = sanitizedTable.getIV();
		metaEncryptor = SymmetricCrypto.getInstance (encAlg, key);
		
		ArrayList<Integer> indices = sanitizedTable.getIndices();
		for (int i = 0; i < indices.size(); i ++) {
			Integer index = indices.get (i);
			byte[] encFileMeta = sanitizedTable.getEncFileMeta (index);
			FileMetadata fileMeta = (FileMetadata) SymmetricCrypto.deserialize (metaEncryptor.decrypt
					(encFileMeta, IV));
			fileTable.put (fileMeta.getLocalName(), fileMeta);
			remoteUsedNames.put (fileMeta.getRemoteName(), fileMeta.getLocalName());
			usedIndices.put (index, fileMeta.getLocalName());
		}
	}
	
	public final void saveFile () {
		
	}
	
	public final void loadFile () {
		
	}
}
