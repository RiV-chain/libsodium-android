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
import org.rivchain.libsodium_java.interfaces.KeyExchange;
import org.rivchain.libsodium_java.utils.KeyPair;
import org.rivchain.libsodium_java.utils.SessionPair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KeyExchangeTest extends BaseTest {


    @Test
    public void generateKeyPair() {
        KeyPair keys = libSodium.cryptoKxKeypair();
        assertNotNull(keys);
    }

    @Test
    public void generateDeterministicPublicKeyPair() {
        byte[] seed = new byte[KeyExchange.SEEDBYTES];
        KeyPair keys = libSodium.cryptoKxKeypair(seed);
        KeyPair keys2 = libSodium.cryptoKxKeypair(seed);

        assertEquals(keys.getPublicKey().getAsHexString(), keys2.getPublicKey().getAsHexString());
    }

    @Test
    public void generateDeterministicSecretKeyPair() {
        byte[] seed = new byte[KeyExchange.SEEDBYTES];
        KeyPair keys = libSodium.cryptoKxKeypair(seed);
        KeyPair keys2 = libSodium.cryptoKxKeypair(seed);

        assertEquals(keys.getSecretKey().getAsHexString(), keys2.getSecretKey().getAsHexString());
    }


    @Test
    public void generateSessionPair() throws SodiumException {
        // Generate the client's keypair
        KeyPair clientKeys = libSodium.cryptoKxKeypair();

        // Generate the server keypair
        KeyPair serverKeys = libSodium.cryptoKxKeypair();

        SessionPair clientSession = libSodium.cryptoKxClientSessionKeys(clientKeys, serverKeys);
        SessionPair serverSession = libSodium.cryptoKxServerSessionKeys(serverKeys, clientKeys);

        // You can now use the secret and public keys of the client and the server
        // to encrypt and decrypt messages to one another.
        // libSodium.cryptoSecretBoxEasy( ... );

        // The Rx of the client should equal the Tx of the server
        assertEquals(clientSession.getRxString(), serverSession.getTxString());
    }

}
