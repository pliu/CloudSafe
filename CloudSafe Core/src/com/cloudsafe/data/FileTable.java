package com.cloudsafe.data;

import com.cloudsafe.utils.Utils;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Mostly immutable table that stores
 */
public final class FileTable {

    /**
     *
     */
    private static final class SerializableFileTable implements Serializable {

        private static final long serialVersionUID = 0L;

        private final String encAlgName;
        private final String encAlgVersion;
        private final byte[] salt;
        private final byte[] IV;
        private final byte[] encryptedFileTable;

        private SerializableFileTable(String encAlgName, String encAlgVersion, byte[] salt, byte[] IV,
                                      byte[] encryptedFileTable) {
            this.encAlgName = encAlgName;
            this.encAlgVersion = encAlgVersion;
            this.salt = salt;
            this.IV = IV;
            this.encryptedFileTable = encryptedFileTable;
        }
    }
	
	private final String encAlgName;
	private final String encAlgVersion;
	private final byte[] salt;
	private final byte[] IV;
    private final Hashtable fileTable;

    /**
     *
     * @param encAlgName
     * @param encAlgVersion
     * @param salt
     * @param IV
     * @return
     * @throws IllegalArgumentException
     */
	public static FileTable getInstance (String encAlgName, String encAlgVersion, byte[] salt, byte[] IV) throws
            IllegalArgumentException {
		if (encAlgName == null || encAlgName.equals("")) {
            System.out.println("Instantiating FileTable with null or empty encAlgName");
            throw new IllegalArgumentException("Instantiating FileTable with null or empty encAlgName");
		}
		if (encAlgVersion == null || encAlgVersion.equals("")) {
            System.out.println("Instantiating FileTable with null or empty encAlgVersion");
            throw new IllegalArgumentException("Instantiating FileTable with null or empty encAlgVersion");
		}
		if (salt == null) {
            System.out.println("Instantiating FileTable with null salt");
            throw new IllegalArgumentException("Instantiating FileTable with null salt");
		}
		if (IV == null) {
			System.out.println("Instantiating FileTable with null IV");
            throw new IllegalArgumentException("Instantiating FileTable with null IV");
		}
		
		return new FileTable(encAlgName, encAlgVersion, salt, IV);
	}

    /**
     *
     */
	private FileTable(String encAlgName, String encAlgVersion, byte[] salt, byte[] IV) {
		this.encAlgName = encAlgName;
        this.encAlgVersion = encAlgVersion;
        this.salt = Utils.copy(salt);
        this.IV = Utils.copy(IV);
        fileTable = new Hashtable();
	}

    /**
     *
     * @return
     */
    public String getEncAlgName() {
        return encAlgName;
    }

    /**
     *
     * @return
     */
    public String getEncAlgVersion() {
        return encAlgVersion;
    }

    /**
     *
     * @return
     */
    public byte[] getSalt() {
        return Utils.copy(salt);
    }

    /**
     *
     * @return
     */
    public byte[] getIV() {
        return Utils.copy(IV);
    }

    public byte[] serialize() {
        return null;
    }

    public static FileMetadata deserialize(byte[] bytes) {
        return null;
    }

}
