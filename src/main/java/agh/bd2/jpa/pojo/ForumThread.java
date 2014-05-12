package agh.bd2.jpa.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ForumThread {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(columnDefinition="nvarchar(255)")
	private String title;
	
	@Column
	private Calendar creationDate;
	
	@ManyToOne
	@JoinColumn(name = "author_name")
	private ForumUser author;

	public ForumThread(String title, Calendar creationDate, ForumUser author) {
		super();
		this.title = title;
		this.creationDate = creationDate;
		this.author = author;
	}

	public ForumThread() {
		super();
		// TODO Auto-generated constructor stub
	}
}
