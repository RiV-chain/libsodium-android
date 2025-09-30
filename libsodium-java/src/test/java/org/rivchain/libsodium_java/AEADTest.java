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

import org.rivchain.libsodium_java.interfaces.AEAD;
import org.rivchain.libsodium_java.interfaces.MessageEncoder;
import org.rivchain.libsodium_java.utils.DetachedDecrypt;
import org.rivchain.libsodium_java.utils.DetachedEncrypt;
import org.rivchain.libsodium_java.utils.HexMessageEncoder;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import javax.crypto.AEADBadTagException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AEADTest extends BaseTest {

    private final String PASSWORD = "superSecurePassword";
    private final MessageEncoder encoder = new HexMessageEncoder();

    @Test
    public void encryptChacha() throws AEADBadTagException {

        Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305);

        byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_NPUBBYTES);

        String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);
        String decrypted = libSodium.decrypt(cipher, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);

        assertEquals(decrypted, PASSWORD);
    }

    @Test
    public void encryptChachaMalformedCipher() throws AEADBadTagException {
        assertThrows(AEADBadTagException.class, () -> {
            Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305);

            byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_NPUBBYTES);

            String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);
            String decrypted = libSodium.decrypt(malformCipher(cipher), null, nPub, key, AEAD.Method.CHACHA20_POLY1305);
        });
    }

    @Test
    public void encryptChachaIetf() throws AEADBadTagException {

        Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305_IETF);

        byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_IETF_NPUBBYTES);

        String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
        String decrypted = libSodium.decrypt(cipher, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);

        assertEquals(decrypted, PASSWORD);
    }

    @Test
    public void encryptChachaIetfMalformedCipher() throws AEADBadTagException {
        assertThrows(AEADBadTagException.class, () -> {
            Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305_IETF);

            byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_IETF_NPUBBYTES);

            String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
            String decrypted = libSodium.decrypt(malformCipher(cipher), null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
        });
    }

    @Test
    public void encryptXChacha() throws AEADBadTagException {

        Key key = libSodium.keygen(AEAD.Method.XCHACHA20_POLY1305_IETF);

        byte[] nPub = libSodium.nonce(AEAD.XCHACHA20POLY1305_IETF_NPUBBYTES);

        String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);
        String decrypted = libSodium.decrypt(cipher, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);

        assertEquals(decrypted, PASSWORD);
    }

    @Test
    public void encryptXChachaMalformedCipher() throws AEADBadTagException {
        assertThrows(AEADBadTagException.class, () -> {
            Key key = libSodium.keygen(AEAD.Method.XCHACHA20_POLY1305_IETF);

            byte[] nPub = libSodium.nonce(AEAD.XCHACHA20POLY1305_IETF_NPUBBYTES);

            String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);
            String decrypted = libSodium.decrypt(malformCipher(cipher), null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);
        });
    }

    @Test
    public void encryptChachaDetached() throws AEADBadTagException {

        Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305);

        byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_NPUBBYTES);

        DetachedEncrypt detachedEncrypt
                = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);

        DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(detachedEncrypt, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);

        assertEquals(detachedDecrypt.getMessageString(), PASSWORD);
    }

    @Test
    public void encryptChachaDetachedMalformedCipher() throws AEADBadTagException {
        assertThrows(AEADBadTagException.class, () -> {
            Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305);

            byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_NPUBBYTES);

            DetachedEncrypt detachedEncrypt
                    = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);

            DetachedEncrypt malformed = new DetachedEncrypt(malformCipherBytes(detachedEncrypt.getCipherString()), detachedEncrypt.getMac());
            DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(malformed, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305);
        });
    }


    @Test
    public void encryptChachaIetfDetached() throws AEADBadTagException {
        Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305_IETF);
        byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_IETF_NPUBBYTES);

        DetachedEncrypt detachedEncrypt
                = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
        DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(detachedEncrypt, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
        assertEquals(detachedDecrypt.getMessageString(), PASSWORD);
    }

    @Test
    public void encryptChachaIetfDetachedMalformedCipher() {
        assertThrows(AEADBadTagException.class, () -> {
            Key key = libSodium.keygen(AEAD.Method.CHACHA20_POLY1305_IETF);
            byte[] nPub = libSodium.nonce(AEAD.CHACHA20POLY1305_IETF_NPUBBYTES);

            DetachedEncrypt detachedEncrypt
                    = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
            DetachedEncrypt malformed = new DetachedEncrypt(malformCipherBytes(detachedEncrypt.getCipherString()), detachedEncrypt.getMac());
            DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(malformed, null, null, nPub, key, AEAD.Method.CHACHA20_POLY1305_IETF);
        });
    }

    @Test
    public void encryptXChachaDetached() throws AEADBadTagException {
        Key key = libSodium.keygen(AEAD.Method.XCHACHA20_POLY1305_IETF);
        byte[] nPub = libSodium.nonce(AEAD.XCHACHA20POLY1305_IETF_NPUBBYTES);

        DetachedEncrypt detachedEncrypt
                = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);

        DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(detachedEncrypt, null, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);
        assertEquals(detachedDecrypt.getMessageString(), PASSWORD);
    }

    @Test
    public void encryptXChachaDetachedMalformedCipher() throws AEADBadTagException {
        assertThrows(AEADBadTagException.class, () -> {
            Key key = libSodium.keygen(AEAD.Method.XCHACHA20_POLY1305_IETF);
            byte[] nPub = libSodium.nonce(AEAD.XCHACHA20POLY1305_IETF_NPUBBYTES);

            DetachedEncrypt detachedEncrypt
                    = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);
            DetachedEncrypt malformed = new DetachedEncrypt(malformCipherBytes(detachedEncrypt.getCipherString()), detachedEncrypt.getMac());
            DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(malformed, null, null, nPub, key, AEAD.Method.XCHACHA20_POLY1305_IETF);
        });
    }


    @Test
    public void encryptAES() throws AEADBadTagException {
        if (libSodium.cryptoAeadAES256GCMIsAvailable()) {
            Key key = libSodium.keygen(AEAD.Method.AES256GCM);

            byte[] nPub = libSodium.nonce(AEAD.AES256GCM_NPUBBYTES);

            String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.AES256GCM);
            String decrypted = libSodium.decrypt(cipher, null, nPub, key, AEAD.Method.AES256GCM);

            assertEquals(decrypted, PASSWORD);
        }
    }

    @Test
    public void encryptAESMalformedCipher() {
        if (libSodium.cryptoAeadAES256GCMIsAvailable()) {
            assertThrows(AEADBadTagException.class, () -> {
                Key key = libSodium.keygen(AEAD.Method.AES256GCM);

                byte[] nPub = libSodium.nonce(AEAD.AES256GCM_NPUBBYTES);

                String cipher = libSodium.encrypt(PASSWORD, null, nPub, key, AEAD.Method.AES256GCM);
                String decrypted = libSodium.decrypt(malformCipher(cipher), null, nPub, key, AEAD.Method.AES256GCM);

                assertEquals(decrypted, PASSWORD);
            });
        }
    }

    @Test
    public void encryptAESDetached() throws AEADBadTagException {
        if (libSodium.cryptoAeadAES256GCMIsAvailable()) {
            Key key = libSodium.keygen(AEAD.Method.AES256GCM);
            byte[] nPub = libSodium.nonce(AEAD.AES256GCM_NPUBBYTES);
            DetachedEncrypt detachedEncrypt
                    = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.AES256GCM);
            DetachedDecrypt detachedDecrypt = libSodium.decryptDetached(detachedEncrypt, null, null, nPub, key, AEAD.Method.AES256GCM);
            assertEquals(detachedDecrypt.getMessageString(), PASSWORD);
        }
    }

    @Test
    public void encryptAESDetachedMalformedCipher() throws AEADBadTagException {
        if (libSodium.cryptoAeadAES256GCMIsAvailable()) {
            assertThrows(AEADBadTagException.class, () -> {
                Key key = libSodium.keygen(AEAD.Method.AES256GCM);
                byte[] nPub = libSodium.nonce(AEAD.AES256GCM_NPUBBYTES);

                DetachedEncrypt detachedEncrypt
                        = libSodium.encryptDetached(PASSWORD, null, null, nPub, key, AEAD.Method.AES256GCM);
                DetachedEncrypt malformed = new DetachedEncrypt(malformCipherBytes(detachedEncrypt.getCipherString()), detachedEncrypt.getMac());
                libSodium.decryptDetached(malformed, null, null, nPub, key, AEAD.Method.AES256GCM);
            });
        }
    }

    private String malformCipher(String ciphertext) {
        byte[] malformedBuf = malformCipherBytes(ciphertext);
        return encoder.encode(malformedBuf);
    }

    private byte[] malformCipherBytes(String ciphertext) {
        byte[] cipherBuf = encoder.decode(ciphertext);
        for (int i = 0; i < cipherBuf.length; i++) {
            cipherBuf[i] ^= 0xff;
        }
        return cipherBuf;
    }
}
