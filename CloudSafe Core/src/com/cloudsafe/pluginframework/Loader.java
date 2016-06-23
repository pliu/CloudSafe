package com.cloudsafe.pluginframework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Loads valid classes, listed under the MANIFEST.MF's PLUGIN_MANIFEST_CLASS_KEY property, from jars in the given
 * directories and registers them in the given Registry.
 * The class is package local as it is only used by Registry's loadPluginsFromDir method.
 */
final class Loader {

    private static final String PLUGIN_MANIFEST_CLASS_KEY = "Plugin-Classes";

    private final Registry registry;

    Loader(Registry registry) {
        this.registry = registry;
    }

    /**
     * Given a valid directory path, calls loadPluginsFromJar on each jar in the directory and returns true; false
     * otherwise. Relies on the Registry's loadPluginsFromDir method to ensure a non-null path.
     */
    boolean loadPluginsFromDir(String path) {
        File dir = new File(path);
        if (isDirectoryPath(dir)) {
            File[] files = dir.listFiles((file, name) -> {
                return name.endsWith(".jar");
            });
            for (File file : files) {
                loadPluginsFromJar(file);
            }
            return true;
        } else {
            System.out.println("Invalid path: " + path);
            return false;
        }
    }

    /**
     * Given a jar, creates a new URLClassLoader and loads valid classes, listed under the MANIFEST.MF's
     * PLUGIN_MANIFEST_CLASS_KEY property, and registers them in the given Registry.
     * Since a new URLClassLoader is created for each jar, the classes in a given jar are isolated from classes in other
     * jars.
     * Relies on Registry's register method to type-check the loaded class.
     */
    private void loadPluginsFromJar(File jar) {
        String[] pluginClasses = getManifestClasses(jar);
        try {
            URLClassLoader classloader = new URLClassLoader(new URL[]{new URL("jar:file:" + jar.getAbsolutePath() +
                    "!/")});
            for (String pluginClass : pluginClasses) {
                try {
                    Class c = classloader.loadClass(pluginClass);
                    registry.register(c);
                } catch (ClassNotFoundException e) {
                    System.out.println(pluginClass + " not found");
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: jar:file:" + jar.getAbsolutePath() + "!/");
        }
    }

    /**
     * Given a jar, returns an Array of Strings of the classes to load from the jar as listed under the MANIFEST.MF's
     * PLUGIN_MANIFEST_CLASS_KEY property.
     */
    private String[] getManifestClasses(File jar) {
        try {
            JarFile jarFile = new JarFile(jar);
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes properties = manifest.getMainAttributes();
                String unparsed = properties.getValue(PLUGIN_MANIFEST_CLASS_KEY);
                if (unparsed != null) {
                    return parseString(unparsed);
                } else {
                    System.out.println("No " + PLUGIN_MANIFEST_CLASS_KEY + " in the manifest of " + jar.getPath());
                    return new String[0];
                }
            } else {
                System.out.println("No manifest in " + jar.getPath());
                return new String[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /**
     * Returns whether the given path is a valid directory path (exists and is a directory).
     */
    private static boolean isDirectoryPath(File path) {
        return path.exists() && path.isDirectory();
    }

    /**
     * Given a String, returns an Array of String trimmed tokens as tokenized by ",".
     */
    private static String[] parseString(String str) {
        String[] classes = str.split(",");
        for (int i = 0; i < classes.length; i++) {
            classes[i] = classes[i].trim();
        }
        return classes;
    }

}
