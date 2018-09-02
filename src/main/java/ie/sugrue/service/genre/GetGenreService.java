package ie.sugrue.service.genre;

import java.util.ArrayList;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;

public interface GetGenreService {

	public Genre getGenre(String id);

	public ResponseWrapper getGenre(ResponseWrapper resp, String id);

	public ArrayList<Genre> getGenres();

	public ResponseWrapper getGenres(ResponseWrapper resp);
}
