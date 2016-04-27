/*
 Licensed to Diennea S.r.l. under one
 or more contributor license agreements. See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership. Diennea S.r.l. licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.

 */
package blazingcache.client.impl;

import blazingcache.client.CacheException;
import blazingcache.client.EntrySerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Standard serializer which uses standard Java serialization
 *
 * @author enrico.olivelli
 */
public class JDKEntrySerializer implements EntrySerializer {

    @Override
    public byte[] serializeObject(String key, Object object) throws CacheException {
        try {
            ByteArrayOutputStream oo = new ByteArrayOutputStream();
            ObjectOutputStream oo2 = new ObjectOutputStream(oo);
            oo2.writeUnshared(object);
            oo2.close();
            return oo.toByteArray();
        } catch (IOException | SecurityException err) {
            throw new CacheException(err);
        }
    }

    @Override
    public Object deserializeObject(String key, byte[] value) throws CacheException {
        try {
            ByteArrayInputStream oo = new ByteArrayInputStream(value);
            ObjectInputStream oo2 = new ObjectInputStream(oo);
            return oo2.readUnshared();
        } catch (IOException | SecurityException | ClassNotFoundException err) {
            throw new CacheException(err);
        }
    }

}