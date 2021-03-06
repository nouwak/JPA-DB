package agh.bd2.jpa.main;

import java.io.FileWriter;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import agh.bd2.jpa.performance.AveragePostLength;
import agh.bd2.jpa.performance.MostPopularInMay2013;
import agh.bd2.jpa.performance.PostsFromCityK;
import agh.bd2.jpa.performance.PostsWithFrodo;
import agh.bd2.jpa.performance.QueryTester;
import agh.bd2.jpa.performance.ThirtyFifthMostOftenUsedWordInPost;
import agh.bd2.jpa.performance.ThirtyFifthMostOftenUsedWordInPostByDatabase;
import agh.bd2.jpa.performance.Threads2013Query;
import agh.bd2.jpa.performance.UserCommentingGreatestNumberOfOtherUsers;
import agh.bd2.jpa.performance.UserInMostThreads;
import agh.bd2.jpa.pojo.ForumPost;
import agh.bd2.jpa.pojo.ForumThread;
import agh.bd2.jpa.pojo.ForumUser;
import agh.bd2.jpa.xmlparser.Parser;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class App {

    private static final int NUMBER_OF_REPETITIONS = 10;
    private static EntityManagerFactory entityManagerFactory;
    private static Map<String, Double> timesOfExecution = new HashMap<String, Double>();

    public static void setUp() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("agh.bd2.jpa.Hibernate_UNIT");
        // entityManagerFactory = Persistence.createEntityManagerFactory(
        // "agh.bd2.jpa.TopLink_UNIT" );
    }

    public static void main(String[] args) throws IOException {
        setUp();
        EntityManager entityManager = entityManagerFactory
                .createEntityManager();

        FileWriter out = new FileWriter("performance.csv");

        String message = "Operation,time (ns)\n";

        System.out.print(message);
        out.write(message);

        // initializeDatabaseFromXML(entityManager, out);
        testPerformance(entityManager, out);
        printResults(out);

        entityManager.close();
        entityManagerFactory.close();
        out.close();
    }

    private static void testPerformance(EntityManager entityManager,
            FileWriter out) throws IOException {
        for (int i = 0; i < NUMBER_OF_REPETITIONS; i++) {
            measureTime(new Threads2013Query(entityManager), out);
            measureTime(new MostPopularInMay2013(entityManager), out);
            measureTime(new AveragePostLength(entityManager), out);
            measureTime(new UserInMostThreads(entityManager), out);
            measureTime(
                    new UserCommentingGreatestNumberOfOtherUsers(entityManager),
                    out);
            measureTime(new PostsWithFrodo(entityManager), out);
            measureTime(new PostsFromCityK(entityManager), out);
            measureTime(new ThirtyFifthMostOftenUsedWordInPost(entityManager), out);
            measureTime(new ThirtyFifthMostOftenUsedWordInPostByDatabase(entityManager), out);

        }
    }

    private static void measureTime(QueryTester tester, FileWriter out)
            throws IOException {
        long result = 0;

        long startTime = System.nanoTime();
        tester.executeQuery();
        long endTime = System.nanoTime();
        result += endTime - startTime;

        double endResult = (double) result;
        endResult /= Math.pow(10, 6);
        String testName = tester.getName();

        if (timesOfExecution.containsKey(testName)) {
            timesOfExecution.put(testName, endResult + timesOfExecution.get(testName));
        } else {
            timesOfExecution.put(testName, endResult);
            tester.printResult();
        }

    }

    private static void initializeDatabaseFromXML(EntityManager entityManager,
            FileWriter out) {
        Parser parser = new Parser();
        try {
            parser.parse();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long startTime = System.nanoTime();

        entityManager.getTransaction().begin();
        for (ForumUser user : parser.getUsers()) {
            entityManager.persist(user);
        }
        for (ForumThread thread : parser.getThreads()) {
            entityManager.persist(thread);
        }
        for (ForumPost post : parser.getPosts()) {
            entityManager.persist(post);
        }

        entityManager.getTransaction().commit();

        long endTime = System.nanoTime();

        System.out.println("Total data loading time,"
                + String.valueOf(endTime - startTime));
        try {
            out.write("Total data loading time,"
                    + String.valueOf(endTime - startTime) + "\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void printResults(FileWriter out) throws IOException {
        for (Map.Entry<String, Double> testAndTime : timesOfExecution.entrySet()) {
            String testName = testAndTime.getKey();
            double endResult = testAndTime.getValue() / NUMBER_OF_REPETITIONS;
            String message = testName + "," + String.format(Locale.UK, "%1.3f", endResult)
                    + "\n";
            System.out.print(message);
            out.write(message);
        }
    }
}
