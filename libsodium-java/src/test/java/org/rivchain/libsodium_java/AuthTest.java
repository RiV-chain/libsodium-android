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

import org.rivchain.libsodium_java.exceptions.SodiumException;
import org.rivchain.libsodium_java.interfaces.Auth;
import org.rivchain.libsodium_java.utils.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AuthTest extends BaseTest {

    @Test
    public void authKeygenAndVerify() throws SodiumException {
        String m = "A simple message.";

        Key key = libSodium.cryptoAuthKeygen();
        String tag = libSodium.cryptoAuth(m, key);

        boolean verification = libSodium.cryptoAuthVerify(tag, m, key);

        assertTrue(verification);
    }

    @Test
    public void auth256KeygenAndVerify() {
        String m = "A simple message.";

        Key k = libSodium.cryptoAuthHMACShaKeygen(Auth.Type.SHA256);
        String shaResult = libSodium.cryptoAuthHMACSha(Auth.Type.SHA256, m, k);
        boolean isTrue = libSodium.cryptoAuthHMACShaVerify(Auth.Type.SHA256, shaResult, m, k);
        assertTrue(isTrue);
    }

    @Test
    public void auth512KeygenAndVerify() {
        String m = "A simple message.";

        Key k = libSodium.cryptoAuthHMACShaKeygen(Auth.Type.SHA512);
        String shaResult = libSodium.cryptoAuthHMACSha(Auth.Type.SHA512, m, k);
        boolean isTrue = libSodium.cryptoAuthHMACShaVerify(Auth.Type.SHA512, shaResult, m, k);
        assertTrue(isTrue);
    }

    @Test
    public void auth512256KeygenAndVerify() {
        String m = "Follow us on twitter @terlacious";

        Key k = libSodium.cryptoAuthHMACShaKeygen(Auth.Type.SHA512256);
        String shaResult = libSodium.cryptoAuthHMACSha(Auth.Type.SHA512256, m, k);
        boolean isTrue = libSodium.cryptoAuthHMACShaVerify(Auth.Type.SHA512256, shaResult, m, k);
        assertTrue(isTrue);
    }

    @Test
    public void auth256StreamKeygenAndVerify() throws SodiumException {
        String m = "Terl is ";
        String m2 = "the best";

        Key k = libSodium.cryptoAuthHMACShaKeygen(Auth.Type.SHA256);
        Auth.StateHMAC256 state = new Auth.StateHMAC256();


        boolean res = libSodium.cryptoAuthHMACShaInit(state, k);
        if (!res) {
            fail("Could not initialise HMAC Sha.");
            return;
        }

        boolean res2 = libSodium.cryptoAuthHMACShaUpdate(state, m);
        if (!res2) {
            fail("Could not update HMAC Sha.");
            return;
        }

        boolean res3 = libSodium.cryptoAuthHMACShaUpdate(state, m2);
        if (!res3) {
            fail("Could not update HMAC Sha (part 2).");
            return;
        }

        String sha = libSodium.cryptoAuthHMACShaFinal(state);

        boolean isTrue = libSodium.cryptoAuthHMACShaVerify(Auth.Type.SHA256, sha, m + m2, k);
        assertTrue(isTrue);
    }


    @Test
    public void auth512StreamKeygenAndVerify() throws SodiumException {
        String m = "Lazysodium makes devs lazy";
        String m2 = " but don't tell your manager that!";

        Key k = libSodium.cryptoAuthHMACShaKeygen(Auth.Type.SHA512);
        Auth.StateHMAC512 state = new Auth.StateHMAC512();


        boolean res = libSodium.cryptoAuthHMACShaInit(state, k);
        if (!res) {
            fail("Could not initialise HMAC Sha.");
            return;
        }

        boolean res2 = libSodium.cryptoAuthHMACShaUpdate(state, m);
        if (!res2) {
            fail("Could not update HMAC Sha.");
            return;
        }

        boolean res3 = libSodium.cryptoAuthHMACShaUpdate(state, m2);
        if (!res3) {
            fail("Could not update HMAC Sha (part 2).");
            return;
        }

        String sha = libSodium.cryptoAuthHMACShaFinal(state);

        boolean isTrue = libSodium.cryptoAuthHMACShaVerify(Auth.Type.SHA512, sha, m + m2, k);
        assertTrue(isTrue);
    }


    @Test
    public void auth512256StreamKeygenAndVerify() throws SodiumException {
        String m = "A string that ";
        String m2 = "is sha512256 sha mac'd ";
        String m3 = "is super secure.";

        Key k = libSodium.cryptoAuthHMACShaKeygen(Auth.Type.SHA512256);
        Auth.StateHMAC512256 state = new Auth.StateHMAC512256();


        boolean res = libSodium.cryptoAuthHMACShaInit(state, k);
        boolean res2 = libSodium.cryptoAuthHMACShaUpdate(state, m);
        boolean res3 = libSodium.cryptoAuthHMACShaUpdate(state, m2);
        boolean res4 = libSodium.cryptoAuthHMACShaUpdate(state, m3);

        String sha = libSodium.cryptoAuthHMACShaFinal(state);

        boolean isTrue = libSodium.cryptoAuthHMACShaVerify(Auth.Type.SHA512256, sha, m + m2 + m3, k);
        assertTrue(isTrue);
    }
}
