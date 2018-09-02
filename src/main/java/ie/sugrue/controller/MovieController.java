package ie.sugrue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.service.movie.CreateMovieService;
import ie.sugrue.service.movie.DeleteMovieService;
import ie.sugrue.service.movie.GetMovieService;
import ie.sugrue.service.movie.UpdateMovieService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/movies")
@Scope("request")
public class MovieController {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResponseWrapper			resp;
	@Autowired
	GetMovieService			getMovieService;
	@Autowired
	CreateMovieService		createMovieService;
	@Autowired
	UpdateMovieService		updateMovieService;
	@Autowired
	DeleteMovieService		deleteMovieService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseWrapper getMovie(@PathVariable int id) {
		ResponseWrapper resp2 = getMovieService.getMovie(resp, id);
		log.info("Called:: /movies/" + id);
		log.info("Returning:: " + resp2);
		return resp2;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseWrapper getMovie(@RequestParam(value = "genreId", defaultValue = "") String genreId) {
		// If no Category id specified, bring them all back (default)
		if (genreId.equals("")) {
			resp = getMovieService.getMovies(resp);
		} else {
			resp = getMovieService.getMovies(resp, genreId);
		}
		log.info("Called:: /movies with genreId=" + genreId);
		log.info("Returning:: " + resp);
		return resp;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseWrapper createMovie(@RequestBody Movie movie) {
		log.info("CREATING -> {}", movie);

		return createMovieService.createMovie(resp, movie);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseWrapper updateMovie(@PathVariable int id, @RequestBody Movie movie) {
		log.info("UPDATING -> {}", movie);
		return updateMovieService.updateMovie(resp, movie);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseWrapper deleteMovie(@PathVariable int id) {
		log.info("DELETING movie with id -> {}", id);
		return deleteMovieService.deleteMovie(resp, id);
	}
}