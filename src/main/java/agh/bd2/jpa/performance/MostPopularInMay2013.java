package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class MostPopularInMay2013 extends QueryTester {
	List resultList;

	public MostPopularInMay2013(EntityManager entityManager) {
		super("Most popular thread in may 2013", entityManager);

	}

	@Override
	public void executeQuery() {
		resultList = entityManager
				.createQuery(
						"select ft.title, COUNT(*) "
								+ "from ForumPost as fp "
								+ "inner join fp.thread as ft "
								+ "where (YEAR(fp.creationDate)=2013) and (MONTH(fp.creationDate)=5) "
								+ "group by ft.title " + "order by 2 desc")
				.setMaxResults(1).getResultList();

	}

	@Override
	public void printResult() {
		Object[] tuple = (Object[]) resultList.get(0);
		String result = (String) tuple[0];
		System.out.println(this.getName() + " result: " + result);
	}
}
