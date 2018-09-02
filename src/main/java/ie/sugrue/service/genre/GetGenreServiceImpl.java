package ie.sugrue.service.genre;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.repository.GenreRepository;

@Service("getGenreService")
@Scope("prototype")
public class GetGenreServiceImpl implements GetGenreService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GenreRepository	genreRepo;

	@Override
	public Genre getGenre(String id) {

		Genre genre = new Genre();

		try {
			genre = genreRepo.getGenre(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting genre with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting genre with id of {} from DB - ", id, e);
		}

		return genre;
	}

	@Override
	public ResponseWrapper getGenre(ResponseWrapper resp, String id) {

		try {
			Genre genre = genreRepo.getGenre(id);
			resp.addObject(genre);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting genre with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "Genre Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting genre with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting Genre data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public ArrayList<Genre> getGenres() {

		ArrayList<Genre> genres = new ArrayList<Genre>();

		try {
			genres = genreRepo.getGenres();
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Genres", erdae);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Genres", e);
		}

		return genres;
	}

	@Override
	public ResponseWrapper getGenres(ResponseWrapper resp) {

		try {
			ArrayList<Genre> genres = genreRepo.getGenres();
			resp.setObjects(genres);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Genres", erdae);
			Status status = new Status(1, "No Genres found");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Genres", e);
			Status status = new Status(2, "Problem getting Genre data from DB");
			resp.setStatus(status);
		}

		return resp;
	}
}
