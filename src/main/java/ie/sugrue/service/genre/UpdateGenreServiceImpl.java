package ie.sugrue.service.genre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;

@Service("updateGenreService")
@Scope("prototype")
public class UpdateGenreServiceImpl implements UpdateGenreService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GenreRepository	genreRepo;

	@Override
	public ResponseWrapper updateGenre(ResponseWrapper resp, Genre genre) {

		try {
			resp.addObject(genreRepo.updateGenre(genre));
		} catch (EmptyResultDataAccessException erdae) {
			log.error("Cannot identify user to be updated when trying to update {} to Database", genre, erdae);
			resp.getStatus().updateStatus(1, "We encountered a problem saving the Genre details to our database. Please try again.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to update {} to Database", genre, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Genre details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to update {} to Database", genre, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Genre details to our database. Please try again.");
		}

		return resp;
	}

}
