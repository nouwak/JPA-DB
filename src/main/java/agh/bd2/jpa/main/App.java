package agh.bd2.jpa.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import agh.bd2.jpa.pojo.ForumPost;
import agh.bd2.jpa.pojo.ForumThread;
import agh.bd2.jpa.pojo.ForumUser;
import agh.bd2.jpa.xmlparser.Parser;

public class App {
	private static EntityManagerFactory entityManagerFactory;

	public static void setUp() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("agh.bd2.jpa.Hibernate_UNIT");
		// entityManagerFactory = Persistence.createEntityManagerFactory(
		// "agh.bd2.jpa.TopLink_UNIT" );
	}

	public static void main(String[] args) {
		setUp();
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();

		initializeDatabaseFromXML(entityManager);

		entityManager.close();
		entityManagerFactory.close();
	}

	private static void initializeDatabaseFromXML(EntityManager entityManager) {
		Parser parser = new Parser();
		try {
			parser.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		entityManager.getTransaction().begin();
		for (ForumUser user : parser.getUsers()) {
			entityManager.persist(user);
		}
		for (ForumThread thread : parser.getThreads()) {
			entityManager.persist(thread);
		}
		for (ForumPost post : parser.getPosts()) {
			entityManager.persist(post);
		}

		entityManager.getTransaction().commit();
	}
}
