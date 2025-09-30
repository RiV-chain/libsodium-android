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

package org.rivchain.libsodium_java.interfaces;


import org.rivchain.libsodium_java.utils.Constants;
import org.rivchain.libsodium_java.utils.Key;

public interface StreamJava extends Stream {

    int SALSA2012_KEYBYTES = 32, SALSA2012_NONCEBYTES = 8,
        SALSA208_KEYBYTES = 32, SALSA208_NONCEBYTES = 8,
        XCHACHA20_KEYBYTES = 32, XCHACHA20_NONCEBYTES = 24;

    long SALSA2012_MESSAGEBYTES_MAX = Constants.SIZE_MAX,
         SALSA208_MESSAGEBYTES_MAX = Constants.SIZE_MAX,
         XCHACHA20_MESSAGEBYTES_MAX = Constants.SIZE_MAX;


    enum Method {
        SALSA20_12,
        SALSA20_8,
        XCHACHA20,
    }


    interface Native extends Stream.Native {

        void cryptoStreamSalsa2012Keygen(byte[] key);

        boolean cryptoStreamSalsa2012(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamSalsa2012Xor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );

        void cryptoStreamSalsa208Keygen(byte[] key);

        boolean cryptoStreamSalsa208(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamSalsa208Xor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );


        // XChaCha20

        void cryptoStreamXChaCha20Keygen(byte[] key);

        boolean cryptoStreamXChaCha20(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamXChaCha20Xor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamXChaCha20Ic(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                long ic,
                byte[] key
        );


    }



    interface Lazy extends Stream.Lazy {

        Key cryptoStreamKeygen(StreamJava.Method method);

        byte[] cryptoStream(
                byte[] nonce,
                Key key,
                StreamJava.Method method
        );

        String cryptoStreamXor(
                String message,
                byte[] nonce,
                Key key,
                StreamJava.Method method
        );

        String cryptoStreamXorDecrypt(
                String cipher,
                byte[] nonce,
                Key key,
                StreamJava.Method method
        );

        String cryptoStreamXorIc(
                String message,
                byte[] nonce,
                long ic,
                Key key,
                StreamJava.Method method
        );

        String cryptoStreamXorIcDecrypt(
                String cipher,
                byte[] nonce,
                long ic,
                Key key,
                StreamJava.Method method
        );

    }

}
