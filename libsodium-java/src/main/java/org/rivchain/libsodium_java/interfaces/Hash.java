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
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public interface Hash {

    int SHA256_BYTES = 32,
        SHA512_BYTES = 64,
        BYTES = SHA512_BYTES;

    interface Native {

        boolean cryptoHashSha256(byte[] out, byte[] in, long inLen);

        boolean cryptoHashSha512(byte[] out, byte[] in, long inLen);


        boolean cryptoHashSha256Init(Hash.State256 state);

        boolean cryptoHashSha256Update(Hash.State256 state,
                                                    byte[] in,
                                                    long inLen);

        boolean cryptoHashSha256Final(Hash.State256 state, byte[] out);


        boolean cryptoHashSha512Init(Hash.State512 state);

        boolean cryptoHashSha512Update(Hash.State512 state,
                                       byte[] in,
                                       long inLen);

        boolean cryptoHashSha512Final(Hash.State512 state, byte[] out);

    }

    interface Lazy {

        String cryptoHashSha256(String message) throws SodiumException;

        String cryptoHashSha512(String message) throws SodiumException;

        boolean cryptoHashSha256Init(Hash.State256 state);

        boolean cryptoHashSha256Update(Hash.State256 state, String messagePart);

        String cryptoHashSha256Final(Hash.State256 state) throws SodiumException;

        boolean cryptoHashSha512Init(Hash.State512 state);

        boolean cryptoHashSha512Update(Hash.State512 state, String messagePart);

        String cryptoHashSha512Final(Hash.State512 state) throws SodiumException;

    }


    class State256 extends Structure {

        public static class ByReference extends State256 implements Structure.ByReference { }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("state", "count", "buf");
        }

        public long[] state = new long[8];
        public long count;
        public byte[] buf = new byte[64];

    }

    class State512 extends Structure {

        public static class ByReference extends State512 implements Structure.ByReference { }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("state", "count", "buf");
        }

        public long[] state = new long[8];
        public long[] count = new long[2];
        public byte[] buf = new byte[128];

    }

}
