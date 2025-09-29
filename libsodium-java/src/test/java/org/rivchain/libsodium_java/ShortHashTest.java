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
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShortHashTest extends BaseTest {


    @Test
    public void hash() throws SodiumException {
        String hashThis = "This should get hashed";

        Key key = libSodium.cryptoShortHashKeygen();
        String hash = libSodium.cryptoShortHash(hashThis, key);

        assertNotNull(hash);
    }



}
