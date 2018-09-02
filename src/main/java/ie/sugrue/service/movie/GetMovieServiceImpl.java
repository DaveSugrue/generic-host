package ie.sugrue.service.movie;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.repository.MovieRepository;

@Service("getMovieService")
@Scope("prototype")
public class GetMovieServiceImpl implements GetMovieService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovieRepository	movieRepo;

	@Override
	public Movie getMovie(int id) {

		Movie movie = new Movie();

		try {
			movie = movieRepo.getMovie(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting movie with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting movie with id of {} from DB - ", id, e);
		}

		return movie;
	}

	@Override
	public ResponseWrapper getMovie(ResponseWrapper resp, int id) {

		try {
			Movie movie = movieRepo.getMovie(id);
			resp.addObject(movie);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting movie with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "Movie Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting movie with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting Movie data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public ArrayList<Movie> getMovies() {

		return getMovies("*");
	}

	@Override
	public ResponseWrapper getMovies(ResponseWrapper resp) {

		return getMovies(resp, "*");
	}

	@Override
	public ArrayList<Movie> getMovies(String genreId) {

		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			if (genreId.equals("*")) {
				movies = movieRepo.getMovies();
			} else {
				movies = movieRepo.getMovies(genreId);
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Movies", erdae);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Movies", e);
		}

		return movies;
	}

	@Override
	public ResponseWrapper getMovies(ResponseWrapper resp, String genreId) {

		ArrayList<Movie> movies = new ArrayList<Movie>();
		try {
			if (genreId.equals("*")) {
				movies = movieRepo.getMovies();
			} else {
				movies = movieRepo.getMovies(genreId);
			}
			resp.setObjects(movies);

		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Movies", erdae);
			Status status = new Status(1, "No Movies found");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Movies", e);
			Status status = new Status(2, "Problem getting Movie data from DB");
			resp.setStatus(status);
		}

		return resp;
	}
}
