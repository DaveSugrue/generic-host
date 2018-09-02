package ie.sugrue.service.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.CategoryRepository;

@Service("updateCategoryService")
@Scope("prototype")
public class UpdateCategoryServiceImpl implements UpdateCategoryService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryRepository	categoryRepo;

	@Override
	public ResponseWrapper updateCategory(ResponseWrapper resp, Category category) {

		try {
			resp.addObject(categoryRepo.updateCategory(category));
		} catch (EmptyResultDataAccessException erdae) {
			log.error("Cannot identify user to be updated when trying to update {} to Database", category, erdae);
			resp.getStatus().updateStatus(1, "We encountered a problem saving the Category details to our database. Please try again.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to update {} to Database", category, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Category details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to update {} to Database", category, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Category details to our database. Please try again.");
		}

		return resp;
	}

}
