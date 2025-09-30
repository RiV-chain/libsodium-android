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
import org.rivchain.libsodium_java.Sodium;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Key {
    private byte[] key;

    private Key(byte[] key) {
        this.key = key;
    }

    public byte[] getAsBytes() {
        return key;
    }

    public String getAsHexString() {
        return LibSodium.toHex(key);
    }

    public String getAsPlainString(Charset charset) {
        return new String(key, charset);
    }

    public String getAsPlainString() {
        return getAsPlainString(StandardCharsets.UTF_8);
    }


    /**
     * Create a Key from a hexadecimal string.
     * @param hexString A hexadecimal encoded string.
     * @return A new Key.
     */
    public static Key fromHexString(String hexString) {
        return new Key(LibSodium.toBin(hexString));
    }

    /**
     * Create a Key from a base64 string.
     * @param base64String A base64 encoded string.
     * @param base64Facade A base64 encoder for Java or Android.
     * @return A new Key.
     */
    public static Key fromBase64String(String base64String, Base64Facade base64Facade) {
        return new Key(base64Facade.decode(base64String));
    }

    /**
     * Create a Key from a base64 string. Only use this
     * if you have initialised Sodium.base64Facade either directly
     * or via calling LazySodiumJava() or LibSodiumAndroid().
     * @param base64String A base64 encoded string.
     * @return A new Key.
     */
    public static Key fromBase64String(String base64String) {
        if (Sodium.base64Facade == null) {
            throw new IllegalStateException(
                    "Sodium.base64Facade not initialised. " +
                    "Call LazySodiumJava() or LibSodiumAndroid().");
        } else {
            return fromBase64String(base64String, Sodium.base64Facade);
        }
    }

    /**
     * Create a Key from a regular, unmodified, not encoded string.
     * @param str A plain string.
     * @return A new Key.
     */
    public static Key fromPlainString(String str) {
        return new Key(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Create a Key from a regular, unmodified, not encoded string.
     * @param str A plain string.
     * @param charset The charset to use.
     * @return A new Key.
     */
    public static Key fromPlainString(String str, Charset charset) {
        return new Key(str.getBytes(charset));
    }

    /**
     * Create a Key by supplying raw bytes. The byte
     * array should not be encoded and should be from a plain string,
     * UNLESS you know what you are doing and actively want
     * to provide a byte array that has been encoded.
     * @param bytes A byte array.
     * @return A new Key.
     */
    public static Key fromBytes(byte[] bytes) {
        return new Key(bytes);
    }

    /**
     * Generate a random Key with a given size.
     * @param ls LazySodium instance as we need to get true
     *           random bytes.
     * @param size The size of the key to generate.
     * @return A new Key.
     */
    public static Key generate(LibSodium ls, int size) {
        return new Key(ls.randomBytesBuf(size));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Key)) return false;
        Key other = (Key) obj;
        return other.getAsHexString().equalsIgnoreCase(getAsHexString());
    }
}
