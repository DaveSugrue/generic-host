package ie.sugrue.repository;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.sugrue.domain.Movie;

@Repository
public class MySQLMovieRepositoryImpl implements MovieRepository {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected JdbcTemplate	jdbc;

	public void createMovie(Movie movie) {
		String SQL = "insert into movie ( id, genre_id, name, format, notes, duration, image, rating ) values (?, ?, ?, ?, ?, ?, ?, ?)";

		jdbc.update(SQL, movie.getId(), movie.getGenreId(), movie.getName(), movie.getFormat(), movie.getNotes(), movie.getDuration(), movie.getImage(), movie.getRating());
		log.debug("Created Record Name = " + movie.getId() + " " + movie.getName());
		return;
	}

	public Movie getMovie(int id) {
		String SQL = "select * from movie where id = ?";
		Movie movie = jdbc.queryForObject(SQL, new Object[] { id }, new MovieMapper());
		return movie;
	}

	public ArrayList<Movie> getMovies(String genreId) {
		String SQL = "select * from movie where genre_id = ? order by name";
		ArrayList<Movie> movies = new ArrayList<Movie>();
		movies.addAll(jdbc.query(SQL, new Object[] { genreId }, new MovieMapper()));
		return movies;
	}

	public ArrayList<Movie> getMovies() {
		String SQL = "select * from movie order by name";
		ArrayList<Movie> movies = new ArrayList<Movie>();
		movies.addAll(jdbc.query(SQL, new MovieMapper()));
		return movies;
	}

	public void deleteMovie(int id) {
		String SQL = "delete from movie where id = ?";
		int result = jdbc.update(SQL, id);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Record with ID = " + id);
		return;
	}

	public Movie updateMovie(Movie movie) {
		// user = populateUserNullsWithdefaults(user);

		String SQL = "update movie set genre_id = ?, name = ?, format = ?, notes = ?, duration = ?, image = ? , rating = ? where id = ?";
		jdbc.update(SQL, movie.getGenreId(), movie.getName(), movie.getFormat(), movie.getNotes(), movie.getDuration(), movie.getImage(), movie.getRating(), movie.getId());
		movie = getMovie(movie.getId());
		log.debug("Updated Record with ID = " + movie.getId());
		return movie;
	}

}