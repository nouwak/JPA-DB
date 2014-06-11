package agh.bd2.jpa.pojo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
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

	public void setLogin(String login) {
		this.login = login;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setJoiningDate(Calendar joiningDate) {
		this.joiningDate = joiningDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForumUser other = (ForumUser) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Id
	@GeneratedValue
	private long id;

	@Column(columnDefinition = "nvarchar(100)")
	private String login;

	@Column(columnDefinition = "nvarchar(100)")
	private String city;

	@Column
	private Calendar joiningDate;
}
