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

package org.proton.plug.test.minimalclient;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.proton.plug.AMQPConnection;
import org.proton.plug.context.ProtonConnectionCallback;
import org.proton.plug.context.ProtonSessionCallback;
import org.proton.plug.util.ByteUtil;
import org.proton.plug.util.DebugInfo;
import org.proton.plug.util.ReusableLatch;

/**
 * @author Clebert Suconic
 */

public class AMQPClientSPI implements ProtonConnectionCallback
{

   final Channel channel;
   protected AMQPConnection connection;

   public AMQPClientSPI(Channel channel)
   {
      this.channel = channel;
   }

   public void setConnection(AMQPConnection connection)
   {
      this.connection = connection;
   }

   public AMQPConnection getConnection()
   {
      return connection;
   }

   @Override
   public void close()
   {

   }

   final ReusableLatch latch = new ReusableLatch(0);

   @Override
   public void onTransport(final ByteBuf bytes, final AMQPConnection connection)
   {
      if (DebugInfo.debug)
      {
         ByteUtil.debugFrame("Bytes leaving client", bytes);
      }

      final int bufferSize = bytes.writerIndex();


      latch.countUp();

      channel.writeAndFlush(bytes).addListener(new ChannelFutureListener()
      {
         @Override
         public void operationComplete(ChannelFuture future) throws Exception
         {
//            connection.outputDone(bufferSize);
            latch.countDown();
         }
      });
      connection.outputDone(bufferSize);

      try
      {
         if (!latch.await(1, TimeUnit.SECONDS))
         {
            // TODO logs
            System.err.println("Flush took longer than 5 seconds!!!");
         }
      }
      catch (Throwable e)
      {
         e.printStackTrace();
      }

   }

   @Override
   public ProtonSessionCallback createSessionSPI(AMQPConnection connection)
   {
      return null;
   }
}
