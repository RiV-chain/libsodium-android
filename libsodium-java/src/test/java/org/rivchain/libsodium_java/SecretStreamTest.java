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
import org.rivchain.libsodium_java.interfaces.SecretStream;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecretStreamTest extends BaseTest {

    private String message1 = "Arbitrary data to encrypt";
    private String message2 = "split into";
    private String message3 = "three messages";

    @Test
    public void test1() throws SodiumException {
        Key key = libSodium.cryptoSecretStreamKeygen();

        byte[] header = libSodium.randomBytesBuf(SecretStream.HEADERBYTES);

        // Start the encryption
        SecretStream.State state = libSodium.cryptoSecretStreamInitPush(header, key);

        String c1 = libSodium.cryptoSecretStreamPush(state, message1, SecretStream.TAG_MESSAGE);
        String c2 = libSodium.cryptoSecretStreamPush(state, message2, SecretStream.TAG_MESSAGE);
        String c3 = libSodium.cryptoSecretStreamPush(state, message3, SecretStream.TAG_FINAL);

        // Start the decryption
        byte[] tag = new byte[1];

        SecretStream.State state2 = libSodium.cryptoSecretStreamInitPull(header, key);

        String decryptedMessage = libSodium.cryptoSecretStreamPull(state2, c1, tag);
        String decryptedMessage2 = libSodium.cryptoSecretStreamPull(state2, c2, tag);
        String decryptedMessage3 = libSodium.cryptoSecretStreamPull(state2, c3, tag);

        if (tag[0] == SecretStream.XCHACHA20POLY1305_TAG_FINAL) {
            assertTrue(
                    decryptedMessage.equals(message1) &&
                    decryptedMessage2.equals(message2) &&
                    decryptedMessage3.equals(message3)
            );
        }

    }




}
