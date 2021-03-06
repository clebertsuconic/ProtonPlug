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

package org.proton.plug.util;

/**
 * @author Clebert Suconic
 */


import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import org.apache.qpid.proton.codec.WritableBuffer;

/**
 * This is to use NettyBuffer within Proton
 * @author Clebert Suconic
 */

public class NettyWritable implements WritableBuffer
{

   final ByteBuf nettyBuffer;

   public NettyWritable(ByteBuf nettyBuffer)
   {
      this.nettyBuffer = nettyBuffer;
   }


   @Override
   public void put(byte b)
   {
      nettyBuffer.writeByte(b);
   }

   @Override
   public void putFloat(float f)
   {
      nettyBuffer.writeFloat(f);
   }

   @Override
   public void putDouble(double d)
   {
      nettyBuffer.writeDouble(d);
   }

   @Override
   public void put(byte[] src, int offset, int length)
   {
      nettyBuffer.writeBytes(src, offset, length);
   }

   @Override
   public void putShort(short s)
   {
      nettyBuffer.writeShort(s);
   }

   @Override
   public void putInt(int i)
   {
      nettyBuffer.writeInt(i);
   }

   @Override
   public void putLong(long l)
   {
      nettyBuffer.writeLong(l);
   }

   @Override
   public boolean hasRemaining()
   {
      return nettyBuffer.writerIndex() < nettyBuffer.capacity();
   }

   @Override
   public int remaining()
   {
      return nettyBuffer.capacity() - nettyBuffer.writerIndex();
   }

   @Override
   public int position()
   {
      return nettyBuffer.writerIndex();
   }

   @Override
   public void position(int position)
   {
      nettyBuffer.writerIndex(position);
   }

   @Override
   public void put(ByteBuffer payload)
   {
      nettyBuffer.writeBytes(payload);
   }

   @Override
   public int limit()
   {
      return nettyBuffer.capacity();
   }
}
