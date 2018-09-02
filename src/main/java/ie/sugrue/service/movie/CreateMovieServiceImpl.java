package ie.sugrue.service.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.MovieRepository;

@Service("createMovieService")
@Scope("prototype")
public class CreateMovieServiceImpl implements CreateMovieService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovieRepository	movieRepo;

	@Override
	public ResponseWrapper createMovie(ResponseWrapper resp, Movie movie) {

		try {
			movieRepo.createMovie(movie);
		} catch (DuplicateKeyException dk) {
			log.info("Duplicate key on id: {}", movie.getId());
			resp.getStatus().updateStatus(1, "The movie id '" + movie.getId() + "' is already in use.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", movie, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Movie details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", movie, e);
		}

		return resp;
	}

}
