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
import org.rivchain.libsodium_java.interfaces.Hash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashTest extends BaseTest {


    private String M1 = "With great power ";
    private String M2 = "comes great responsibility";
    private String MESSAGE = M1 + M2;

    @Test
    public void sha256Compare() throws SodiumException {
        String hashed1 = libSodium.cryptoHashSha256(MESSAGE);
        String hashed2 = libSodium.cryptoHashSha256(MESSAGE);
        assertNotSame(hashed1, hashed2);
    }

    @Test
    public void sha512Compare() throws SodiumException {
        String hash1 = libSodium.cryptoHashSha512(MESSAGE);
        String hash2 = libSodium.cryptoHashSha512(MESSAGE);
        assertNotSame(hash1, hash2);
    }

    @Test
    public void sha512IsLonger() throws SodiumException {
        String hash1 = libSodium.cryptoHashSha256(MESSAGE);
        String hash2 = libSodium.cryptoHashSha512(MESSAGE);
        assertTrue(hash1.length() < hash2.length());
    }

    @Test
    public void multipartSha256() throws SodiumException {
        Hash.State256 state = new Hash.State256.ByReference();
        libSodium.cryptoHashSha256Init(state);

        libSodium.cryptoHashSha256Update(state, M1);
        libSodium.cryptoHashSha256Update(state, M2);
        libSodium.cryptoHashSha256Update(state, "more text to be hashed");

        String hash = libSodium.cryptoHashSha256Final(state);
        assertNotNull(hash);
    }

    @Test
    public void multipartSha512() throws SodiumException {
        Hash.State512 state = new Hash.State512.ByReference();
        libSodium.cryptoHashSha512Init(state);

        libSodium.cryptoHashSha512Update(state, M1);
        libSodium.cryptoHashSha512Update(state, M2);
        libSodium.cryptoHashSha512Update(state, "more text to be hashed");

        String hash = libSodium.cryptoHashSha512Final(state);

        assertNotNull(hash);
    }
}
