package ie.sugrue.service.movie;

import java.util.ArrayList;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;

public interface GetMovieService {

	public Movie getMovie(int id);

	public ResponseWrapper getMovie(ResponseWrapper resp, int id);

	public ArrayList<Movie> getMovies();

	public ResponseWrapper getMovies(ResponseWrapper resp);

	public ArrayList<Movie> getMovies(String genreId);

	public ResponseWrapper getMovies(ResponseWrapper resp, String genreId);
}
