package com.cloudsafe.shared;

import java.io.Serializable;

/**
 * A wrapper that provides immuatbility to the wrapped byte[].
 */
public final class ImmutableBytes implements Serializable {

    // The instance that is returned if ImmutableBytes is instantiated with null or an empty byte[].
    private static final ImmutableBytes EMPTY_BYTES = new ImmutableBytes(new byte[0]);

    // Must be incremented everytime this class is changed.
    private static final long serialVersionUID = 0L;

    private final byte[] bytes;

    /**
     * Factory method for wrapping a byte[] in an instance of ImmutableByte.
     * @param bytes The byte[] to be wrapped by ImmutableBytes.
     * @return Returns an ImmutableByte wrapping bytes or EMPTY_BYTES if bytes is null or empty.
     */
    public static ImmutableBytes getInstance(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return EMPTY_BYTES;
        }
        return new ImmutableBytes(bytes);
    }

    /**
     * Private constructor called by getInstance. Wraps a copy of the original byte[] to insulate ImmutableByte from
     * changes in the original byte[].
     */
    private ImmutableBytes(byte[] bytes) {
        this.bytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    /**
     * Returns the wrapped byte[].
     * @return Returns a copy of the byte[] array wrapped by this ImmutableByte. Changes to the returned byte[] do not
     * affect the ImmutableByte.
     */
    public final byte[] getBytes() {
        byte[] bytes = new byte[this.bytes.length];
        System.arraycopy(this.bytes, 0, bytes, 0, this.bytes.length);
        return bytes;
    }

}
