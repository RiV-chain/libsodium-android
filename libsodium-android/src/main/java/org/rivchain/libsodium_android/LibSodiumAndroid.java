/*
 * Copyright (c) Terl Tech Ltd • 04/04/2020, 00:05 • goterl.com
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.rivchain.libsodium_android;

import org.rivchain.libsodium_java.LibSodium;
import org.rivchain.libsodium_java.interfaces.MessageEncoder;
import org.rivchain.libsodium_java.utils.HexMessageEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class LibSodiumAndroid extends LibSodium {

    private final SodiumAndroid sodium;

    public LibSodiumAndroid(SodiumAndroid sodium) {
        this(sodium, StandardCharsets.UTF_8, new HexMessageEncoder());
    }

    public LibSodiumAndroid(SodiumAndroid sodium, Charset charset) {
        this(sodium, charset, new HexMessageEncoder());
    }

    public LibSodiumAndroid(SodiumAndroid sodium, MessageEncoder messageEncoder) {
        this(sodium, StandardCharsets.UTF_8, messageEncoder);
    }

    public LibSodiumAndroid(SodiumAndroid sodium, Charset charset, MessageEncoder messageEncoder) {
        super(charset, messageEncoder);
        this.sodium = sodium;
    }

    public SodiumAndroid getSodium() {
        return sodium;
    }

}