package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class ThirtyFifthMostUsedWordInPosts extends QueryTester {

	List results;

	public ThirtyFifthMostUsedWordInPosts(EntityManager entityManager) {
		super("Thirty fifth most used word in posts", entityManager);
	}

	@Override
	public void executeQuery() {
		results = entityManager.createQuery( //todo change me :)
				"select as, count(*) from ForumPost where content like '%Frodo%'")
				.getResultList();

	}

	@Override
	public void printResult() {
		long result = (long) results.get(0);
		System.out.println(this.getName() + " result: "
				+ String.valueOf(result));

	}

}
