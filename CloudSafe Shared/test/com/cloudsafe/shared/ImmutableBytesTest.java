package com.cloudsafe.shared;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImmutableBytesTest {

    // Tests the immutability of ImmutableBytes to mutation of the original byte[].
    @Test
    public void originalBytes() throws Exception {
        byte[] bytes = "test".getBytes();
        ImmutableBytes imBytes = ImmutableBytes.getInstance(bytes);
        bytes[0] = 'a';
        assertNotEquals("test", new String(bytes, "UTF-8"));
        assertEquals("test", new String(imBytes.getBytes(), "UTF-8"));
    }

    // Tests the immutability of ImmutableBytes to the mutation of the byte[] returned by getBytes().
    @Test
    public void getBytes() throws Exception {
        ImmutableBytes imBytes = ImmutableBytes.getInstance("test".getBytes());
        byte[] bytes = imBytes.getBytes();
        assertEquals("test", new String(bytes, "UTF-8"));
        bytes[0] = 'a';
        assertNotEquals("test", new String(bytes, "UTF-8"));
        assertEquals("test", new String(imBytes.getBytes(), "UTF-8"));
    }

    // Tests that ImmutableBytes.EMPTY_BYTES is returned when creating an instance using null.
    @Test
    public void nullBytes() throws Exception {
        ImmutableBytes imBytes = ImmutableBytes.getInstance(null);
        assertSame(ImmutableBytes.EMPTY_BYTES, imBytes);
    }

    // Tests that ImmutableBytes.EMPTY_BYTES is returned when creating an instance using an empty byte[].
    @Test
    public void emptyBytes() throws Exception {
        ImmutableBytes imBytes = ImmutableBytes.getInstance(new byte[0]);
        assertSame(ImmutableBytes.EMPTY_BYTES, imBytes);
    }

}