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


import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Padding {

    interface Native {

        /**
         * Adds extra padding to a buffer {@code buf} whose
         * original size is {@code unpaddedBufLen} in order
         * to extend its total length to a multiple of {@code blocksize}.
         * @param paddedBuffLen New length of buffer.
         * @param buf The buffer byte array.
         * @param unpaddedBufLen The length of {@code buf} with no padding.
         * @param blockSize Block size.
         * @param maxBufLen The absolute maximum you want this buffer length
         *                  to be.
         * @return False if the padded buffer length would exceed {@code maxBufLen}.
         */
        boolean sodiumPad(IntByReference paddedBuffLen, Pointer buf, int unpaddedBufLen, int blockSize, int maxBufLen);

        /**
         * Computes the original, unpadded length of a message previously padded using
         * {@link #sodiumPad(IntByReference, Pointer, int, int, int)}. The original length is put into
         * {@code unpaddedBufLen}.
         * @param unpaddedBufLen This will be populated with the unpadded buffer length.
         * @param buf The buffer.
         * @param paddedBufLen The padded buffer size.
         * @param blockSize The block size.
         * @return True if the buffer was unpadded.
         */
        boolean sodiumUnpad(IntByReference unpaddedBufLen, Pointer buf, int paddedBufLen, int blockSize);
    }

    interface Lazy {

    }


}
