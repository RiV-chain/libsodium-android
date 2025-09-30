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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SodiumJavaTest {

    @Test
    @Disabled
    // This test is ignored for two reasons:
    //  - We cannot assume that libsodium is installed on any machine
    //  - Loading is a no-op if libsodium has already been loaded by another test (say, from resources)
    // It is supposed to work with 'sodium', 'libsodium.so' (platform dependent) and
    // '/usr/lib/x86_64-linux-gnu/libsodium.so'
    public void canLoadWithSystemLibrary() {
        SodiumJava sodium = new SodiumJava("sodium");
        int initResult = sodium.sodium_init();
        assertNotEquals(-1, initResult);
    }
}
