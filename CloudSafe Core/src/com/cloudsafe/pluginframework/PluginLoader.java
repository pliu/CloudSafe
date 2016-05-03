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
 * Created by pengl on 5/29/2016.
 */
class PluginLoader {

    private static final String MANIFEST_CLASS_KEY = "Plugin-Classes";

    private final Registry registry;

    PluginLoader(Registry registry) {
        this.registry = registry;
    }

    void loadPluginsFromDir(String path) {
        File dir = new File(path);
        if (isDirectoryPath(dir)) {
            File[] files = dir.listFiles((File file, String name) -> {
                return name.endsWith(".jar");
            });
            for (File file : files) {
                loadPluginsFromJar(file);
            }
        } else {
            System.out.println("Invalid path: " + path);
        }
    }

    private void loadPluginsFromJar(File jar) {
        String[] pluginClasses = getManifestClasses(jar);
        try {
            URLClassLoader classloader = new URLClassLoader(new URL[]{new URL("jar:file:" +
                    jar.getAbsolutePath() + "!/")});
            for (String pluginClass : pluginClasses) {
                try {
                    Class c = classloader.loadClass(pluginClass);
                    Object instance = c.newInstance();
                    registry.register(instance);

                } catch (ClassNotFoundException e) {
                    System.out.println(pluginClass + " not found");
                } catch (IllegalAccessException e) {
                    System.out.println(pluginClass + " is not accessible");
                } catch (InstantiationException e) {
                    System.out.println("Could not instantiate " + pluginClass);
                }

            }

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: jar:file:" + jar.getAbsolutePath() + "!/");
        }
    }

    private String[] getManifestClasses(File jar) {
        try {
            JarFile jarFile = new JarFile(jar);
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes properties = manifest.getMainAttributes();
                String unparsed = properties.getValue(MANIFEST_CLASS_KEY);
                if (unparsed != null) {
                    return parseString(unparsed);
                } else {
                    System.out.println("No Plugin-Classes in the manifest of " + jar.getPath());
                    return new String[]{};
                }
            } else {
                System.out.println("No manifest in " + jar.getPath());
                return new String[]{};
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    private static boolean isDirectoryPath(File path) {
        return path.exists() && path.isDirectory();
    }

    private static String[] parseString(String str) {
        String[] classes = str.split(",");
        for (int i = 0; i < classes.length; i++) {
            classes[i] = classes[i].trim();
        }
        return classes;
    }

}
