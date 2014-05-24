package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class UserInMostThreads extends QueryTester {

	List resultList;

	public UserInMostThreads(EntityManager entityManager) {
		super("User talking in most threads", entityManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeQuery() {
		resultList = entityManager.createNativeQuery(
				"select f.login, COUNT(*) as number " + "from "
						+ "(select  fu.login, thread "
						+ "from ForumPost as fp "
						+ "inner join ForumUser as fu "
						+ "on fu.id = fp.author "
						+ "group by fu.login, thread) as f "
						+ "group by f.login " + "order by 2 desc")
				.getResultList();

	}

	@Override
	public void printResult() {
		Object[] tuple = (Object[]) resultList.get(0);
		String result = (String) tuple[0];
		System.out.println(this.getName() + " result: " + result);
	}

}
