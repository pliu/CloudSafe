package com.cloudsafe.data;

import java.io.Serializable;

import com.cloudsafe.utility.Logger;

public final class FileMetadata implements Serializable {
	
	private static final long serialVersionUID = 0L;
	
	private final String localFilename;
	private final String remoteFilename;
	private final ImmutableBytes key;
	private final ImmutableBytes IV;
	
	public static final FileMetadata getInstance (String localFilename, String remoteFilename, byte[] key,
			byte[] IV) {
		if (localFilename == null) {
			Logger.log ("Local filename was null.");
			return null;
		}
		if (remoteFilename == null) {
			Logger.log ("Remote filename was null.");
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
		
		return new FileMetadata (localFilename, remoteFilename, key, IV);
	}
	
	private FileMetadata (String localFilename, String remoteFilename, byte[] key, byte[] IV) {
		this.localFilename = localFilename;
		this.remoteFilename = remoteFilename;
		this.key = ImmutableBytes.getInstance (key);
		this.IV = ImmutableBytes.getInstance (IV);
	}
	
	public final String getLocalName () {
		return localFilename;
	}
	
	public final String getRemoteName () {
		return remoteFilename;
	}
	
	public final byte[] getKey () {
		return key.getBytes();
	}
	
	public final byte[] getIV () {
		return IV.getBytes();
	}
}
