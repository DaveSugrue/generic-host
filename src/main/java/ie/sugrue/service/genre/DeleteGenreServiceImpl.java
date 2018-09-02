package ie.sugrue.service.genre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.GenreRepository;
import ie.sugrue.utils.Utils;

@Service("deleteGenreService")
@Scope("prototype")
public class DeleteGenreServiceImpl implements DeleteGenreService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GenreRepository	genreRepo;

	@Override
	public ResponseWrapper deleteGenre(ResponseWrapper resp, String id) {

		try {
			if (Utils.isNotNull(id)) {
				genreRepo.deleteGenre(id);
			} else {
				log.error("Cannot identify Genre to be deleted when trying to delete genre with id {} from Database", id);
				resp.getStatus().updateStatus(1, "I'm not sure what Genre you are trying to delete. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting genre with id of {} from DB - ", id, erdae);
			resp.getStatus().updateStatus(1, "Genre cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to delete genre with id {} from Database", id, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Genre details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to delete genre with id {} to Database", id, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Genre details from our database. Please try again.");
		}

		return resp;
	}
}
