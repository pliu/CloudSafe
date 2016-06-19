package com.cloudsafe.data;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FileMetadataTest {

    private static final byte[] bytes = "this is a test".getBytes();

    @Test
    public void getInstanceValid() throws Exception {
        FileMetadata data = FileMetadata.getInstance("localFilename", "description", "remoteFilename", bytes, bytes);
        assertEquals("localFilename", data.getLocalName());
        assertEquals("description", data.getDescription());
        assertEquals("remoteFilename", data.getRemoteName());
        assertArrayEquals(bytes, data.getKey());
        assertArrayEquals(bytes, data.getIV());
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullLocalFilename() throws Exception {
        FileMetadata.getInstance(null, "description", "remoteFilename", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullDescription() throws Exception {
        FileMetadata.getInstance("localFilename", null, "remoteFilename", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullRemoteFilename() throws Exception {
        FileMetadata.getInstance("localFilename", "description", null, bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullKey() throws Exception {
        FileMetadata.getInstance("localFilename", "description", "remoteFilename", null, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullIV() throws Exception {
        FileMetadata.getInstance("localFilename", "description", "remoteFilename", bytes, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyLocalFilename() throws Exception {
        FileMetadata.getInstance("", "description", "remoteFilename", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyRemoteFilename() throws Exception {
        FileMetadata.getInstance("localFilename", "description", "", bytes, bytes);
    }

    // Tests key isolation from the byte[] used to instantiate FileMetadata and from the byte[] returned by getKey
    @Test
    public void immutableKey() throws Exception {
        byte[] bytes = "this is a test".getBytes();
        FileMetadata data = FileMetadata.getInstance("localFilename", "description", "remoteFilename", bytes, bytes);
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getKey()[0]);
        bytes = data.getKey();
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getKey()[0]);
    }

    // Tests IV isolation from the byte[] used to instantiate FileMetadata and from the byte[] returned by getIV
    @Test
    public void immutableIV() throws Exception {
        byte[] bytes = "this is a test".getBytes();
        FileMetadata data = FileMetadata.getInstance("localFilename", "description", "remoteFilename", bytes, bytes);
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getIV()[0]);
        bytes = data.getKey();
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getIV()[0]);
    }

    @Test
    public void serializable() throws Exception {
        FileMetadata data = FileMetadata.getInstance("localFilename", "description", "remoteFilename", bytes, bytes);

        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(data);
            byte[] intermediate = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(intermediate);
            in = new ObjectInputStream(bis);
            FileMetadata check = (FileMetadata) in.readObject();

            System.out.println(intermediate.length);
            assertEquals("localFilename", check.getLocalName());
            assertEquals("description", check.getDescription());
            assertEquals("remoteFilename", check.getRemoteName());
            assertArrayEquals(bytes, check.getKey());
            assertArrayEquals(bytes, check.getIV());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.out.println("Shouldn't happen");
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("Shouldn't happen");
            }
        }
    }

}