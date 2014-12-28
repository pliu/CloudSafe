package com.cloudsafe.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ListIterator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import com.cloudsafe.shared.Logger;

public final class Session {
	
	private static final RandomDataGenerator rand = new RandomDataGenerator();
	private static final int RAND_STRING_LENGTH = 10; // in characters
	private static final String FILE_EXTENSION = ".csafe";
	
	//Put into higher layer
	private static final int SALT_LENGTH = 128; // in bits
	private static final int ITERATIONS = (int) Math.pow (2, 17);
	
	//For testing purposes
	private static final String OUTPUT = System.getProperty ("user.home") + "\\Desktop\\CloudSafe\\";
	
	private final LocalSanitizedFileTable sanitizedTable;
	private final SymmetricCrypto metaEncryptor;
	private final Hashtable <String, Long> fileTable = new Hashtable<>();
	private final Hashtable <String, String> remoteUsedNames = new Hashtable<>();
	
	private CloudConnection cloudConnection;
	
	public static final Session getInstance (LocalSanitizedFileTable sanitizedTable, String passphrase) {
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
	
	public static final Session getInstance (String keyGenAlg, int saltLength, String encAlg,
			int iterations, String passphrase) {
		if (keyGenAlg == null) {
			Logger.log ("Key generation algorithm was null.");
			return null;
		}
		if (saltLength < 1) {
			Logger.log ("Salt length was " + saltLength + " bits (key length must be at least 1).");
			return null;
		}
		if (saltLength % 8 != 0) {
			Logger.log ("Salt length was " + saltLength + " bits (key length must be a multiple of 8).");
			return null;
		}
		if (encAlg == null) {
			Logger.log ("Encryption algorithm was null.");
			return null;
		}
		if (iterations < 1) {
			Logger.log ("Iterations input was " + iterations + " (iterations must be greater than 1).");
			return null;
		}
		if (passphrase == null) {
			Logger.log ("Passphrase was null.");
			return null;
		}
		
		byte[] salt = ByteGenerator.getBytes (ByteGenerator.RANDOM, SALT_LENGTH, null, null, 0);
		byte[] IV = ByteGenerator.getBytes (ByteGenerator.RANDOM, SymmetricCrypto.getBlockLength(encAlg),
				null, null, 0);
		return new Session (LocalSanitizedFileTable.getInstance (keyGenAlg, salt, ITERATIONS, encAlg, IV),
				passphrase);
	}
	
	private Session (LocalSanitizedFileTable sanitizedTable, String passphrase) {
		this.sanitizedTable = sanitizedTable;
		String keyGenAlg = sanitizedTable.getKeyGenAlg();
		byte[] salt = sanitizedTable.getSalt();
		int iterations = sanitizedTable.getIterations();
		String encAlg = sanitizedTable.getEncAlg();
		byte[] key = ByteGenerator.getBytes (keyGenAlg, SymmetricCrypto.getKeyLength (encAlg), passphrase,
				salt, iterations);
		metaEncryptor = SymmetricCrypto.getInstance (encAlg, key);
		
		ListIterator<Long> indices = sanitizedTable.getIndices().listIterator();
		while (indices.hasNext()) {
			long index = indices.next();			
			addMeta (decryptFileMeta (sanitizedTable.getEncFileMeta (index)), index);
		}
	}
	
	public final boolean saveFile (String localFilename) {
		if (localFilename == null) {
			Logger.log ("Local filename was null.");
			return false;
		}
		
		File file = new File (localFilename);
		byte[] fileBytes = null;
		try {
			FileInputStream input = new FileInputStream (file);
			fileBytes = new byte[(int) file.length()];
			input.read (fileBytes);
			input.close();
		}
		catch (IOException e) {
			Logger.log (e.toString());
			return false;
		}
		
		String encAlg = sanitizedTable.getEncAlg();
		byte[] key = ByteGenerator.getBytes (ByteGenerator.RANDOM, SymmetricCrypto.getKeyLength (encAlg),
				null, null, 0);
		byte[] IV = ByteGenerator.getBytes (ByteGenerator.RANDOM, SymmetricCrypto.getBlockLength (encAlg),
				null, null, 0);
		String remoteFilename;
		do {
			remoteFilename = RandomStringUtils.randomAlphanumeric (RAND_STRING_LENGTH).toLowerCase();
		}
		while (remoteUsedNames.containsKey (remoteFilename));
		long index;
		do {
			index = rand.nextLong (Long.MIN_VALUE, Long.MAX_VALUE);
		}
		while (sanitizedTable.indexExists (index));
	
		SymmetricCrypto fileEncryptor = SymmetricCrypto.getInstance (encAlg, key);
		fileBytes = fileEncryptor.encrypt (fileBytes, IV);
		if (!saveFile (OUTPUT + remoteFilename + FILE_EXTENSION, fileBytes)) {
			return false;
		}
		
		FileMetadata fileMeta = FileMetadata.getInstance (localFilename, remoteFilename, key, IV);
		addMeta (fileMeta, index);
		
		return true;
	}
	
	public final boolean loadFile (String localFilename) {
		if (localFilename == null) {
			Logger.log ("Local filename was null.");
			return false;
		}
		if (!fileTable.containsKey (localFilename)) {
			Logger.log ("Local file does not exist.");
			return false;
		}
		
		FileMetadata fileMeta = decryptFileMeta (sanitizedTable.getEncFileMeta (fileTable.get
				(localFilename)));
		
		File file = new File (OUTPUT + fileMeta.getRemoteName() + FILE_EXTENSION);
		byte[] fileBytes = null;
		try {
			FileInputStream input = new FileInputStream (file);
			fileBytes = new byte[(int) file.length()];
			input.read (fileBytes);
			input.close();
		}
		catch (IOException e) {
			Logger.log (e.toString());
			return false;
		}
		
		SymmetricCrypto fileEncryptor = SymmetricCrypto.getInstance (sanitizedTable.getEncAlg(), 
				fileMeta.getKey());
		fileBytes = fileEncryptor.decrypt (fileBytes, fileMeta.getIV());
		
		if (!saveFile (OUTPUT + "test.mp3", fileBytes)) {
			return false;
		}
		
		return true;
	}
	
	private final boolean saveFile (String remoteFilename, byte[] fileBytes) {
		File file = new File (remoteFilename);
		try {
			FileOutputStream output = new FileOutputStream (file);
			output.write (fileBytes);
			output.close();
		}
		catch (IOException e) {
			Logger.log (e.toString());
			return false;
		}
		return true;
	}
	
	private final boolean saveSanitizedFileTable () {
		return true;
	}
	
	private final FileMetadata decryptFileMeta (byte[] encFileMeta) {
		return (FileMetadata) SymmetricCrypto.deserialize (metaEncryptor.decrypt (encFileMeta,
				sanitizedTable.getIV()));
	}
	
	private final void addMeta (FileMetadata fileMeta, long index) {
		sanitizedTable.addFileMeta (index, metaEncryptor.encrypt (fileMeta, sanitizedTable.getIV()));
		fileTable.put (fileMeta.getLocalName(), index);
		remoteUsedNames.put (fileMeta.getRemoteName(), fileMeta.getLocalName());
	}
	
	public static void main (String[] args) {
		Session s = Session.getInstance ("PBKDF2", 128, "GCM256", (int) Math.pow (2, 17), "testing");
		s.saveFile ("D:\\Downloads\\mt9ztzH6LmFX.128.mp3");
		s.loadFile ("D:\\Downloads\\mt9ztzH6LmFX.128.mp3");
	}
}
