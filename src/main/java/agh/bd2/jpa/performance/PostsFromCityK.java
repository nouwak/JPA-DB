package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class PostsFromCityK extends QueryTester {

	List results;

	public PostsFromCityK(EntityManager entityManager) {
		super("Posts send by users from city beginning with 'K'", entityManager);

	}

	@Override
	public void executeQuery() {
		results = entityManager.createQuery(
				"select COUNT(*) " + "from ForumPost as fp "
						+ "inner join fp.postAuthor as fu "
						+ "where fu.city like 'K%'").getResultList();

	}

	@Override
	public void printResult() {
		long result = (long) results.get(0);
		System.out.println(this.getName() + " result: "
				+ String.valueOf(result));

	}

}
