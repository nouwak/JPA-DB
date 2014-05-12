package agh.bd2.jpa.pojo;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ForumUser {
	public ForumUser() {
		super();
	}

	public ForumUser(String login, String city, Calendar joiningDate) {
		super();
		this.login = login;
		this.city = city;
		this.joiningDate = joiningDate;
	}

	@Id
	@GeneratedValue
	private long id;
	
	@Column(columnDefinition="nvarchar(20)")
	private String login;
	
	@Column(columnDefinition="nvarchar(50)")
	private String city;
	
	@Column
	private Calendar joiningDate;
}
