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

package org.rivchain.libsodium_java.interfaces;


public interface Helpers {

    interface Native {
        int sodiumInit();
    }

    interface Lazy {

        /**
         * Binary to hexadecimal. This method does not null terminate strings.
         * @param bin The binary bytes you want to convert to a string.
         * @return A hexadecimal string solely made up of the characters 0123456789ABCDEF.
         */
        String sodiumBin2Hex(byte[] bin);

        /**
         * Hexadecimal to binary. Does not null terminate the binary
         * array.
         * @param hex Hexadecimal string (a string that's
         *            made up of the characters 0123456789ABCDEF)
         *            to convert to a binary array.
         * @return Binary byte array.
         */
        byte[] sodiumHex2Bin(String hex);


    }


}
