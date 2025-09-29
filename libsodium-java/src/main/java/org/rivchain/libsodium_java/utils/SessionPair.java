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

public class SessionPair {
    private byte[] rx;
    private byte[] tx;

    public SessionPair(byte[] rx, byte[] tx) {
        this.rx = rx;
        this.tx = tx;
    }

    public SessionPair(String rx, String tx) {
        this.rx = LibSodium.toBin(rx);
        this.tx =  LibSodium.toBin(tx);
    }

    public byte[] getRx() {
        return rx;
    }

    public byte[] getTx() {
        return tx;
    }

    public String getRxString() {
        return LibSodium.toHex(rx);
    }

    public String getTxString() {
        return LibSodium.toHex(tx);
    }
}
