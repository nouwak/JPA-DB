package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class PostsWithFrodo extends QueryTester {

	List results;

	public PostsWithFrodo(EntityManager entityManager) {
		super("Number of posts containing word 'Frodo'", entityManager);
	}

	@Override
	public void executeQuery() {
		// results = entityManager.createQuery(
		// "select count(*) from ForumPost where content like '%Frodo%'")
		// .getResultList();
		results = entityManager
				.createNativeQuery(
						"select count(*) from ForumPost where contains(content, 'Frodo')")
				.getResultList();

	}

	@Override
	public void printResult() {
		// long result = (long) results.get(0);
		// System.out.println(this.getName() + " result: "
		// + String.valueOf(result));
		long result = (int) results.get(0);
		System.out.println(this.getName() + " result: "
				+ String.valueOf(result));

	}

}
