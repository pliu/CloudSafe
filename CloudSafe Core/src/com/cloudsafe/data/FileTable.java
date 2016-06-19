package com.cloudsafe.data;

import com.cloudsafe.utils.Utils;

import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 */
public class FileTable implements Serializable {

	// Must be incremented everytime this class is changed.
	private static final long serialVersionUID = 0L;
	
	private final String encAlgName;
	private final String encAlgVersion;
	private final byte[] salt;
	private final byte[] IV;

	public static FileTable getInstance (String encAlgName, String encAlgVersion, byte[] salt, byte[] IV) throws
            IllegalArgumentException {
		if (encAlgName == null) {
            System.out.println("");
            throw new IllegalArgumentException("");
		}
		if (encAlgVersion == null) {
            System.out.println("");
            throw new IllegalArgumentException("");
		}
		if (salt == null) {
            System.out.println("");
            throw new IllegalArgumentException("");
		}
		if (IV == null) {
			System.out.println("");
            throw new IllegalArgumentException("");
		}
		
		return new FileTable(encAlgName, encAlgVersion, salt, IV);
	}
	
	private FileTable(String encAlgName, String encAlgVersion, byte[] salt, byte[] IV) {
		this.encAlgName = encAlgName;
        this.encAlgVersion = encAlgVersion;
        this.salt = Utils.copy(salt);
        this.IV = Utils.copy(IV);
	}

}
