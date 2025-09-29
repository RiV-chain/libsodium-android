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
import org.rivchain.libsodium_java.utils.DetachedDecrypt;
import org.rivchain.libsodium_java.utils.DetachedEncrypt;
import org.rivchain.libsodium_java.utils.KeyPair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests public and private key encryption.
 */
public class BoxTest extends BaseTest {


    private Box.Lazy cryptoBoxLazy;

    @BeforeAll
    public void before() {
        cryptoBoxLazy = (Box.Lazy) libSodium;
    }

    @Test
    public void generateKeyPair() throws SodiumException {
        KeyPair keys = cryptoBoxLazy.cryptoBoxKeypair();
        assertNotNull(keys);
    }

    @Test
    public void generateDeterministicPublicKeyPair() throws SodiumException {
        byte[] seed = new byte[Box.SEEDBYTES];
        KeyPair keys = cryptoBoxLazy.cryptoBoxSeedKeypair(seed);
        KeyPair keys2 = cryptoBoxLazy.cryptoBoxSeedKeypair(seed);

        assertEquals(keys.getPublicKey().getAsHexString(), keys2.getPublicKey().getAsHexString());
    }

    @Test
    public void generateDeterministicSecretKeyPair() throws SodiumException {
        byte[] seed = new byte[Box.SEEDBYTES];
        KeyPair keys = cryptoBoxLazy.cryptoBoxSeedKeypair(seed);
        KeyPair keys2 = cryptoBoxLazy.cryptoBoxSeedKeypair(seed);

        assertEquals(keys.getSecretKey().getAsHexString(), keys2.getSecretKey().getAsHexString());
    }


    @Test
    public void encryptMessage() throws SodiumException {
        String message = "This should get encrypted";

        // Generate the client's keypair
        KeyPair clientKeys = cryptoBoxLazy.cryptoBoxKeypair();

        // Generate the server keypair
        KeyPair serverKeys = cryptoBoxLazy.cryptoBoxKeypair();


        // We're going to encrypt a message on the client and
        // send it to the server.
        byte[] nonce = libSodium.nonce(Box.NONCEBYTES);
        KeyPair encryptionKeyPair = new KeyPair(serverKeys.getPublicKey(), clientKeys.getSecretKey());
        String encrypted = cryptoBoxLazy.cryptoBoxEasy(message, nonce, encryptionKeyPair);

        // ... In this space, you can theoretically send encrypted to
        // the server.

        // Now we can decrypt the encrypted message.
        KeyPair decryptionKeyPair = new KeyPair(clientKeys.getPublicKey(), serverKeys.getSecretKey());
        String decryptedMessage = cryptoBoxLazy.cryptoBoxOpenEasy(encrypted, nonce, decryptionKeyPair);

        // Public-private key encryption complete!
        assertEquals(message, decryptedMessage);
    }


    @Test
    public void encryptMessageBeforeNm() throws SodiumException {
        String message = "This should get encrypted";

        // Generate a keypair
        KeyPair keyPair = cryptoBoxLazy.cryptoBoxKeypair();

        // Generate a shared key which can be used
        // to encrypt and decrypt data
        String sharedKey = cryptoBoxLazy.cryptoBoxBeforeNm(keyPair);

        byte[] nonce = libSodium.nonce(Box.NONCEBYTES);

        // Encrypt the data using the shared key
        String encrypted = cryptoBoxLazy.cryptoBoxEasyAfterNm(message, nonce, sharedKey);

        // Decrypt the data using the shared key
        String decryptedMessage = cryptoBoxLazy.cryptoBoxOpenEasyAfterNm(encrypted, nonce, sharedKey);

        assertEquals(message, decryptedMessage);
    }

    @Test
    public void encryptMessageBeforeNmDetached() throws SodiumException {
        String message = "This should get encrypted";

        // Generate a keypair
        KeyPair keyPair = cryptoBoxLazy.cryptoBoxKeypair();

        // Generate a shared key which can be used
        // to encrypt and decrypt data
        String sharedKey = cryptoBoxLazy.cryptoBoxBeforeNm(keyPair);

        byte[] nonce2 = libSodium.nonce(Box.NONCEBYTES);

        // Use the detached functions
        DetachedEncrypt encDet = cryptoBoxLazy.cryptoBoxDetachedAfterNm(message, nonce2, sharedKey);
        DetachedDecrypt decryptDet = cryptoBoxLazy.cryptoBoxOpenDetachedAfterNm(encDet, nonce2, sharedKey);

        assertEquals(message, libSodium.str(decryptDet.getMessage()));
    }

    @Test
    public void sealMessage() throws SodiumException {
        String message = "This should get encrypted";

        // Generate the keypair
        KeyPair keyPair = cryptoBoxLazy.cryptoBoxKeypair();

        // Encrypt the message
        String encrypted = cryptoBoxLazy.cryptoBoxSealEasy(message, keyPair.getPublicKey());

        // Now we can decrypt the encrypted message.
        String decryptedMessage = cryptoBoxLazy.cryptoBoxSealOpenEasy(encrypted, keyPair);

        // Public-private key encryption complete!
        assertEquals(message, decryptedMessage);
    }
}
