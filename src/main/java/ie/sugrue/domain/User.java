package ie.sugrue.domain;

import java.sql.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {
	private long	id;
	private String	firstName, lastName, email, pw;
	private Date	dob;

	public User() {

	}

	public User(long id, String firstName, String lastName, Date dateOfBirth, String email, String pw) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dateOfBirth;
		this.email = email;
		this.pw = pw;
	}

	public User(String firstName, String lastName, Date dateOfBirth, String email, String pw) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dateOfBirth;
		this.email = email;
		this.pw = pw;
	}

	public User(long id, String firstName, String lastName, String dobString, String email, String pw) {
		this(id, firstName, lastName, Date.valueOf(dobString), email, pw);
	}

	public User(String firstName, String lastName, String dobString, String email, String pw) {
		this(firstName, lastName, Date.valueOf(dobString), email, pw);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Date getDOB() {
		return dob;
	}

	public void setDOB(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", pw=" + pw + ", dob=" + dob + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((pw == null) ? 0 : pw.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (dob == null) {
			if (other.dob != null) {
				return false;
			}
		} else if (!dob.equals(other.dob)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (pw == null) {
			if (other.pw != null) {
				return false;
			}
		} else if (!pw.equals(other.pw)) {
			return false;
		}
		return true;
	}
}