package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class Threads2013Query extends QueryTester {
	List results;

	public Threads2013Query(EntityManager entityManager) {
		super("Number of threads creadted in 2013", entityManager);
	}

	@Override
	public void executeQuery() {
		results = entityManager
				.createQuery(
						"select COUNT(*) from ForumThread where year(creationDate) = 2013")
				.getResultList();

	}

	@Override
	public void printResult() {
		long result = (long) results.get(0);
		System.out.println(this.getName() + " result: "
				+ String.valueOf(result));
	}

}
