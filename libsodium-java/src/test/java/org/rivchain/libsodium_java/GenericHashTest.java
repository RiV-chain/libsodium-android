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
import org.rivchain.libsodium_java.interfaces.GenericHash;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenericHashTest extends BaseTest {

    @Test
    public void genKey() {
        Key key = libSodium.cryptoGenericHashKeygen();
        assertNotNull(key);
    }

    @Test
    public void hash() throws SodiumException {
        String message = "https://terl.co";
        Key key = libSodium.cryptoGenericHashKeygen();
        String hash = libSodium.cryptoGenericHash(message);
        assertNotNull(hash);
    }

    @Test
    public void hashNoKey() throws SodiumException {
        String message = "https://terl.co";
        String hash = libSodium.cryptoGenericHash(message);
        assertNotNull(hash);
    }


    @Test
    public void hashMultiPartRecommended() throws SodiumException {
        String message = "The sun";
        String message2 = "is shining";

        String hash = hashMultiPart(
                GenericHash.KEYBYTES,
                GenericHash.BYTES,
                message,
                message2
        );


        assertNotNull(hash);
    }


    @Test
    public void hashMultiPartMax() throws SodiumException {
        String message = "Do not go gentle into that good night";
        String message2 = "Old age should burn and rave at close of day";
        String message3 = "Rage, rage against the dying of the light";

        String hash = hashMultiPart(
                GenericHash.KEYBYTES_MAX,
                GenericHash.BYTES_MAX,
                message,
                message2,
                message3
        );

        assertNotNull(hash);
    }


    private String hashMultiPart(int keySize, int hashSize, String... messages) throws SodiumException {

        Key key = libSodium.cryptoGenericHashKeygen(keySize);
        byte[] state = new byte[libSodium.cryptoGenericHashStateBytes()];
        libSodium.cryptoGenericHashInit(state, key, hashSize);

        for (String msg : messages) {
            libSodium.cryptoGenericHashUpdate(state, msg);
        }

        String hash = libSodium.cryptoGenericHashFinal(state, hashSize);
        return hash;
    }


}
