package ie.sugrue.repository;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.sugrue.domain.User;

@Repository
public class MySQLUserRepositoryImpl implements UserRepository {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected JdbcTemplate	jdbc;

	public void createUser(User user) {
		String SQL = "insert into app_user ( firstName, lastName, dob, email, pw ) values (?, ?, ?, ?, ?)";

		jdbc.update(SQL, user.getFirstName(), user.getLastName(), user.getDOB(), user.getEmail(), user.getPw());
		log.debug("Created Record Name = " + user.getFirstName() + " " + user.getLastName());
		return;
	}

	public User getUser(long id) {
		String SQL = "select * from app_user where id = ?";
		User user = jdbc.queryForObject(SQL, new Object[] { id }, new UserMapper());
		return user;
	}

	public User getUser(String emailAddress) {
		String SQL = "select * from app_user where email = ?";
		User user = jdbc.queryForObject(SQL, new Object[] { emailAddress }, new UserMapper());
		return user;
	}

	public ArrayList<User> getUsers() {
		String SQL = "select * from app_user";
		ArrayList<User> users = new ArrayList<User>();
		users.addAll(jdbc.query(SQL, new UserMapper()));
		return users;
	}

	public void deleteUser(long id) {
		String SQL = "delete from app_user where id = ?";
		int result = jdbc.update(SQL, id);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Record with ID = " + id);
		return;
	}

	public void deleteUser(String email) {
		String SQL = "delete from app_user where email = ?";
		int result = jdbc.update(SQL, email);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Record with email = " + email);
		return;
	}

	public User updateUser(User user) {
		// user = populateUserNullsWithdefaults(user);

		String SQL = "update app_user set firstName = ?, lastName = ?, dob = ?, email = ?, pw = ?";

		if (user.getId() > 0) {
			SQL += " where id = ?";
			jdbc.update(SQL, user.getFirstName(), user.getLastName(), user.getDOB(), user.getEmail(), user.getPw(), user.getId());
			user = getUser(user.getId());
		} else {
			SQL += " where email = ?";
			jdbc.update(SQL, user.getFirstName(), user.getLastName(), user.getDOB(), user.getEmail(), user.getPw(), user.getEmail());
			user = getUser(user.getEmail());
		}
		log.debug("Updated Record with ID = " + user.getId());
		return user;
	}

}