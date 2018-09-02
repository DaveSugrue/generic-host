package ie.sugrue.service.genre;

import ie.sugrue.domain.ResponseWrapper;

public interface DeleteGenreService {

	ResponseWrapper deleteGenre(ResponseWrapper resp, String id);

}
