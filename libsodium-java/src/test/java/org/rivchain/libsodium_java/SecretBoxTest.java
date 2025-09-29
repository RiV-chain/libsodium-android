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
import org.rivchain.libsodium_java.interfaces.SecretBox;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecretBoxTest extends BaseTest {


    private SecretBox.Lazy secretBoxLazy;

    @BeforeAll
    public void before() {
        secretBoxLazy = (SecretBox.Lazy) libSodium;
    }


    @Test
    public void encrypt() throws SodiumException {
        String message = "This is a super secret message.";

        // Generate a symmetric key to encrypt the message.
        Key key = secretBoxLazy.cryptoSecretBoxKeygen();

        // Generate a random nonce.
        byte[] nonce = libSodium.nonce(SecretBox.NONCEBYTES);
        String cipher = secretBoxLazy.cryptoSecretBoxEasy(message, nonce, key);
        String decrypted = secretBoxLazy.cryptoSecretBoxOpenEasy(cipher, nonce, key);

        assertEquals(decrypted, message);
    }

}
