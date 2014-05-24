package agh.bd2.jpa.performance;

import javax.persistence.EntityManager;

public abstract class QueryTester {
	protected EntityManager entityManager;
	private String name;

	public String getName() {
		return name;
	}

	public abstract void executeQuery();
	public abstract void printResult();

	public QueryTester(String name, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.name = name;
	}
}
