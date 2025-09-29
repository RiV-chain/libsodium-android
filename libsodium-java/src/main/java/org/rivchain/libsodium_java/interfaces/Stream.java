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

public interface Stream {

    // REGULAR CHACHA

    int CHACHA20_NONCEBYTES = 8,
        CHACHA20_KEYBYTES = 32;
    long CHACHA20_MESSAGEBYTES_MAX = Constants.SIZE_MAX;


    // IETF CHACHA

    int CHACHA20_IETF_NONCEBYTES = 12,
        CHACHA20_IETF_KEYBYTES = 32;
    long CHACHA20_IETF_MESSAGEBYTES_MAX = Constants.GB_256;


    // SALSA20

    int SALSA20_NONCEBYTES = 12,
        SALSA20_KEYBYTES = 32;
    long SALSA20_MESSAGEBYTES_MAX = Constants.SIZE_MAX;


    // XSALSA20

    int XSALSA20_NONCEBYTES = 24,
        XSALSA20_KEYBYTES = 32;
    long XSALSA20_MESSAGEBYTES_MAX = Constants.SIZE_MAX;

    int NONCEBYTES = XSALSA20_NONCEBYTES,
        KEYBYTES = XSALSA20_KEYBYTES;
    long MESSAGEBYTES_MAX = XSALSA20_MESSAGEBYTES_MAX;


    enum Method {
        CHACHA20,
        CHACHA20_IETF,
        SALSA20,
        XSALSA20,
    }



    interface Native {

        void cryptoStreamChaCha20Keygen(byte[] key);

        boolean cryptoStreamChaCha20(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamChaCha20Xor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamChacha20XorIc(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                long ic,
                byte[] key
        );

        // IETF CHACHA

        void cryptoStreamChaCha20IetfKeygen(byte[] key);

        boolean cryptoStreamChaCha20Ietf(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamChaCha20IetfXor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamChacha20IetfXorIc(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                long ic,
                byte[] key
        );

        // Salsa20

        void cryptoStreamSalsa20Keygen(byte[] key);

        boolean cryptoStreamSalsa20(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamSalsa20Xor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamSalsa20XorIc(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                long ic,
                byte[] key
        );

        // XSalsa20

        void cryptoStreamXSalsa20Keygen(byte[] key);

        boolean cryptoStreamXSalsa20(
                byte[] c,
                long cLen,
                byte[] nonce,
                byte[] key
        );

        boolean cryptoStreamXSalsa20Xor(
                byte[] cipher,
                byte[] message,
                long messageLen,
                byte[] nonce,
                byte[] key
        );

    }



    interface Lazy {

        Key cryptoStreamKeygen(Method method);

        byte[] cryptoStream(
                byte[] nonce,
                Key key,
                Method method
        );

        String cryptoStreamXor(
                String message,
                byte[] nonce,
                Key key,
                Method method
        );

        String cryptoStreamXorDecrypt(
                String cipher,
                byte[] nonce,
                Key key,
                Method method
        );

        String cryptoStreamXorIc(
                String message,
                byte[] nonce,
                long ic,
                Key key,
                Method method
        );

        String cryptoStreamXorIcDecrypt(
                String cipher,
                byte[] nonce,
                long ic,
                Key key,
                Method method
        );

    }

}
