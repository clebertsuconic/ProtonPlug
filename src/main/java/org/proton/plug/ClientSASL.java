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

package org.proton.plug;

/**
 * TODO: remove this
 * Client SASL is actually more complex than this. this is a temporary hack so I can keep doing my dev
 *        Wildfly has a complete implementation of SASL much more complete than this.
 * @author Clebert Suconic
 */
public abstract class ClientSASL
{
   public abstract byte[] getBytes();
   public abstract String getName();
}
