package com.cloudsafe.utils;

/**
 * Static utility functions.
 */
public class Utils {

    /**
     * Returns a copy of a byte[].
     *
     * @param bytes The byte[] to be copied. Relies on callers to ensure a non-null value.
     * @return Returns a copy of bytes. Changes to the returned byte[] do not affect the original byte[].
     */
    public static byte[] copy(byte[] bytes) {
        byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        return copy;
    }
}
