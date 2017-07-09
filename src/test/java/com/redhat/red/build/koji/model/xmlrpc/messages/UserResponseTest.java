/**
 * Copyright (C) 2015 Red Hat, Inc. (jcasey@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.impl.estream.EventStreamGeneratorImpl;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserStatus;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserType;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jdcasey on 12/3/15.
 */
public class UserResponseTest
        extends AbstractKojiMessageTest
{
    private static final String USER_NAME = "newcastle";

    private static final int USER_ID = 2982;

    private static final KojiUserStatus STATUS = KojiUserStatus.NORMAL;

    private static final KojiUserType USER_TYPE = KojiUserType.NORMAL;

    private static final String KRB_PRINCIPAL = "me@MYCO.COM";

    @Test
    public void verifyVsCapturedHttpRequest()
            throws Exception
    {
        bindery.render( eventParser, newResponse( true ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        List<Event<?>> xmlEvents = parseEvents( "user-krbPrincipal-response.xml" );
        assertEquals( objectEvents, xmlEvents );
    }

    @Test
    public void verifyVsCapturedHttpRequest_NilKerberosPrincipal()
            throws Exception
    {
        bindery.render( eventParser, newResponse( false ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        List<Event<?>> xmlEvents = parseEvents( "user-krbPrincipal-response.xml" );
        assertEquals( objectEvents, xmlEvents );
    }

    private UserResponse newResponse( boolean enableKerberosPrincipal )
    {
        return new UserResponse( new KojiUserInfo( STATUS, USER_TYPE, USER_ID, USER_NAME,
                                                   enableKerberosPrincipal ? KRB_PRINCIPAL : null ) );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, newResponse( true ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        UserResponse parsed = bindery.parse( generator, UserResponse.class );
        assertNotNull( parsed );
    }
}
