package ch.cyberduck.core.proxy;

/*
 * Copyright (c) 2002-2015 David Kocher. All rights reserved.
 * http://cyberduck.ch/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Bug fixes, suggestions and comments should be sent to feedback@cyberduck.ch
 */

import ch.cyberduck.core.AbstractTestCase;
import ch.cyberduck.core.Host;
import ch.cyberduck.core.ProtocolFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @version $Id$
 */
public class DefaultProxyFinderTest extends AbstractTestCase {

    @Test
    public void testFind() throws Exception {
        final DefaultProxyFinder proxy = new DefaultProxyFinder();
        assertEquals(Proxy.Type.DIRECT, proxy.find(new Host("cyberduck.io")).getType());
//        assertEquals(Proxy.Type.HTTP, proxy.find(new Host(ProtocolFactory.WEBDAV, "cyberduck.io")).getType());
    }

    @Test
    public void testPassiveFTP() throws Exception {
        final ProxyFinder proxy = new DefaultProxyFinder();
        assertTrue(proxy.usePassiveFTP());
    }

    @Test
    public void testExcludedLocalHost() throws Exception {
        final DefaultProxyFinder proxy = new DefaultProxyFinder();
        assertEquals(Proxy.Type.DIRECT, proxy.find(new Host("cyberduck.local")).getType());
    }

    @Test
    public void testSimpleExcluded() throws Exception {
        final DefaultProxyFinder proxy = new DefaultProxyFinder();
        assertEquals(Proxy.Type.DIRECT, proxy.find(new Host(ProtocolFactory.WEBDAV, "simple")).getType());
    }
}