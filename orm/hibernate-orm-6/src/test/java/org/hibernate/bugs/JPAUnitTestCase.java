package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.Query;
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
		// Do stuff...

		// CASE 1: works
		MyEntity myEntity1 = new MyEntity();
		MyJsonField myJson = new MyJsonField();
		myJson.setField1("field1");
		myEntity1.setMyJsonField(myJson);

		entityManager.persist(myEntity1);
		entityManager.getTransaction().commit();

		Query query1 = entityManager.createQuery("FROM MyEntity WHERE myJsonField = :myJsonField");
		query1.setParameter("myJsonField", myJson);
		System.out.println(query1.getSingleResult());

		// CASE 2: null field
		entityManager.getTransaction().begin();

		MyEntity myEntity2 = new MyEntity();
		myEntity2.setMyJsonField(null);

		entityManager.persist(myEntity2);

		entityManager.getTransaction().commit();
		Query query2 = entityManager.createQuery("FROM MyEntity WHERE myJsonField = :myJsonField");
		query2.setParameter("myJsonField", null);
		// Failed here: not found result when searching on 'null' field (it was translated to "null" string using
		// AttributeConverter in Hibernate 5, but in Hibernate 6 we don't use AttributeConverter anymore for null fields
		// See how it filters out null in Hibernate 6: https://github.com/hibernate/hibernate-orm/blob/6.4/hibernate-core/src/main/java/org/hibernate/query/sqm/internal/SqmUtil.java#L353
		assert myEntity2.equals(query2.getSingleResult());

		entityManager.close();
	}
}
