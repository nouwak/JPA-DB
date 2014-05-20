package agh.bd2.jpa.xmlparser;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import agh.bd2.jpa.pojo.ForumPost;
import agh.bd2.jpa.pojo.ForumThread;
import agh.bd2.jpa.pojo.ForumUser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Parser extends DefaultHandler {
	List<ForumPost> posts;
	Set<ForumThread> threads;
	Set<ForumUser> users;
	String currentElement = "";
	ForumThread currentThread = new ForumThread();
	ForumUser currentUser = new ForumUser();
	ForumPost currentPost = new ForumPost();

	@Override
	public void endDocument() throws SAXException {
		//posts.get(0);
		// TODO zapis do bazy
	}

	public List<ForumPost> getPosts() {
		return posts;
	}

	

	@Override
	public void startDocument() throws SAXException {
		posts = new ArrayList<>();
		threads = new HashSet<>();
		users = new HashSet<>();
	}

	public void parse() throws Exception {
		String filename = "tolkien.xml";

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();

		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(this);
		xmlReader.parse(convertToFileURL(filename));

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("task_result")) {
		
			threads.add(currentThread);
			users.add(currentUser);
			posts.add(currentPost);
			
			ForumUser linkedUser=getUser(currentUser);
			
			currentPost.setPostAuthor(linkedUser);
			currentThread.setAuthor(linkedUser);
			
			ForumThread linkedThread=getThread(currentThread);
			
			currentPost.setThread(linkedThread);
			
			currentThread = new ForumThread();
			currentUser = new ForumUser();
			currentPost = new ForumPost();
			// TODO koniec danych postu
		}
		currentElement = "";
	}

	public Set<ForumThread> getThreads() {
		return threads;
	}

	public Set<ForumUser> getUsers() {
		return users;
	}

	private ForumThread getThread(ForumThread currentThread2) {
		Iterator<ForumThread> iterator = threads.iterator();
		while(iterator.hasNext()){
			ForumThread forumThread = iterator.next();
			if(currentThread2.equals(forumThread)){
				return forumThread;
			}
		}
		return null;
	}

	private ForumUser getUser(ForumUser currentUser2) {
		Iterator<ForumUser> iterator = users.iterator();
		while(iterator.hasNext()){
			ForumUser forumUser = iterator.next();
			if(currentUser2.equals(forumUser)){
				return forumUser;
			}
		}
		return null;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (localName.equals("rule")) {
			currentElement = atts.getValue("name");
		}
	}

	@Override
	public void characters(char[] ch, int start, int len) throws SAXException {
		// TODO Auto-generated method stub
		if (!currentElement.isEmpty()) {
			// System.out.println(currentElement + " ");
			String content = String.copyValueOf(ch, start, len);
			process(content);
		}
	}

	private void process(String content) {
		if (currentElement.equals("thread-title")) {
			processThreadTitle(content);
		} else if (currentElement.equals("user-data")) {
			processUserData(content);
		} else if (currentElement.equals("user-login")) {
			processUserLogin(content);
		} else if (currentElement.equals("post-content")) {
			processPostContent(content);
		} else if (currentElement.equals("post-details")) {
			processPostDetails(content);
		}

	}

	private void processPostDetails(String content) {
		Pattern pattern = Pattern.compile("Wysłany: ([^\\n]+)\\n");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String date = matcher.group(1);
			if (!date.isEmpty()) {
				int day = 1;
				int month = 0;
				int year = 2000;
				int hour = 12;
				int minute = 0;
				if (date.startsWith("Dzisiaj")) {
					day = 10;
					month = 3;
					year = 2014;
					hour = Integer.valueOf(date.substring(10, 12));
					minute = Integer.valueOf(date.substring(13, 15));
				} else if (date.startsWith("Wczoraj")) {
					day = 9;
					month = 3;
					year = 2014;
					hour = Integer.valueOf(date.substring(10, 12));
					minute = Integer.valueOf(date.substring(13, 15));
				} else {
					day = Integer.valueOf(date.substring(0, 2));
					month = Integer.valueOf(date.substring(3, 5)) - 1;
					year = Integer.valueOf(date.substring(6, 10));
					hour = Integer.valueOf(date.substring(11, 13));
					minute = Integer.valueOf(date.substring(14, 16));
				}

				Calendar dateCalendar = Calendar.getInstance();
				dateCalendar.set(year, month, day, hour, minute);
				currentPost.setCreationDate(dateCalendar);
				currentThread.setCreationDate(dateCalendar);
			}

		}

	}

	private void processPostContent(String content) {
		if (currentPost.getContent() == null
				|| currentPost.getContent().isEmpty()) {
			currentPost.setContent(content);
		} else {
			currentPost.setContent(currentPost.getContent() + content);
		}
	}

	private void processUserLogin(String content) {
		Pattern pattern = Pattern.compile("([^\\n]+)\\n");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String login = matcher.group(1);
			if (!login.isEmpty()) {
				currentUser.setLogin(login);
			}

		}

	}

	private void processUserData(String content) {
		Pattern pattern = Pattern.compile("Dołączył\\(a\\): ([^\\n]+)");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String date = matcher.group(1);
			if (!date.isEmpty()) {
				try {
					int day = Integer.valueOf(date.substring(0, 2));
					int month = monthToInt(date.substring(3, 6));
					int year = Integer.valueOf(date.substring(7, 11));
					Calendar dataCalendar = Calendar.getInstance();
					dataCalendar.set(year, month, day);
					currentUser.setJoiningDate(dataCalendar);
				} catch (Exception e) {
					int day = 7;
					int month = 2;
					int year = 1998;
					Calendar dataCalendar = Calendar.getInstance();
					dataCalendar.set(year, month, day);
					currentUser.setJoiningDate(dataCalendar);
				}
			}

		}

		pattern = Pattern.compile("Skąd: ([^\\n]+)");
		matcher = pattern.matcher(content);
		if (matcher.find()) {
			String city = matcher.group(1);
			if (!city.isEmpty()) {
				currentUser.setCity(city);
			}

		}
	}

	private int monthToInt(String month) {
		if (month.equals("Sty")) {
			return 0;
		} else if (month.equals("Lut")) {
			return 1;
		} else if (month.equals("Mar")) {
			return 2;
		} else if (month.equals("Kwi")) {
			return 3;
		} else if (month.equals("Maj")) {
			return 4;
		} else if (month.equals("Cze")) {
			return 5;
		} else if (month.equals("Lip")) {
			return 6;
		} else if (month.equals("Sie")) {
			return 7;
		} else if (month.equals("Wrz")) {
			return 8;
		} else if (month.equals("Paź")) {
			return 9;
		} else if (month.equals("Lis")) {
			return 10;
		} else {
			return 11;
		}
	}

	private void processThreadTitle(String content) {
		Pattern pattern = Pattern.compile("Temat: ((\\S)+)\\n");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String title = matcher.group(1);
			if (!title.isEmpty()) {
				currentThread.setTitle(title);
			}

		}
	}

	private static String convertToFileURL(String filename) {
		String path = new File(filename).getAbsolutePath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return "file:" + path;
	}

}
