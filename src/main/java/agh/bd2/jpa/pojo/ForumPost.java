package agh.bd2.jpa.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class ForumPost {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name="thread")
	private ForumThread thread;
	
	@Column(columnDefinition="nvarchar(MAX)")
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setThread(ForumThread thread) {
		this.thread = thread;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public void setPostAuthor(ForumUser postAuthor) {
		this.postAuthor = postAuthor;
	}

	@Column
	private Calendar creationDate;

        public Calendar getCreationDate() {
            return creationDate;
        }
	
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
