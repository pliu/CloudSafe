package com.cloudsafe.shared;

/**
 * Root (abstract) class for storage adaptors. Classes that are registrable in PluginRegistry must extend this class.
 * <p>
 * Next Steps:
 * - look into locking/concurrency control to ensure that while a file is being uploaded, downloaded, or deleted, it
 * will not be accessed by another process that could result in an inconsistent state
 */
public abstract class AbstractStorageAdaptor implements Registrable, Creatable {

    /**
     * Deletes the data at the given path.
     *
     * @param path The path at which data is deleted.
     * @return Returns true if the data is successfully deleted and false otherwise.
     */
    public abstract boolean deleteData(String path);

    /**
     * @param path The path to be checked.
     * @return Returns true if the path is already occupied and false otherwise.
     */
    public abstract boolean pathOccupied(String path);

    /**
     * Uploads the data to the given path.
     *
     * @param path The path at which to upload data.
     * @return Returns true if the data is successfully uploaded to the path or false otherwise.
     */
    public abstract boolean uploadData(String path, byte[] data);

    /**
     * Downloads the data at the given path.
     *
     * @param path The path from which to download the data from.
     * @return Returns the data as a byte[] array if successful or null if the path is inaccessible.
     */
    public abstract byte[] downloadData(String path);

    /**
     * @return Returns true if the connection is open by the end of the method or false otherwise.
     */
    public abstract boolean openConnection();

    /**
     * @return Returns true if the connection is closed by the end of the method or false otherwise.
     */
    public abstract boolean closeConnection();

}
