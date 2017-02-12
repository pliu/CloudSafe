package com.cloudsafe.plugins.storage;

import com.cloudsafe.pluginframework.PluginRegistry;
import com.cloudsafe.shared.AbstractStorageAdaptor;
import org.apache.commons.io.FileUtils;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class FileSystemAdaptorTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static String testOutput = "This is a test output!";
    private static FileSystemAdaptor adaptor = new FileSystemAdaptor();

    @Test
    public void writeReadable() throws Exception {
        File file = temporaryFolder.newFile();
        file.delete();
        assertTrue(adaptor.uploadData(file.getAbsolutePath(), testOutput.getBytes()));
        assertArrayEquals(testOutput.getBytes(), adaptor.downloadData(file.getAbsolutePath()));
    }

    @Test
    public void deletable() throws Exception{
        File file = temporaryFolder.newFile();
        file.delete();
        assertTrue(adaptor.uploadData(file.getAbsolutePath(), testOutput.getBytes()));
        assertTrue(adaptor.deleteData(file.getAbsolutePath()));
        assertNull(adaptor.downloadData(file.getAbsolutePath()));
    }

    @Test
    public void noOverwrite() throws Exception {
        File file = temporaryFolder.newFile();
        assertFalse(adaptor.uploadData(file.getAbsolutePath(), testOutput.getBytes()));
    }

    @Test
    public void nonexistentPath() throws Exception {
        adaptor.uploadData("C:/CloudSafeTest/test/output/test.txt", testOutput.getBytes());
        assertTrue(new File("C:/CloudSafeTest/test/output/test.txt").exists());
        FileUtils.deleteDirectory(new File("C:/CloudSafeTest/"));
        assertFalse(new File("C:/CloudSafeTest/").exists());
    }

    @Test
    public void registrableCreatable() throws Exception {
        PluginRegistry<AbstractStorageAdaptor> registry = new PluginRegistry<>(AbstractStorageAdaptor.class);
        assertTrue(registry.register(FileSystemAdaptor.class));
        AbstractStorageAdaptor adaptor = registry.get(FileSystemAdaptor.getName(), FileSystemAdaptor.getVersion());
        assertNotNull(adaptor);
        assertEquals(FileSystemAdaptor.class, adaptor.getClass());
    }

}