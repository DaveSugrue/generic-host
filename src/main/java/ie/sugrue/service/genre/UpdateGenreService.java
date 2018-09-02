package ie.sugrue.service.genre;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;

public interface UpdateGenreService {

	ResponseWrapper updateGenre(ResponseWrapper resp, Genre genre);

}
