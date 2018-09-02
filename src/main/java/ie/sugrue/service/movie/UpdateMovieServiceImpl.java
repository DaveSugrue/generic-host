package ie.sugrue.service.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.MovieRepository;
import ie.sugrue.utils.Utils;

@Service("updateMovieService")
@Scope("prototype")
public class UpdateMovieServiceImpl implements UpdateMovieService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovieRepository	movieRepo;

	@Autowired
	private GetMovieService	getMovieService;

	@Override
	public ResponseWrapper updateMovie(ResponseWrapper resp, Movie movie) {

		try {
			movie = populateMovieNullsWithdefaults(movie);

			resp.addObject(movieRepo.updateMovie(movie));

		} catch (EmptyResultDataAccessException erdae) {
			log.error("Cannot identify movie to be updated when trying to update {} to Database", movie, erdae);
			resp.getStatus().updateStatus(1, "We encountered a problem saving the Movie details to our database. Please try again.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to update {} to Database", movie, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Movie details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to update {} to Database", movie, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Movie details to our database. Please try again.");
		}

		return resp;
	}

	private Movie populateMovieNullsWithdefaults(Movie movie) {

		Movie previousMovieDetails = getMovieService.getMovie(movie.getId());

		if (Utils.isNull(movie.getGenreId())) {
			movie.setGenreId(previousMovieDetails.getGenreId());
		}
		if (Utils.isNull(movie.getName())) {
			movie.setName(previousMovieDetails.getName());
		}
		if (Utils.isNull(movie.getFormat())) {
			movie.setFormat(previousMovieDetails.getFormat());
		}
		if (Utils.isNull(movie.getNotes())) {
			movie.setNotes(previousMovieDetails.getNotes());
		}
		if (Utils.isNull(movie.getDuration())) {
			movie.setDuration(previousMovieDetails.getDuration());
		}
		if (Utils.isNull(movie.getImage())) {
			movie.setImage(previousMovieDetails.getImage());
		}
		if (Utils.isNull(movie.getRating())) {
			movie.setRating(previousMovieDetails.getRating());
		}

		return movie;
	}

}
