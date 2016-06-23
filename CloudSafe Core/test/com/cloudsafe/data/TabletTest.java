package com.cloudsafe.data;

import com.cloudsafe.utils.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class TabletTest {

    private static final byte[] bytes = "this is a test".getBytes();

    @Test
    public void getInstanceValid() throws Exception {
        Tablet data = Tablet.getInstance("test", "encAlgName", "encAlgVersion", bytes, bytes);
        assertEquals("encAlgName", data.getEncAlgName());
        assertEquals("encAlgVersion", data.getEncAlgVersion());
        assertArrayEquals(bytes, data.getSalt());
        assertArrayEquals(bytes, data.getIV());
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullName() throws Exception {
        Tablet.getInstance(null, "encAlgName", "encAlgVersion", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullEncAlgName() throws Exception {
        Tablet.getInstance("test", null, "encAlgVersion", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullEncAlgVersion() throws Exception {
        Tablet.getInstance("test", "encAlgName", null, bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullSalt() throws Exception {
        Tablet.getInstance("test", "encAlgName", "encAlgVersion", null, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullIV() throws Exception {
        Tablet.getInstance("test", "encAlgName", "encAlgVersion", bytes, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyName() throws Exception {
        Tablet.getInstance("", "encAlgName", "encAlgVersion", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyEncAlgName() throws Exception {
        Tablet.getInstance("test", "", "encAlgVersion", bytes, bytes);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyEncAlgVersion() throws Exception {
        Tablet.getInstance("test", "encAlgName", "", bytes, bytes);
    }

    // Tests salt isolation from the byte[] used to instantiate FileMetadata and from the byte[] returned by getKey
    @Test
    public void immutableSalt() throws Exception {
        byte[] bytes = "this is a test".getBytes();
        Tablet data = Tablet.getInstance("test", "encAlgName", "encAlgVersion", bytes, bytes);
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
        Tablet data = Tablet.getInstance("test", "encAlgName", "encAlgVersion", bytes, bytes);
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getIV()[0]);
        bytes = data.getIV();
        bytes[0] = 'a';
        assertEquals('a', bytes[0]);
        assertEquals('t', data.getIV()[0]);
    }

    //TODO: Test encrypted serialization after encryption implemented
    @Test
    public void serializable() throws Exception {
        Tablet data = Tablet.getInstance("test", "encAlgName", "encAlgVersion", bytes, bytes);
        byte[] intermediate = Utils.serialize(data);
        Tablet check = (Tablet)Utils.deserialize(intermediate);
        System.out.println(intermediate.length);
        assertEquals("test", check.getName());
        assertEquals("encAlgName", check.getEncAlgName());
        assertEquals("encAlgVersion", check.getEncAlgVersion());
        assertArrayEquals(bytes, check.getSalt());
        assertArrayEquals(bytes, check.getIV());
    }

}