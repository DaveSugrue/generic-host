package ie.sugrue.service.movie;

import ie.sugrue.domain.ResponseWrapper;

public interface DeleteMovieService {

	ResponseWrapper deleteMovie(ResponseWrapper resp, int id);

}
