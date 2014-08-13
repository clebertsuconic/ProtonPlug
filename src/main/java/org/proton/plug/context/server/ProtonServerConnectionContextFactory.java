/*
 * Copyright 2005-2014 Red Hat, Inc.
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.proton.plug.context.server;

import org.proton.plug.AMQPConnection;
import org.proton.plug.AMQPConnectionFactory;
import org.proton.plug.context.ProtonConnectionCallback;

/**
 * @author Clebert Suconic
 */

public class ProtonServerConnectionContextFactory extends AMQPConnectionFactory
{
   private static final AMQPConnectionFactory theInstance = new ProtonServerConnectionContextFactory();
   public static AMQPConnectionFactory getFactory()
   {
      return theInstance;
   }

   public AMQPConnection createConnection(ProtonConnectionCallback connectionCallback)
   {
      ProtonServerConnectionContext connection = new ProtonServerConnectionContext(connectionCallback);
      return connection;
   }
}
