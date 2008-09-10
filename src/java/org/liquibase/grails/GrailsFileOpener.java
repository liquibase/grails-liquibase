package org.liquibase.grails;

import liquibase.FileOpener;
import liquibase.ClassLoaderFileOpener;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.codehaus.groovy.grails.commons.ApplicationHolder;

public class GrailsFileOpener implements FileOpener {
    
    private FileOpener fileOpener;

    public GrailsFileOpener() {
        if (ApplicationHolder.getApplication().isWarDeployed()) {
            fileOpener = new ClassLoaderFileOpener();
        } else {
            fileOpener = new DevFileOpener();
        }
    }

    public InputStream getResourceAsStream(String file) throws IOException {
        return fileOpener.getResourceAsStream(file);
    }

    public Enumeration<URL> getResources(String packageName) throws IOException {
        return fileOpener.getResources(packageName);
    }

    public ClassLoader toClassLoader() {
        return fileOpener.toClassLoader();
    }

    private static class DevFileOpener implements FileOpener {

        public InputStream getResourceAsStream(String file) throws IOException {
            return getClass().getClassLoader().getResourceAsStream("grails-app/migrations/"+file);
        }

        public Enumeration<URL> getResources(String packageName) throws IOException {
            return getClass().getClassLoader().getResources("grails-app/migrations/"+packageName);
        }

        public ClassLoader toClassLoader() {
            return getClass().getClassLoader();
        }

    }
}
