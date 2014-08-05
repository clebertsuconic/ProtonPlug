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

package org.proton.plug.context.client;

import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Session;
import org.proton.plug.AMQPClientConnection;
import org.proton.plug.AMQPClientSession;
import org.proton.plug.ClientSASL;
import org.proton.plug.context.AbstractConnection;
import org.proton.plug.exceptions.HornetQAMQPException;
import org.proton.plug.context.ProtonConnectionCallback;
import org.proton.plug.context.ProtonInitializable;
import org.proton.plug.context.SessionExtension;
import org.proton.plug.context.ProtonSessionCallback;
import org.proton.plug.util.FutureRunnable;

/**
 * @author Clebert Suconic
 */

public class ProtonClientConnectionImpl extends AbstractConnection implements AMQPClientConnection
{
   public ProtonClientConnectionImpl(ProtonConnectionCallback connectionCallback)
   {
      super(connectionCallback);
   }

   // Maybe a client interface?
   public void clientOpen(ClientSASL sasl) throws Exception
   {
      FutureRunnable future = new FutureRunnable(1);
      synchronized (handler.getLock())
      {
         this.afterInit(future);
         if (sasl != null)
         {
            handler.createClientSasl(sasl);
         }
         handler.getConnection().open();
      }

      flush();

      waitWithTimeout(future);
   }

   public AMQPClientSession createClientSession() throws HornetQAMQPException
   {

      FutureRunnable futureRunnable =  new FutureRunnable(1);
      ProtonClientSessionImpl sessionImpl;
      synchronized (handler.getLock())
      {
         Session session = handler.getConnection().session();
         sessionImpl = (ProtonClientSessionImpl) getSessionExtension(session);
         sessionImpl.afterInit(futureRunnable);
         session.open();
      }

      flush();
      waitWithTimeout(futureRunnable);

      return sessionImpl;
   }

   @Override
   protected SessionExtension newSessionExtension(Session realSession) throws HornetQAMQPException
   {
      ProtonSessionCallback sessionSPI = connectionCallback.createSessionSPI(this);
      SessionExtension protonSession = new ProtonClientSessionImpl(sessionSPI, this, realSession);

      return protonSession;

   }

   @Override
   protected void remoteLinkOpened(Link link) throws Exception
   {
      Object context = link.getContext();
      if (context != null && context instanceof ProtonInitializable)
      {
         ((ProtonInitializable)context).initialise();
      }
   }
}
