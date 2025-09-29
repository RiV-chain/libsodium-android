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
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaddingTest extends BaseTest {


    @Test
    public void paddingTest1() {
        int maxBufLen = 10;
        int blockSize = 4;
        int expectedPadLength = 8;
        String padThis = "test";
        pad(padThis, blockSize, maxBufLen, expectedPadLength);
    }

    @Test
    public void paddingTest2() {
        int maxBufLen = 50;
        int blockSize = 49;
        int expectedPadLength = 49;
        String padThis = "hi";
        pad(padThis, blockSize, maxBufLen, expectedPadLength);
    }

    @Test
    public void paddingTest3() {
        int maxBufLen = 20;
        int blockSize = 3;
        int expectedPadLength = 9;
        String padThis = "zebras";
        pad(padThis, blockSize, maxBufLen, expectedPadLength);
    }

    private void pad(String padThis, int blockSize, int maxBufLen, int expectedPadLength) {
        // This asks the C side to store the final padded size
        // inside this JNA object.
        IntByReference finalPaddedLength = new IntByReference();
        int contentsLength = padThis.length();

        // Create some native memory that is of the maximum
        // size and then set the string into it.
        Pointer p = new Memory(maxBufLen);
        p.setString(0, padThis);

        // Pad
        libSodium.getSodium().sodium_pad(finalPaddedLength, p, contentsLength, blockSize, maxBufLen);

        // Test
        assertEquals(expectedPadLength, finalPaddedLength.getValue());

        // Test unpadding of this padded string
        int finalLength = finalPaddedLength.getValue();

        // Uncomment to print the string at this point
        //printString(p, finalLength);

        unPad(padThis, p, finalLength, blockSize, contentsLength);
    }

    public void unPad(String startingString, Pointer paddedPointer, int lengthOfArray, int blockSize, int expectedUnpaddedLength) {
        IntByReference unpadRef = new IntByReference();
        libSodium.getSodium().sodium_unpad(unpadRef, paddedPointer, lengthOfArray, blockSize);
        assertEquals(expectedUnpaddedLength, unpadRef.getValue());

        String finishingString = pointerToString(paddedPointer, unpadRef.getValue());
        assertEquals(startingString, finishingString);
    }

    private String pointerToString(Pointer p, int length) {
        return new String(p.getByteArray(0, length));
    }

    private void printString(Pointer p, int length) {
        System.out.println(pointerToString(p, length));
    }
}
