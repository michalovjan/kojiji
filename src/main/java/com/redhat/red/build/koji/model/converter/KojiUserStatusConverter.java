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
package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiUserStatus;
import org.commonjava.rwx.core.Converter;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiUserStatusConverter
                implements Converter<KojiUserStatus>
{
    @Override
    public KojiUserStatus parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        return KojiUserStatus.fromInteger( (Integer) object );
    }

    @Override
    public Object render( KojiUserStatus value )
    {
        if ( value == null )
        {
            return null;
        }

        Integer intUserStatus = value.getValue();
        if ( intUserStatus == null )
        {
            return null;
        }
        else
        {
            return intUserStatus;
        }
    }
}
