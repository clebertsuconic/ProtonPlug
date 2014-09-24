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

package org.proton.plug.handler.impl;

import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Transport;
import org.proton.plug.handler.EventHandler;

/**
 *
 * This is useful for cases where you only want to implement a few methods
 * @author Clebert Suconic
 */
public class DefaultEventHandler implements EventHandler
{
   @Override
   public void onInit(Connection connection) throws Exception
   {

   }

   @Override
   public void onOpen(Connection connection) throws Exception
   {

   }

   @Override
   public void onRemoteOpen(Connection connection) throws Exception
   {

   }

   @Override
   public void onClose(Connection connection) throws Exception
   {

   }

   @Override
   public void onRemoteClose(Connection connection) throws Exception
   {
   }

   @Override
   public void onFinal(Connection connection) throws Exception
   {

   }

   @Override
   public void onInit(Session session) throws Exception
   {

   }

   @Override
   public void onOpen(Session session) throws Exception
   {

   }

   @Override
   public void onRemoteOpen(Session session) throws Exception
   {

   }

   @Override
   public void onClose(Session session) throws Exception
   {

   }

   @Override
   public void onRemoteClose(Session session) throws Exception
   {

   }

   @Override
   public void onFinal(Session session) throws Exception
   {

   }

   @Override
   public void onInit(Link link) throws Exception
   {

   }

   @Override
   public void onOpen(Link link) throws Exception
   {

   }

   @Override
   public void onRemoteOpen(Link link) throws Exception
   {

   }

   @Override
   public void onClose(Link link) throws Exception
   {

   }

   @Override
   public void onRemoteClose(Link link) throws Exception
   {

   }

   @Override
   public void onFlow(Link link) throws Exception
   {

   }

   @Override
   public void onFinal(Link link) throws Exception
   {

   }


   @Override
   public void onRemoteDetach(Link link) throws Exception
   {

   }

   @Override
   public void onDetach(Link link) throws Exception
   {

   }

   @Override
   public void onDelivery(Delivery delivery) throws Exception
   {

   }

   @Override
   public void onTransport(Transport transport) throws Exception
   {

   }
}
