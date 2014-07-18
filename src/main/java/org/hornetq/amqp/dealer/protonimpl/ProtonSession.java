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

package org.hornetq.amqp.dealer.protonimpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.qpid.proton.amqp.transport.ErrorCondition;
import org.apache.qpid.proton.engine.Receiver;
import org.apache.qpid.proton.engine.Sender;
import org.apache.qpid.proton.engine.Session;
import org.hornetq.amqp.dealer.exceptions.HornetQAMQPException;
import org.hornetq.amqp.dealer.exceptions.HornetQAMQPInternalErrorException;
import org.hornetq.amqp.dealer.spi.ProtonSessionSPI;

/**
 * @author <a href="mailto:andy.taylor@jboss.org">Andy Taylor</a>
 *         4/10/13
 */
public abstract class ProtonSession extends ProtonInitializable
{
   protected final AbstractProtonConnection connection;

   protected final ProtonSessionSPI sessionSPI;

   protected final Session session;

   private long currentTag = 0;

   protected Map<Receiver, AbstractProtonReceiver> receivers = new HashMap<Receiver, AbstractProtonReceiver>();

   protected Map<Sender, AbstractProtonSender> senders = new HashMap<Sender, AbstractProtonSender>();

   protected boolean closed = false;

   public ProtonSession(ProtonSessionSPI sessionSPI, AbstractProtonConnection connection, Session session)
   {
      this.connection = connection;
      this.sessionSPI = sessionSPI;
      this.session = session;
   }

   public void initialise() throws HornetQAMQPException
   {
      if (!isInitialized())
      {
         super.initialise();

         if (sessionSPI != null)
         {
            try
            {
               sessionSPI.init(this, connection.getLogin(), connection.getPasscode());
            }
            catch (Exception e)
            {
               throw new HornetQAMQPInternalErrorException(e.getMessage(), e);
            }
         }
      }
    }


   public void disconnect(Object consumer, String queueName)
   {
      AbstractProtonSender protonConsumer = senders.remove(consumer);
      if (protonConsumer != null)
      {
         try
         {
            protonConsumer.close();
         }
         catch (HornetQAMQPException e)
         {
            protonConsumer.getSender().setTarget(null);
            protonConsumer.getSender().setCondition(new ErrorCondition(e.getAmqpError(), e.getMessage()));
         }
      }
   }


   public byte[] getTag()
   {
      return Long.toHexString(currentTag++).getBytes();
   }

   public void replaceTag(byte[] tag)
   {
      // TODO: do we need to reuse this?
   }

   public void close()
   {
      if (closed)
      {
         return;
      }

      for (AbstractProtonReceiver protonProducer : receivers.values())
      {
         try
         {
            protonProducer.close();
         }
         catch (Exception e)
         {
            e.printStackTrace();
            // TODO Logging
         }
      }
      receivers.clear();
      for (AbstractProtonSender protonConsumer : senders.values())
      {
         try
         {
            protonConsumer.close();
         }
         catch (Exception e)
         {
            e.printStackTrace();
            // TODO Logging
         }
      }
      senders.clear();
      try
      {
         new Exception ("Session.close").printStackTrace();
         if (sessionSPI != null)
         {
            sessionSPI.rollbackCurrentTX();
            sessionSPI.close();
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         // TODO logging
      }
      closed = true;
   }

   public void removeSender(Sender sender) throws HornetQAMQPException
   {
      senders.remove(sender);
   }

   public void removeReceiver(Receiver receiver)
   {
      receivers.remove(receiver);
   }
}
