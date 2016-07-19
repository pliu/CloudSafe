package com.cloudsafe.shared;

/**
 * Root class for storage adaptors.
 *
 * Next Steps:
 * - look into locking/concurrency control to ensure that while a file is being uploaded, downloaded, or deleted, it
 *   will not be accessed by another process that could result in an inconsistent state
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
     * Checks if the path is occupied.
     *
     * @param path The path to be checked.
     * @return Returns true if the path is already occupied and false otherwise.
     */
    public abstract boolean pathOccupied(String path);

    /**
     * Uploads the data to the given path.
     *
     * @param path The path at which to upload data.
     * @param data A byte[] representation of the data to upload.
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
     * Sets up any requirements for the functioning of the storage adaptor.
     *
     * @return Returns true if all requirements are set up by the end of the method or false otherwise.
     */
    public abstract boolean setup();

    /**
     * Cleans up any overhead that was required by the storage adaptor.
     *
     * @return Returns true if all overhead is cleaned up by the end of the method or false otherwise.
     */
    public abstract boolean cleanup();

}
