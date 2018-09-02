package ie.sugrue.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ie.sugrue.domain.Movie;

public class MovieMapper implements RowMapper<Movie> {
	public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
		Movie movie = new Movie();
		movie.setId(rs.getInt("id"));
		movie.setGenreId(rs.getString("genre_id"));
		movie.setFormat(rs.getString("format"));
		movie.setName(rs.getString("name"));
		movie.setNotes(rs.getString("notes"));
		movie.setDuration(rs.getInt("duration"));
		movie.setImage(rs.getString("image"));
		movie.setRating(rs.getInt("rating"));
		return movie;
	}
}