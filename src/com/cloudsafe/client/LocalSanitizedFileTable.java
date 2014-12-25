package com.cloudsafe.client;

import com.cloudsafe.shared.ImmutableBytes;
import com.cloudsafe.shared.Logger;
import com.cloudsafe.shared.SanitizedFileTable;

public final class LocalSanitizedFileTable extends SanitizedFileTable {
	
	private static final long serialVersionUID = 0L;
	
	private ImmutableBytes encToken;
	
	public static final LocalSanitizedFileTable getInstance (String keyGenAlg, byte[] salt,
			int iterations, String encAlg, byte[] IV) {
		return new LocalSanitizedFileTable (keyGenAlg, salt, iterations, encAlg, IV);
	}
	
	private LocalSanitizedFileTable (String keyGenAlg, byte[] salt, int iterations, String encAlg,
			byte[] IV) {
		super (keyGenAlg, salt, iterations, encAlg, IV);
	}

	public final byte[] getEncToken () {
		return encToken.getBytes();
	}
	
	public final boolean putEncToken (byte[] encToken) {
		if (encToken == null) {
			Logger.log ("Encrypted token was null.");
			return false;
		}
		this.encToken = ImmutableBytes.getInstance (encToken);
		return true;
	}
}
