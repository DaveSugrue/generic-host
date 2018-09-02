package ie.sugrue.repository;

import java.util.ArrayList;

import ie.sugrue.domain.Movie;

public interface MovieRepository {

	public void createMovie(Movie movie);

	public Movie getMovie(int id);

	public ArrayList<Movie> getMovies(String genreId);

	public ArrayList<Movie> getMovies();

	public void deleteMovie(int id);

	public Movie updateMovie(Movie movie);
}