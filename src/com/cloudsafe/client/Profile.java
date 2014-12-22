package com.cloudsafe.client;

import java.util.Hashtable;

import com.cloudsafe.shared.EncryptedFileMeta;
import com.cloudsafe.shared.SanitizedProfile;

public class Profile {
	
	private SanitizedProfile sanitizedProfile;
	private SymmetricCrypto keyEncryptor;
	private final Hashtable <String, EncryptedFileMeta> fileTable = new Hashtable<>();
	private final Hashtable <String, String> remoteUsedNames = new Hashtable<>();
}
