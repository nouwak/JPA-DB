package agh.bd2.jpa.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import agh.bd2.jpa.performance.AveragePostLength;
import agh.bd2.jpa.performance.MostPopularInMay2013;
import agh.bd2.jpa.performance.PostsFromCityK;
import agh.bd2.jpa.performance.PostsWithFrodo;
import agh.bd2.jpa.performance.QueryTester;
import agh.bd2.jpa.performance.Threads2013Query;
import agh.bd2.jpa.performance.UserInMostThreads;
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

	public static void main(String[] args) throws IOException {
		setUp();
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();

		FileWriter out = new FileWriter("performance.csv");

		System.out.println("Operation,time (ns)");
		out.write("Operation,time (ns)\n");

//		initializeDatabaseFromXML(entityManager, out);
		testPerformance(entityManager, out);

		entityManager.close();
		entityManagerFactory.close();
		out.close();
	}

	private static void testPerformance(EntityManager entityManager,
			FileWriter out) throws IOException {

		measureTime(new Threads2013Query(entityManager), out);
		measureTime(new MostPopularInMay2013(entityManager), out);
		measureTime(new AveragePostLength(entityManager), out);
		measureTime(new UserInMostThreads(entityManager), out);
		measureTime(new PostsWithFrodo(entityManager), out);
		measureTime(new PostsFromCityK(entityManager), out);
	}

	private static void measureTime(QueryTester tester, FileWriter out)
			throws IOException {
		long startTime = System.nanoTime();
		tester.executeQuery();
		long endTime = System.nanoTime();
		
		tester.printResult();

		String queryTime = String.valueOf(endTime - startTime);
		System.out.println(tester.getName() + "," + queryTime);
		out.write(tester.getName() + "," + queryTime + "\n");
	}

	private static void initializeDatabaseFromXML(EntityManager entityManager,
			FileWriter out) {
		Parser parser = new Parser();
		try {
			parser.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long startTime = System.nanoTime();

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

		long endTime = System.nanoTime();

		System.out.println("Total data loading time,"
				+ String.valueOf(endTime - startTime));
		try {
			out.write("Total data loading time,"
					+ String.valueOf(endTime - startTime) + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
