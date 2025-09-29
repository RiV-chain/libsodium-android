/*
 * This file is part of libsodium-android.
 *
 * libsodium-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * libsodium-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with libsodium-android.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.rivchain.libsodium_java.utils;

import com.goterl.resourceloader.SharedLibraryLoader;
import org.rivchain.libsodium_java.Sodium;
import org.rivchain.libsodium_java.SodiumJava;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple library class which helps with loading dynamic sodium library stored in the
 * JAR archive. Works with JNA.
 *
 * <p>This class is thread-safe.
 *
 * @see <a href="http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar">http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar</a>
 * @see <a href="https://github.com/adamheinrich/native-utils">https://github.com/adamheinrich/native-utils</a>
 */
public final class LibraryLoader {

    private final Logger logger = LoggerFactory.getLogger(Constants.LAZYSODIUM_JAVA);

    /**
     * Library loading mode controls which libraries are attempted to be loaded (installed in the system or bundled
     * in the Lazysodium JAR) and in which order.
     */
    public enum Mode {

        /**
         * Try to load the system installed native sodium library first, if that fails, then load the bundled version.
         *
         * <p>This is the recommended mode, because it allows the clients to upgrade the sodium library
         * as soon as it is available instead of waiting for another lazysodium release and releasing a new version of
         * the client library/application.
         */
        PREFER_SYSTEM,

        /**
         * Load the bundled native libraries first, then fallback to finding it in the system.
         */
        PREFER_BUNDLED,

        /**
         * Load the bundled version, ignoring the system.
         *
         * <p>This mode might be useful if the system sodium turns out to be outdated and cannot be upgraded.
         */
        BUNDLED_ONLY,

        /**
         * Load the system sodium only, ignoring the bundled.
         *
         * <p>This mode is recommended if it is required to use the system sodium only, and the application
         * must fail if it is not installed.
         */
        SYSTEM_ONLY,
    }

    private List<Class> classes = new ArrayList<>();

    public LibraryLoader(List<Class> classesToRegister) {
        classes.addAll(classesToRegister);
    }

    /**
     * Loads the sodium library and registers the native methods of {@link Sodium}
     * and {@link SodiumJava} using the specified loading mode.
     * The library will be loaded at most once.
     *
     * @param mode controls which sodium library (installed in the system or bundled in the JAR)
     *     is loaded, and in which order
     * @param systemFallBack If loading directly fails then it will fall to the system fallback specified here
     * @throws LibraryLoadingException if fails to load the library
     * @see Native#register(Class, String)
     */
    public void loadLibrary(Mode mode, String systemFallBack) {
        switch (mode) {
            case PREFER_SYSTEM:
                try {
                    loadSystemLibrary(systemFallBack);
                } catch (Throwable suppressed) {
                    logger.debug("Tried loading native libraries from system but failed. Message: {}.", suppressed.getMessage());
                    // Attempt to load the bundled
                    loadBundledLibrary();
                }
                break;
            case PREFER_BUNDLED:
                try {
                    loadBundledLibrary();
                } catch (Throwable suppressed) {
                    logger.debug("Tried loading native libraries from the bundled resources but failed. Message: {}.", suppressed.getMessage());
                    loadSystemLibrary(systemFallBack);
                }
                break;
            case BUNDLED_ONLY:
                loadBundledLibrary();
                break;
            case SYSTEM_ONLY:
                loadSystemLibrary(systemFallBack);
                break;
            default:
                throw new IllegalStateException("Unsupported mode: " + mode);
        }
    }

    public void loadSystemLibrary(String library) {
        SharedLibraryLoader.get().loadSystemLibrary(library, classes);
    }

    public void loadAbsolutePath(String absPath) {
        SharedLibraryLoader.get().loadSystemLibrary(absPath, classes);
    }

    /**
     * Loads library from the current JAR archive and registers the native methods
     * of {@link Sodium} and {@link SodiumJava}. The library will be loaded at most once.
     *
     * <p>The file from JAR is copied into system temporary directory and then loaded.
     * The temporary file is deleted after exiting.
     */
    private void loadBundledLibrary() {
        String pathInJar = getSodiumPathInResources();
        SharedLibraryLoader.get().load(pathInJar, classes);
    }

    /**
     * Returns the absolute path to sodium library inside JAR (beginning with '/'), e.g. /linux/libsodium.so.
     * @return The path to the libsodium binary.
     */
    public static String getSodiumPathInResources() {
        boolean is64Bit = Native.POINTER_SIZE == 8;
        if (Platform.isWindows()) {
            if (is64Bit) {
                return getPath("windows64", "libsodium.dll");
            } else {
                return getPath("windows", "libsodium.dll");
            }
        }
        if (Platform.isMac()) {
            // check for Apple Silicon
            if (Platform.isARM()) {
                return getPath("mac_arm", "libsodium.dylib");
            } else {
                return getPath("mac", "libsodium.dylib");
            }
        }
        if (Platform.isARM()) {
            if (is64Bit) {
                return getPath("arm64", "libsodium.so");
            } else {
                return getPath("armv6", "libsodium.so");
            }
        }
        if (Platform.isLinux()) {
            if (is64Bit) {
                return getPath("linux64", "libsodium.so");
            } else {
                return getPath("linux", "libsodium.so");
            }
        }

        String message = String.format("Unsupported platform: %s/%s", System.getProperty("os.name"),
                System.getProperty("os.arch"));
        throw new LibraryLoadingException(message);
    }

    private static String getPath(String folder, String name) {
        String separator = "/";
        return folder + separator + name;
    }
}
