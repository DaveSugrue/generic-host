package ie.sugrue.service.genre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;

@Service("createGenreService")
@Scope("prototype")
public class CreateGenreServiceImpl implements CreateGenreService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GenreRepository	genreRepo;

	@Override
	public ResponseWrapper createGenre(ResponseWrapper resp, Genre genre) {

		try {
			genreRepo.createGenre(genre);
		} catch (DuplicateKeyException dk) {
			log.info("Duplicate key on id: {}", genre.getId());
			resp.getStatus().updateStatus(1, "The genre id '" + genre.getId() + "' is already in use.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", genre, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Genre details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", genre, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Genre details to our database. Please try again.");
		}

		return resp;
	}

}
