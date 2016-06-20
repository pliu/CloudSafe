package com.cloudsafe.utils;

import java.io.*;

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

    /**
     * Serializes a Serializable object.
     *
     * @param obj The Serializable to be serialized. Relies on callers to ensure a non-null value.
     * @return Returns a byte[] of the serialized object.
     * @throws IOException If can't writeObject or toByteArray.
     */
    public static byte[] serialize(Serializable obj) throws IOException {
        ObjectOutputStream out = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            return bos.toByteArray();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.out.println("Problem closing ObjectOutputStream");
            }
        }
    }

    /**
     * Deserializes a Serializable object.
     *
     * @param bytes The serialized byte[]. Relies on callers to ensure a non-null value.
     * @return Returns an Object that is a copy of the one serialized to obtain bytes.
     * @throws IOException If can't new ObjectInputStream or readObject.
     * @throws ClassNotFoundException If class from which bytes was serialized is not available.
     */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            in = new ObjectInputStream(bis);
            return in.readObject();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("Problem closing ObjectInputStream");
            }
        }
    }
}
