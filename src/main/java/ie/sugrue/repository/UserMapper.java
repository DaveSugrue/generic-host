package ie.sugrue.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.sugrue.domain.User;

public class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setFirstName(rs.getString("firstName"));
		user.setLastName(rs.getString("lastName"));
		user.setDOB(rs.getDate("dob"));
		user.setEmail(rs.getString("email"));
		user.setPw(rs.getString("pw"));
		return user;
	}
}