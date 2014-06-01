package agh.bd2.jpa.performance;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

public class ThirtyFifthMostOftenUsedWordInPost extends QueryTester {

	private List resultList;
	private Map<String, Integer> wordCounter = new HashMap<>();
	private String resultWord;

	public ThirtyFifthMostOftenUsedWordInPost(EntityManager entityManager) {
		super("35th most often used word in posts", entityManager);
	}

	@Override
	public void executeQuery() {
		resultList = entityManager.createQuery("select content from ForumPost")
				.getResultList();

		wordCounter = new HashMap<>();
		countWordOccurences();
		List<Entry<String, Integer>> sortedWordsCounts = sortByValue(wordCounter);
		resultWord = sortedWordsCounts.get(34).getKey();

	}

	private void countWordOccurences() {
		for (Object obj : resultList) {
			String content = ((String) obj).toLowerCase();
			String[] words = content.split("[^A-Za-zęĘóÓśŚłŁżŻźŹćĆńŃ]+");
			for (String word : words) {
				if (!word.isEmpty()) {
					if (wordCounter.containsKey(word)) {
						wordCounter.put(word, wordCounter.get(word) + 1);
					} else {
						wordCounter.put(word, +1);
					}
				}
			}
		}
	}

	@Override
	public void printResult() {
		System.out.println(this.getName() + " result: " + resultWord);
	}

	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (-(o1.getValue()).compareTo(o2.getValue()));
			}
		});

		return list;
	}
}
