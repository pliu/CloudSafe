package com.cloudsafe.client;

import java.util.Enumeration;
import java.util.Hashtable;

import com.cloudsafe.shared.SanitizedProfile;

public final class Profile {
	
	private SanitizedProfile sanitizedProfile;
	private SymmetricCrypto keyEncryptor;
	private final Hashtable <String, FileMetadata> fileTable = new Hashtable<>();
	private final Hashtable <String, String> remoteUsedNames = new Hashtable<>();
	private final Hashtable <String, Integer> usedIndices = new Hashtable<>();
	
	public final void sanitize () {
		Enumeration<FileMetadata> list = fileTable.elements();
		while (list.hasMoreElements()) {
			FileMetadata current = list.nextElement();
		}
	}
}
