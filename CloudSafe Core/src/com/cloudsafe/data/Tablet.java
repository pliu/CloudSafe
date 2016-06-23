package com.cloudsafe.data;

import com.cloudsafe.utils.Utils;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 */
public final class Tablet implements Serializable {

    /**
     *
     */
    private static final class TabletProxy implements Serializable {

        // Must be incremented every time this class is changed in an incompatible manner.
        private static final long serialVersionUID = 0L;

        private final String name;
        private final String encAlgName;
        private final String encAlgVersion;
        private final byte[] salt;
        private final byte[] IV;
        private byte[] encryptedFileTable;

        private TabletProxy(Tablet tablet) {
            this.name = tablet.name;
            this.encAlgName = tablet.encAlgName;
            this.encAlgVersion = tablet.encAlgVersion;
            this.salt = tablet.salt;
            this.IV = tablet.IV;
            try {
                this.encryptedFileTable = Utils.serialize(tablet.fileTable);
            } catch (Exception e) {

            }
        }

        private Object readResolve() throws ObjectStreamException {
            Hashtable fileTable = null;
            try {
                fileTable = (Hashtable) Utils.deserialize(encryptedFileTable);
            } catch (Exception e) {

            }
            return new Tablet(name, encAlgName, encAlgVersion, salt, IV, fileTable);
        }
    }

    // Must be incremented every time this class is changed in an incompatible manner.
    private static final long serialVersionUID = 0L;

    private final String name;
    private final String encAlgName;
    private final String encAlgVersion;
    private final byte[] salt;
    private final byte[] IV;
    private final Hashtable fileTable;

    /**
     *
     * @param name
     * @param encAlgName
     * @param encAlgVersion
     * @param salt
     * @param IV
     * @return
     * @throws IllegalArgumentException
     */
    public static Tablet getInstance(String name, String encAlgName, String encAlgVersion, byte[] salt, byte[] IV)
            throws IllegalArgumentException {
        validate(name, encAlgName, encAlgVersion, salt, IV);
        return new Tablet(name, encAlgName, encAlgVersion, salt, IV, new Hashtable());
    }

    /**
     *
     */
    private Tablet(String name, String encAlgName, String encAlgVersion, byte[] salt, byte[] IV, Hashtable fileTable) {
        this.name = name;
        this.encAlgName = encAlgName;
        this.encAlgVersion = encAlgVersion;
        this.salt = Utils.copy(salt);
        this.IV = Utils.copy(IV);
        this.fileTable = fileTable;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
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

    /**
     *
     */
    private Object writeReplace() throws ObjectStreamException {
        return new TabletProxy(this);
    }

    /**
     *
     */
    private static void validate(String name, String encAlgName, String encAlgVersion, byte[] salt, byte[] IV) throws
            IllegalArgumentException {
        if (name == null || name.equals("")) {
            System.out.println("Instantiating Tablet with null or empty name");
            throw new IllegalArgumentException("Instantiating Tablet with null or empty name");
        }
        if (encAlgName == null || encAlgName.equals("")) {
            System.out.println("Instantiating Tablet with null or empty encAlgName");
            throw new IllegalArgumentException("Instantiating Tablet with null or empty encAlgName");
        }
        if (encAlgVersion == null || encAlgVersion.equals("")) {
            System.out.println("Instantiating Tablet with null or empty encAlgVersion");
            throw new IllegalArgumentException("Instantiating Tablet with null or empty encAlgVersion");
        }
        if (salt == null) {
            System.out.println("Instantiating Tablet with null salt");
            throw new IllegalArgumentException("Instantiating Tablet with null salt");
        }
        if (IV == null) {
            System.out.println("Instantiating Tablet with null IV");
            throw new IllegalArgumentException("Instantiating Tablet with null IV");
        }
    }

}
