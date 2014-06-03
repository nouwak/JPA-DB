package agh.bd2.jpa.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class ForumThread {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(columnDefinition="nvarchar(255)")
	private String title;
	
	@Column
        @Temporal(javax.persistence.TemporalType.DATE)
	private Calendar creationDate;

        public Calendar getCreationDate() {
            return creationDate;
        }
	
	@ManyToOne
	@JoinColumn(name = "author_name")
	private ForumUser author;

	public ForumThread(String title, Calendar creationDate, ForumUser author) {
		super();
		this.title = title;
		this.creationDate = creationDate;
		this.author = author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public void setAuthor(ForumUser author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForumThread other = (ForumThread) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public ForumThread() {
		super();
		// TODO Auto-generated constructor stub
	}
}
