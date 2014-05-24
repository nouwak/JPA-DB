package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class AveragePostLength extends QueryTester {

	List results;

	public AveragePostLength(EntityManager entityManager) {
		super("Average post length", entityManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeQuery() {
		results = entityManager.createQuery(
				"select avg(LEN(content)) from ForumPost").getResultList();

	}

	@Override
	public void printResult() {
		double result = (double) results.get(0);
		System.out.println(this.getName() + " result: "
				+ String.valueOf(result));

	}

}
