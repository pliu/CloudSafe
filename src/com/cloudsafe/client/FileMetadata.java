package com.cloudsafe.client;

import com.cloudsafe.shared.ImmutableBytes;
import com.cloudsafe.shared.Logger;

public final class FileMetadata {
	
	private final String localFilename;
	private final String remoteFilename;
	private final String alg;
	private final ImmutableBytes key;
	private final ImmutableBytes IV;
	
	public static final FileMetadata getInstance (String localFilename, String remoteFilename, String alg,
			byte[] key, byte[] IV) {
		if (localFilename == null) {
			Logger.log ("Local filename was null.");
			return null;
		}
		if (remoteFilename == null) {
			Logger.log ("Remote filename was null.");
			return null;
		}
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return null;
		}
		if (key == null) {
			Logger.log ("Key was null.");
			return null;
		}
		if (IV == null) {
			Logger.log ("IV was null.");
			return null;
		}
		
		return new FileMetadata (localFilename, remoteFilename, alg, key, IV);
	}
	
	private FileMetadata (String localFilename, String remoteFilename, String alg, byte[] key, byte[] IV) {
		this.localFilename = localFilename;
		this.remoteFilename = remoteFilename;
		this.alg = alg;
		this.key = ImmutableBytes.getInstance (key);
		this.IV = ImmutableBytes.getInstance (IV);
	}
	
	public final String getLocalName () {
		return localFilename;
	}
	
	public final String getRemoteName () {
		return remoteFilename;
	}
	
	public final String getAlgorithm () {
		return alg;
	}
	
	public final byte[] getKey () {
		return key.getBytes();
	}
	
	public final byte[] getIV () {
		return IV.getBytes();
	}
}
