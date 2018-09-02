package ie.sugrue.repository;

import java.util.ArrayList;

import ie.sugrue.domain.Genre;

public interface GenreRepository {
	/**
	 * This is the method to be used to initialize database resources ie. connection.
	 */
	// public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the genre table.
	 */
	public void createGenre(Genre genre);

	/**
	 * This is the method to be used to list down a record from the user table corresponding to a passed genre id.
	 */
	public Genre getGenre(String id);

	/**
	 * This is the method to be used to list down all the records from the genre table.
	 */
	public ArrayList<Genre> getGenres();

	/**
	 * This is the method to be used to delete a record from the genre table corresponding to a passed genre id.
	 */
	public void deleteGenre(String id);

	/**
	 * This is the method to be used to update a record into the genre table.
	 */
	public Genre updateGenre(Genre genre);
}