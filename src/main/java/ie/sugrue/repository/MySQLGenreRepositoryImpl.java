package ie.sugrue.repository;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.sugrue.domain.Genre;

@Repository
public class MySQLGenreRepositoryImpl implements GenreRepository {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected JdbcTemplate	jdbc;

	public void createGenre(Genre genre) {
		String SQL = "insert into genre ( id, description, orderSequence ) values (?, ?, ?)";

		jdbc.update(SQL, genre.getId(), genre.getDescription(), genre.getOrderSequence());
		log.debug("Created Record = " + genre.getId() + " " + genre.getDescription());
		return;
	}

	public Genre getGenre(String id) {
		String SQL = "select * from genre where id = ?";
		Genre genre = jdbc.queryForObject(SQL, new Object[] { id }, new GenreMapper());
		return genre;
	}

	public ArrayList<Genre> getGenres() {
		String SQL = "select * from genre order by orderSequence";
		ArrayList<Genre> categories = new ArrayList<Genre>();
		categories.addAll(jdbc.query(SQL, new GenreMapper()));
		return categories;
	}

	public void deleteGenre(String id) {
		String SQL = "delete from genre where id = ?";
		int result = jdbc.update(SQL, id);
		if (result == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		log.debug("Deleted Record with ID = " + id);
		return;
	}

	public Genre updateGenre(Genre genre) {
		String SQL = "update genre set description = ?, orderSequence = ? where id = ?";
		jdbc.update(SQL, genre.getDescription(), genre.getOrderSequence(), genre.getId());
		genre = getGenre(genre.getId());
		log.debug("Updated Record with ID = " + genre.getId());
		return genre;
	}

}