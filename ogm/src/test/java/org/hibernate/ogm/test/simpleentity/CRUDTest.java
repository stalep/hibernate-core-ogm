/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat, Inc. and/or its affiliates or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat, Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.ogm.test.simpleentity;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Emmanuel Bernard
 */
public class CRUDTest extends OgmTestCase {

	public void testSimpleCRUD() throws Exception {
		final Session session = openSession();

		Transaction transaction = session.beginTransaction();
		Hypothesis hyp = new Hypothesis();
		hyp.setId( "1234567890" );
		hyp.setDescription( "NP != P" );
		hyp.setPosition( 1 );
		session.persist( hyp );
		transaction.commit();

		session.clear();

		transaction = session.beginTransaction();
		final Hypothesis loadedHyp = (Hypothesis) session.get( Hypothesis.class, hyp.getId() );
		assertNotNull( "Cannot load persisted object", loadedHyp );
		assertEquals( "persist and load fails", hyp.getDescription(), loadedHyp.getDescription() );
		assertEquals( "@Column fails", hyp.getPosition(), loadedHyp.getPosition() );
		transaction.commit();

		session.clear();

		transaction = session.beginTransaction();
		loadedHyp.setDescription( "P != NP");
		session.merge( loadedHyp );
		transaction.commit();

		session.clear();

		transaction = session.beginTransaction();
		Hypothesis secondLoadedHyp = (Hypothesis) session.get( Hypothesis.class, hyp.getId() );
		assertEquals( "Merge fails", loadedHyp.getDescription(), secondLoadedHyp.getDescription() );
		session.delete( secondLoadedHyp );
		transaction.commit();

		session.clear();

		transaction = session.beginTransaction();
		assertNull( session.get( Hypothesis.class, hyp.getId() ) );
		transaction.commit();

		session.close();
	}

	public void testGeneratedValue() throws Exception {
		final Session session = openSession();

		Transaction transaction = session.beginTransaction();
		Helicopter h = new Helicopter();
		h.setName( "Eurocopter" );
		session.persist( h );
		transaction.commit();

		session.clear();

		transaction = session.beginTransaction();
		h = (Helicopter) session.get( Helicopter.class, h.getUUID() );
		session.delete( h );
		transaction.commit();

		session.close();
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
				Hypothesis.class,
				Helicopter.class
		};
	}
}
