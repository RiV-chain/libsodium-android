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

import org.rivchain.libsodium_java.exceptions.SodiumException;
import org.rivchain.libsodium_java.interfaces.Box;
import org.rivchain.libsodium_java.interfaces.DiffieHellman;
import org.rivchain.libsodium_java.interfaces.SecretBox;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiffieHellmanTest extends BaseTest {

    private String clientSecretKey = "CLIENT_TOP_SECRET_KEY_1234567890";
    private String serverSecretKey = "SERVER_TOP_SECRET_KEY_1234567890";


    @Test
    public void create() throws SodiumException {
        DiffieHellman.Lazy dh = (DiffieHellman.Lazy) libSodium;
        SecretBox.Lazy box = (SecretBox.Lazy) libSodium;

        Key secretKeyC = Key.fromPlainString(clientSecretKey);
        Key publicKeyC = dh.cryptoScalarMultBase(secretKeyC);

        Key secretKeyS = Key.fromPlainString(serverSecretKey);
        Key publicKeyS = dh.cryptoScalarMultBase(secretKeyS);

        // -----
        // ON THE CLIENT
        // -----

        // Compute a shared key for sending from client
        // to server.
        Key sharedKey = dh.cryptoScalarMult(secretKeyC, publicKeyS);

        String message = "Hello";
        byte[] nonce = new byte[Box.NONCEBYTES];
        String encrypted = box.cryptoSecretBoxEasy(message, nonce, sharedKey);

        // Send 'encrypted' to server...


        // -----
        // ON THE SERVER
        // -----

        // Compute the shared key for receiving server messages from client
        Key sharedKeyServer = dh.cryptoScalarMult(secretKeyS, publicKeyC);
        String decrypted = box.cryptoSecretBoxOpenEasy(encrypted, nonce, sharedKeyServer);

        // 'decrypted' == Hello

        assertEquals(message, decrypted);
    }
}
