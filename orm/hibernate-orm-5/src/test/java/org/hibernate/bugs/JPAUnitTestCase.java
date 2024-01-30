package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();


		// CASE 1: works
		MyEntity myEntity1 = new MyEntity();
		MyJsonField myJsonField = new MyJsonField();
		myJsonField.setField1("field1");
		myEntity1.setMyJsonField(myJsonField);

		entityManager.persist(myEntity1);
		entityManager.getTransaction().commit();

		Query query1 = entityManager.createQuery("FROM MyEntity WHERE myJsonField = :myJsonField");
		query1.setParameter("myJsonField", myJsonField);
		assert myEntity1.equals(query1.getSingleResult());

		// CASE 2: null field
		entityManager.getTransaction().begin();

		MyEntity myEntity2 = new MyEntity();
		myEntity2.setMyJsonField(null);

		entityManager.persist(myEntity2);

		entityManager.getTransaction().commit();
		Query query2 = entityManager.createQuery("FROM MyEntity WHERE myJsonField = :myJsonField");
		query2.setParameter("myJsonField", null);
		// Works as expected
		assert myEntity2.equals(query2.getSingleResult());

		entityManager.close();
	}
}
