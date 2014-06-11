package agh.bd2.jpa.performance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public class ThirtyFifthMostOftenUsedWordInPostByDatabase extends QueryTester {

    private List resultList;
    private Map<String, Integer> wordCounter = new HashMap<>();

    public ThirtyFifthMostOftenUsedWordInPostByDatabase(EntityManager entityManager) {
        super("35th most often used word in posts by database", entityManager);
    }

    @Override
    public void executeQuery() {
        resultList = entityManager.createNativeQuery("select wordData, count(*)"
                + " from thirtyFifthMostOftenUsedWord() "
                + "group by wordData "
                + "order by 2 desc").setMaxResults(1).getResultList();

    }

    @Override
    public void printResult() {
        Object[] tuple = (Object[]) resultList.get(0);
        String result = (String) tuple[0];
        System.out.println(this.getName() + " result: " + result);
    }

}
