package ie.sugrue.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.sugrue.domain.Genre;

public class GenreMapper implements RowMapper<Genre> {
	public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
		Genre genre = new Genre();
		genre.setId(rs.getString("id"));
		genre.setDescription(rs.getString("description"));
		genre.setOrderSequence(rs.getInt("orderSequence"));
		return genre;
	}
}