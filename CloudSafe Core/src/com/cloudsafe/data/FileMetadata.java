package com.cloudsafe.data;

import com.cloudsafe.shared.ImmutableBytes;

import java.io.Serializable;

/**
 * Holds the metadata associated with an encrypted file
 */
public final class FileMetadata implements Serializable {

    static final FileMetadata EMPTY_METADATA = new FileMetadata("", "", null, null);

    // Must be incremented everytime this class is changed.
    private static final long serialVersionUID = 0L;

    private final String localFilename;
    private final String remoteFilename;
    private final ImmutableBytes key;
    private final ImmutableBytes IV;

    /**
     * Factory method
     * @param localFilename
     * @param remoteFilename
     * @param key
     * @param IV
     * @return
     */
    public static FileMetadata getInstance(String localFilename, String remoteFilename, byte[] key, byte[] IV) {
        if (localFilename == null) {
            // Logger.log ("Local filename was null.");
            return null;
        }
        if (remoteFilename == null) {
            // Logger.log ("Remote filename was null.");
            return null;
        }
        if (key == null) {
            // Logger.log ("Key was null.");
            return null;
        }
        if (IV == null) {
            // Logger.log ("IV was null.");
            return null;
        }

        return new FileMetadata(localFilename, remoteFilename, key, IV);
    }

    /**
     * Private constructor called by getInstance. Wraps the original byte[]s in ImmutableByte from
     * changes in the original byte[].
     */
    private FileMetadata(String localFilename, String remoteFilename, byte[] key, byte[] IV) {
        this.localFilename = localFilename;
        this.remoteFilename = remoteFilename;
        this.key = ImmutableBytes.getInstance(key);
        this.IV = ImmutableBytes.getInstance(IV);
    }

    /**
     *
     * @return
     */
    public final String getLocalName() {
        return localFilename;
    }

    /**
     *
     * @return
     */
    public final String getRemoteName() {
        return remoteFilename;
    }

    /**
     *
     * @return
     */
    public final byte[] getKey() {
        return key.getBytes();
    }

    /**
     *
     * @return
     */
    public final byte[] getIV() {
        return IV.getBytes();
    }
}
