package com.cloudsafe.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileTableTest {

    private static final byte[] bytes = "this is a test".getBytes();

    @Test
    public void getInstanceValid() throws Exception {
        FileTable data = FileTable.getInstance("encAlgName", "encAlgVersion", bytes, bytes);
        assertEquals("encAlgName", data.getEncAlgName());
        assertEquals("encAlgVersion", data.getEncAlgVersion());
        assertArrayEquals(bytes, data.getSalt());
        assertArrayEquals(bytes, data.getIV());
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullEncAlgName() throws Exception {
        FileTable.getInstance(null, "encAlgVersion", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullEncAlgVersion() throws Exception {
        FileTable.getInstance("encAlgName", null, bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullSalt() throws Exception {
        FileTable.getInstance("encAlgName", "encAlgVersion", null, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullIV() throws Exception {
        FileTable.getInstance("encAlgName", "encAlgVersion", bytes, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyEncAlgName() throws Exception {
        FileTable.getInstance("", "encAlgVersion", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyEncAlgVersion() throws Exception {
        FileTable.getInstance("encAlgName", "", bytes, bytes);
    }

    // Tests salt isolation from the byte[] used to instantiate FileMetadata and from the byte[] returned by getKey
    @Test
    public void immutableSalt() throws Exception {
        byte[] bytes = "this is a test".getBytes();
        FileTable data = FileTable.getInstance("encAlgName", "encAlgVersion", bytes, bytes);
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getSalt()[0]);
        bytes = data.getSalt();
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getSalt()[0]);
    }

    // Tests IV isolation from the byte[] used to instantiate FileMetadata and from the byte[] returned by getKey
    @Test
    public void immutableIV() throws Exception {
        byte[] bytes = "this is a test".getBytes();
        FileTable data = FileTable.getInstance("encAlgName", "encAlgVersion", bytes, bytes);
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getIV()[0]);
        bytes = data.getIV();
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getIV()[0]);
    }

    @Test
    public void serializable() throws Exception {

    }

}