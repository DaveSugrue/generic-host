package ie.sugrue.service.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.MovieRepository;

@Service("deleteMovieService")
@Scope("prototype")
public class DeleteMovieServiceImpl implements DeleteMovieService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovieRepository	movieRepo;

	@Override
	public ResponseWrapper deleteMovie(ResponseWrapper resp, int id) {

		try {
			if (id > 0) {
				movieRepo.deleteMovie(id);
			} else {
				log.error("Cannot identify Movie to be deleted when trying to delete movie with id {} from Database", id);
				resp.getStatus().updateStatus(1, "I'm not sure what Movie you are trying to delete. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting movie with id of {} from DB - ", id, erdae);
			resp.getStatus().updateStatus(1, "Movie cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to movie with id delete {} from Database", id, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Movie details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to delete movie with id {} to Database", id, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Movie details from our database. Please try again.");
		}

		return resp;
	}
}
