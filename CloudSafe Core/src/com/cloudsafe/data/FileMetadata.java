package com.cloudsafe.data;

import com.cloudsafe.utils.Utils;

import java.io.Serializable;

/**
 * Immutable metadata associated with an encrypted file.
 * Each file has its own unique key so that users can share individual files independently of each other.
 */
public final class FileMetadata implements Serializable {

    // Must be incremented every time this class is changed in an incompatible manner.
    private static final long serialVersionUID = 0L;

    private final String localFilename;
    private final String description;
    private final String remoteFilename;
    private final byte[] key;
    private final byte[] IV;

    /**
     * Factory method for wrapping an encrypted file's metadata in an instance of FileMetadata.
     *
     * @param localFilename  The String representing the original filename.
     * @param description    The String description of the file.
     * @param remoteFilename The String representing the canonical path of the file on the remote store.
     * @param key            The byte[] used as the key to encrypt the file.
     * @param IV             The byte[] used as the IV to encrypt the file.
     * @return Returns a FileMetadata object containing the above-mentioned metadata.
     * @throws IllegalArgumentException If localFilename or remoteFilename are null or empty, or if description, key, or
     *                                  IV are null.
     */
    public static FileMetadata getInstance(String localFilename, String description, String remoteFilename, byte[] key,
                                           byte[] IV) throws IllegalArgumentException {
        validate(localFilename, description, remoteFilename, key, IV);
        return new FileMetadata(localFilename, description, remoteFilename, key, IV);
    }

    /**
     * Private constructor called by getInstance. Defensively copies key and IV to insulate the local copies from
     * changes to the originals.
     */
    private FileMetadata(String localFilename, String description, String remoteFilename, byte[] key, byte[] IV) {
        this.localFilename = localFilename;
        this.description = description;
        this.remoteFilename =  remoteFilename;
        this.key = Utils.copy(key);
        this.IV = Utils.copy(IV);
    }

    /**
     * Returns the original filename.
     *
     * @return Returns a non-null, non-empty String (verified at creation in the factory method).
     */
    public String getLocalName() {
        return localFilename;
    }

    /**
     * Returns a description of the file.
     *
     * @return Returns a non-null String (verified at creation in the factory method).
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the filename used in the remote store.
     *
     * @return Returns a non-null, non-empty String (verified at creation in the factory method).
     */
    public String getRemoteName() {
        return remoteFilename;
    }

    /**
     * Returns a copy of the key used to encrypt this file.
     *
     * @return Returns a non-null byte[] (verified at creation in the factory method). Changes to the returned byte[] do
     * not affect the local key.
     */
    public byte[] getKey() {
        return Utils.copy(key);
    }

    /**
     * Returns a copy of the IV used to encrypt this file.
     *
     * @return Returns a non-null byte[] (verified at creation in the factory method). Changes to the returned byte[] do
     * not affect the local IV.
     */
    public byte[] getIV() {
        return Utils.copy(IV);
    }

    /**
     *
     */
    private static void validate(String localFilename, String description, String remoteFilename, byte[] key, byte[] IV)
            throws IllegalArgumentException {
        if (localFilename == null || localFilename.equals("")) {
            System.out.println("Instantiating FileMetadata with null or empty localFilename");
            throw new IllegalArgumentException("Instantiating FileMetadata with null or empty localFilename");
        }
        if (description == null) {
            System.out.println("Instantiating FileMetadata with null description");
            throw new IllegalArgumentException("Instantiating FileMetadata with null description");
        }
        if (remoteFilename == null || remoteFilename.equals("")) {
            System.out.println("Instantiating FileMetadata with null or empty remoteFilename");
            throw new IllegalArgumentException("Instantiating FileMetadata with null or empty remoteFilename");
        }
        if (key == null) {
            System.out.println("Instantiating FileMetadata with null key");
            throw new IllegalArgumentException("Instantiating FileMetadata with null key");
        }
        if (IV == null) {
            System.out.println("Instantiating FileMetadata with null IV");
            throw new IllegalArgumentException("Instantiating FileMetadata with null IV");
        }
    }

}
