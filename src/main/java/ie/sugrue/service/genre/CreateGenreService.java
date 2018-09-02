package ie.sugrue.service.genre;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;

public interface CreateGenreService {

	ResponseWrapper createGenre(ResponseWrapper resp, Genre genre);

}
