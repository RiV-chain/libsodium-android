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

import org.rivchain.libsodium_java.interfaces.Stream;
import org.rivchain.libsodium_java.interfaces.StreamJava;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamTest extends BaseTest {

    private String message1 = "A top secret message.";

    @Test
    public void javaXChaCha20() {
        StreamJava.Lazy streamLazy = (StreamJava.Lazy) libSodium;

        byte[] nonce = libSodium.nonce(StreamJava.XCHACHA20_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(StreamJava.Method.XCHACHA20);
        String cipher = streamLazy.cryptoStreamXor(message1, nonce, key, StreamJava.Method.XCHACHA20);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, StreamJava.Method.XCHACHA20);

        assertEquals(message1, finalMsg);
    }

    @Test
    public void javaSalsa2012() {
        StreamJava.Lazy streamLazy = (StreamJava.Lazy) libSodium;

        byte[] nonce = libSodium.nonce(StreamJava.SALSA2012_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(StreamJava.Method.SALSA20_12);
        String cipher = streamLazy.cryptoStreamXor(message1, nonce, key, StreamJava.Method.SALSA20_12);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, StreamJava.Method.SALSA20_12);

        assertEquals(message1, finalMsg);
    }

    @Test
    public void javaSalsa208() {
        StreamJava.Lazy streamLazy = (StreamJava.Lazy) libSodium;

        byte[] nonce = libSodium.nonce(StreamJava.SALSA208_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(StreamJava.Method.SALSA20_8);
        String cipher = streamLazy.cryptoStreamXor(message1, nonce, key, StreamJava.Method.SALSA20_8);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, StreamJava.Method.SALSA20_8);

        assertEquals(message1, finalMsg);
    }

    @Test
    public void chacha20() {
        byte[] c = new byte[32];
        int cLen = c.length;
        byte[] nonce = libSodium.nonce(Stream.CHACHA20_NONCEBYTES);
        byte[] key = "RANDOM_KEY_OF_32_BYTES_LENGTH121".getBytes();

        libSodium.cryptoStreamChaCha20(c, cLen, nonce, key);

        // Encrypt
        byte[] mBytes = message1.getBytes();
        byte[] cipher = new byte[mBytes.length];
        libSodium.cryptoStreamChaCha20Xor(cipher, mBytes, mBytes.length, nonce, key);

        // Decrypt
        byte[] result = new byte[mBytes.length];
        libSodium.cryptoStreamChaCha20Xor(result, cipher, cipher.length, nonce, key);

        assertEquals(message1, libSodium.str(result));
    }

    @Test
    public void lazyChacha20() {
        Stream.Lazy streamLazy = (Stream.Lazy) libSodium;

        byte[] nonce = libSodium.nonce(Stream.CHACHA20_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(Stream.Method.CHACHA20);
        String cipher = streamLazy.cryptoStreamXor(message1, nonce, key, Stream.Method.CHACHA20);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, Stream.Method.CHACHA20);

        assertEquals(message1, finalMsg);
    }

    @Test
    public void lazyChacha20Ietf() {
        Stream.Lazy streamLazy = (Stream.Lazy) libSodium;

        byte[] nonce = libSodium.nonce(Stream.CHACHA20_IETF_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(Stream.Method.CHACHA20_IETF);
        String cipher = streamLazy.cryptoStreamXor(message1, nonce, key, Stream.Method.CHACHA20_IETF);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, Stream.Method.CHACHA20_IETF);

        assertEquals(message1, finalMsg);
    }


    @Test
    public void lazySalsa20() {
        Stream.Lazy streamLazy = (Stream.Lazy) libSodium;

        String message = "Hello";

        byte[] nonce = libSodium.nonce(Stream.SALSA20_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(Stream.Method.SALSA20);
        String cipher = streamLazy.cryptoStreamXor(message, nonce, key, Stream.Method.SALSA20);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, Stream.Method.SALSA20);

        assertEquals(message, finalMsg);
    }

    @Test
    public void lazyXSalsa20() {
        Stream.Lazy streamLazy = (Stream.Lazy) libSodium;

        byte[] nonce = libSodium.nonce(Stream.XSALSA20_NONCEBYTES);
        Key key = streamLazy.cryptoStreamKeygen(Stream.Method.XSALSA20);
        String cipher = streamLazy.cryptoStreamXor(message1, nonce, key, Stream.Method.XSALSA20);
        String finalMsg = streamLazy.cryptoStreamXorDecrypt(cipher, nonce, key, Stream.Method.XSALSA20);

        assertEquals(message1, finalMsg);
    }


}
