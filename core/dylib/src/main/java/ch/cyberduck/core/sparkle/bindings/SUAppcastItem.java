package ch.cyberduck.core.sparkle.bindings;

/*
 * Copyright (c) 2002-2023 iterate GmbH. All rights reserved.
 * https://cyberduck.io/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

import ch.cyberduck.binding.foundation.NSObject;

public abstract class SUAppcastItem extends NSObject {

    /**
     * This corresponds to the application update’s CFBundleVersion
     *
     * @return The version of the update item.
     */
    public abstract String versionString();

    /**
     * This corresponds to the application update’s CFBundleShortVersionString
     *
     * @return The human-readable display version of the update item if provided.
     */
    public abstract String displayVersionString();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SUAppcastItem{");
        sb.append("versionString='").append(versionString()).append('\'');
        sb.append(", displayVersionString='").append(displayVersionString()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
