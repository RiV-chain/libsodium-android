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

package org.rivchain.libsodium_java;import com.sun.jna.Pointer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecureMemoryTest extends BaseTest {


    @Test
    public void memZero() {
        byte[] b = new byte[] { 4, 2, 2, 1 };
        boolean res = libSodium.sodiumMemZero(b, b.length);
        assertTrue(isZero(b));
    }

    @Test
    public void mLock() {
        byte[] b = new byte[] { 4, 5, 2, 1 };
        boolean res = libSodium.sodiumMLock(b, b.length);
        boolean res2 = libSodium.sodiumMUnlock(b, b.length);
        assertTrue(isZero(b));
    }

    @Test
    public void malloc() {
        int size = 10;

        Pointer ptr = libSodium.sodiumMalloc(size);

        byte[] arr = ptr.getByteArray(0, size);

        assertEquals(arr.length, size);
    }

    @Test
    public void free() {
        int size = 10;
        Pointer ptr = libSodium.sodiumMalloc(size);
        libSodium.sodiumFree(ptr);
        // If this test reached this comment it didn't segfault
        // so it passes
        assertTrue(true);
    }



    private boolean isZero(byte[] arr) {
        boolean allZeroes = true;
        for (byte b : arr) {
            if (b != 0) {
                allZeroes = false;
            }
        }
        return allZeroes;
    }


}
