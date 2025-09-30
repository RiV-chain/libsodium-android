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

package org.rivchain.libsodium_java;

import org.rivchain.libsodium_java.utils.Base64Java;
import org.rivchain.libsodium_java.utils.LibraryLoader;

import java.util.ArrayList;
import java.util.List;

public class SodiumJava extends Sodium {

    /**
     * Creates the SodiumJava instance. Uses the {@linkplain LibraryLoader.Mode#PREFER_SYSTEM default loading mode},
     * first attempting to load the system sodium, and, if that fails — the bundled one.
     */
    public SodiumJava() {
        this(LibraryLoader.Mode.PREFER_SYSTEM);
    }

    /**
     * Creates the SodiumJava using the given loading mode.
     *
     * @param loadingMode controls which sodium library (installed in the system or bundled in the JAR)
     *                    is loaded, and in which order
     * @see LibraryLoader.Mode
     */
    public SodiumJava(LibraryLoader.Mode loadingMode) {
        new LibraryLoader(getClassesToRegister()).loadLibrary(loadingMode, "sodium");
        base64Facade = new Base64Java();
        onRegistered();
    }

    public SodiumJava(String absolutePath) {
        new LibraryLoader(getClassesToRegister()).loadAbsolutePath(absolutePath);
        base64Facade = new Base64Java();
        onRegistered();
    }


    // Scrypt

    public native int crypto_pwhash_scryptsalsa208sha256(
            byte[] out,
            long outLen,
            byte[] password,
            long passwordLen,
            byte[] salt,
            long opsLimit,
            long memLimit
    );

    public native int crypto_pwhash_scryptsalsa208sha256_str(
            byte[] out,
            byte[] password,
            long passwordLen,
            long opsLimit,
            long memLimit
    );

    public native int crypto_pwhash_scryptsalsa208sha256_str_verify(
            byte[] str,
            byte[] password,
            long passwordLen
    );

    public native int crypto_pwhash_scryptsalsa208sha256_ll(
            byte[] password,
            int passwordLen,
            byte[] salt,
            int saltLen,
            long N,
            long r,
            long p,
            byte[] buf,
            int bufLen
    );

    public native int crypto_pwhash_scryptsalsa208sha256_str_needs_rehash(
            byte[] password,
            long opsLimit,
            long memLimit
    );


    // Salsa20 12 rounds

    public native void crypto_stream_salsa2012_keygen(byte[] key);

    public native int crypto_stream_salsa2012(
            byte[] c,
            long cLen,
            byte[] nonce,
            byte[] key
    );

    public native int crypto_stream_salsa2012_xor(
            byte[] cipher,
            byte[] message,
            long messageLen,
            byte[] nonce,
            byte[] key
    );


    public native void crypto_stream_salsa208_keygen(byte[] key);

    public native int crypto_stream_salsa208(
            byte[] c,
            long cLen,
            byte[] nonce,
            byte[] key
    );

    public native int crypto_stream_salsa208_xor(
            byte[] cipher,
            byte[] message,
            long messageLen,
            byte[] nonce,
            byte[] key
    );


    // XChaCha20

    public native int crypto_stream_xchacha20(
            byte[] c,
            long cLen,
            byte[] nonce,
            byte[] key
    );

    public native int crypto_stream_xchacha20_xor(
            byte[] cipher,
            byte[] message,
            long messageLen,
            byte[] nonce,
            byte[] key
    );

    public native int crypto_stream_xchacha20_xor_ic(
            byte[] cipher,
            byte[] message,
            long messageLen,
            byte[] nonce,
            long ic,
            byte[] key
    );

    public native void crypto_stream_xchacha20_keygen(byte[] key);

    public static List<Class> getClassesToRegister() {
        final List<Class> classes = new ArrayList<>();
        classes.add(Sodium.class);
        classes.add(SodiumJava.class);
        return classes;
    }

}
