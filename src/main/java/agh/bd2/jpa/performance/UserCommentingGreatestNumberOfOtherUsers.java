package agh.bd2.jpa.performance;

import java.util.List;

import javax.persistence.EntityManager;

public class UserCommentingGreatestNumberOfOtherUsers extends QueryTester {
	List resultList;

	public UserCommentingGreatestNumberOfOtherUsers(EntityManager entityManager) {
		super("User commenting greatest number of other users", entityManager);

	}

	@Override
	public void executeQuery() {
		resultList = entityManager.createNativeQuery(
                                                            "select fulogin, COUNT(*) from "
                                                                + "(SELECT fu.login as fulogin, fta.login as ftalogin "
								+ "from ForumPost as fp "
								+ "inner join ForumThread as ft "
                                                                + "on fp.thread = ft.id "
                                                                + "inner join ForumUser as fta "
                                                                + "on ft.author = fta.id "
                                                                + "inner join ForumUser as fu "
                                                                + "on fp.author = fu.id "
                                                                + "group by fulogin, ftalogin) "
								+ "group by fulogin "
                                                                + "order by 2 desc")
				.setMaxResults(1).getResultList();

	}

	@Override
	public void printResult() {
		Object[] tuple = (Object[]) resultList.get(0);
		String result = (String) tuple[0];
		System.out.println(this.getName() + " result: " + result);
	}
}
