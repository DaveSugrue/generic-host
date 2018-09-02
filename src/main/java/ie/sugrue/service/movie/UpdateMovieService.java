package ie.sugrue.service.movie;

import ie.sugrue.domain.Movie;
import ie.sugrue.domain.ResponseWrapper;

public interface UpdateMovieService {

	ResponseWrapper updateMovie(ResponseWrapper resp, Movie movie);

}
