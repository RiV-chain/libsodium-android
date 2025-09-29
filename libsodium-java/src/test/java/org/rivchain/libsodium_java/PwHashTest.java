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
import org.rivchain.libsodium_java.interfaces.PwHash;
import org.rivchain.libsodium_java.interfaces.Scrypt;
import com.sun.jna.NativeLong;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PwHashTest extends BaseTest {

    private final String PASSWORD = "Password123456!!!!@@";
    private PwHash.Lazy pwHashLazy;

    @BeforeAll
    public void before() {
        pwHashLazy = (PwHash.Lazy) libSodium;
    }

    @Test
    public void scryptHash() throws SodiumException {
        byte[] salt = new byte[LibSodium.longToInt(Scrypt.SCRYPTSALSA208SHA256_SALT_BYTES)];
        String scryptHash = libSodium.cryptoPwHashScryptSalsa208Sha256(
                PASSWORD,
                300L, // This can be anything up to Constants.SIZE_MAX
                salt,
                Scrypt.SCRYPTSALSA208SHA256_OPSLIMIT_MIN,
                Scrypt.SCRYPTSALSA208SHA256_MEMLIMIT_MIN
        );

        String hash = libSodium.cryptoPwHashScryptSalsa208Sha256Str(
                PASSWORD,
                Scrypt.SCRYPTSALSA208SHA256_OPSLIMIT_MIN,
                Scrypt.SCRYPTSALSA208SHA256_MEMLIMIT_MIN
        );

        boolean isCorrect = libSodium.cryptoPwHashScryptSalsa208Sha256StrVerify(hash, PASSWORD);


        assertTrue(isCorrect, "Minimum hashing failed.");
    }

    @Test
    public void nativeHash() throws SodiumException {
        String output = pwHashLazy.cryptoPwHash(
                PASSWORD,
                PwHash.BYTES_MIN,
                libSodium.randomBytesBuf(PwHash.SALTBYTES),
                5L,
                new NativeLong(8192 * 2),
                PwHash.Alg.PWHASH_ALG_ARGON2ID13
        );

        assertNotNull("Native hashing failed.", output);
    }

    @Test
    public void strMin() throws SodiumException {
        String hash = pwHashLazy.cryptoPwHashStr(
                PASSWORD,
                3,
                PwHash.MEMLIMIT_MIN
        );

        boolean isCorrect = pwHashLazy.cryptoPwHashStrVerify(hash, PASSWORD);

        assertTrue(isCorrect, "Minimum hashing failed.");
    }


    // We don't test for this as it's pretty demanding and
    // will fail on most machines
    public void cryptoPwHashStrTestSensitive() {}

}
