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


public class KeyPair {
    private Key secretKey;
    private Key publicKey;

    public KeyPair(Key publicKey, Key secretKey) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
    }

    public Key getSecretKey() {
        return secretKey;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof KeyPair)) return false;
        KeyPair other = (KeyPair) obj;
        return other.getSecretKey().equals(getSecretKey())
                && other.getPublicKey().equals(getPublicKey());
    }
}
