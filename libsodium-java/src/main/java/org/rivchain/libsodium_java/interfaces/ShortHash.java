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


import org.rivchain.libsodium_java.exceptions.SodiumException;
import org.rivchain.libsodium_java.utils.Key;

public interface ShortHash {

    int SIPHASH24_BYTES = 8,
        SIPHASH24_KEYBYTES = 16,
        SIPHASHX24_BYTES = 16,
        SIPHASHX24_KEYBYTES = 16,

        BYTES = SIPHASH24_BYTES,
        KEYBYTES = SIPHASH24_KEYBYTES;



    interface Native {

        /**
         * Short-input hash some text.
         * @param out The hashed text of size {@link #SIPHASH24_BYTES} or
         *            {@link #SIPHASHX24_BYTES} depending on {@code in} size.
         * @param in The short-input text to hash of size {@link #BYTES} or of size {@link #SIPHASHX24_BYTES}.
         * @param inLen The length of the short-input.
         * @param key The key generated via {@link #cryptoShortHashKeygen(byte[])}.
         * @return true if success, false if fail.
         */
        boolean cryptoShortHash(byte[] out, byte[] in, long inLen, byte[] key);


        /**
         * Output a 64-bit key.
         * @param k The key of size {@link #SIPHASH24_KEYBYTES}.
         */
        void cryptoShortHashKeygen(byte[] k);

    }

    interface Lazy {

        /**
         * Generate a 64-bit key for short-input hashing.
         * @return Key in string format.
         */
        Key cryptoShortHashKeygen();

        /**
         * Hash a short message using a key.
         * @param in The short message to hash.
         * @param key The key generated via {@link #cryptoShortHashKeygen()}.
         * @return Your message hashed of size {@link #BYTES}.
         */
        String cryptoShortHash(String in, Key key) throws SodiumException;


    }


}
