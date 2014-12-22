package com.cloudsafe.shared;

import java.io.Serializable;

public final class EncryptedFileMeta implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private final String localFilename;
	private final String remoteFilename;
	private final String description;
	private final String alg;
	private final byte[] key;
	private final byte[] IV;
	
	
}
