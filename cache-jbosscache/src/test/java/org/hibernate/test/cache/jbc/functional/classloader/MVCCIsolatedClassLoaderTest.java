/*
 * Copyright (c) 2007, Red Hat Middleware, LLC. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, v. 2.1. This program is distributed in the
 * hope that it will be useful, but WITHOUT A WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. You should have received a
 * copy of the GNU Lesser General Public License, v.2.1 along with this
 * distribution; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Red Hat Author(s): Brian Stansberry
 */

package org.hibernate.test.cache.jbc.functional.classloader;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.hibernate.test.cache.jbc.functional.util.IsolatedCacheTestSetup;

/**
 * Optimistic locking version of IsolatedClassLoaderTest.
 * 
 * @author <a href="brian.stansberry@jboss.com">Brian Stansberry</a>
 * @version $Revision: 1 $
 */
public class MVCCIsolatedClassLoaderTest extends PessimisticIsolatedClassLoaderTest
{
   private static final String CACHE_CONFIG = "mvcc-shared";
   
   /**
    * Create a new OptimisticIsolatedClassLoaderTest.
    * 
    * @param name
    */
   public MVCCIsolatedClassLoaderTest(String name)
   {
      super(name);      
   }
   
   public static Test suite() throws Exception {
       TestSuite suite = new TestSuite(MVCCIsolatedClassLoaderTest.class);
       String[] acctClasses = { OUR_PACKAGE + ".Account", OUR_PACKAGE + ".AccountHolder" };
       return new IsolatedCacheTestSetup(suite, acctClasses, CACHE_CONFIG);
   }

   protected String getEntityCacheConfigName() {
       return CACHE_CONFIG;
   } 

}
