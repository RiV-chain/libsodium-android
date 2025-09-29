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

package org.rivchain.libsodium_java.utils;

import org.rivchain.libsodium_java.LibSodium;


public class DetachedEncrypt extends Detached {

    byte[] cipher;

    public DetachedEncrypt(byte[] cipher, byte[] mac) {
        super(mac);
        this.cipher = cipher;
    }

    public byte[] getCipher() {
        return cipher;
    }

    public String getCipherString() {
        return LibSodium.toHex(getCipher());
    }


}
