package agh.bd2.jpa.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ForumPost {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name="thread")
	private ForumThread thread;
	
	@Column(columnDefinition="nvarchar(MAX)")
	private String content;
	
	@Column
	private Calendar creationDate;
	
	@ManyToOne
	@JoinColumn(name="author")
	private ForumUser postAuthor;

	public ForumPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumPost(ForumThread thread, String content, Calendar creationDate,
			ForumUser postAuthor) {
		super();
		this.thread = thread;
		this.content = content;
		this.creationDate = creationDate;
		this.postAuthor = postAuthor;
	}
}
