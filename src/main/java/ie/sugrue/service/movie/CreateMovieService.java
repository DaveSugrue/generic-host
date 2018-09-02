package ie.sugrue.service.movie;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;

public interface CreateMovieService {

	ResponseWrapper createMovie(ResponseWrapper resp, Movie movie);

}
