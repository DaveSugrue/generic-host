package ie.sugrue.service.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.CategoryRepository;

@Service("createCategoryService")
@Scope("prototype")
public class CreateCategoryServiceImpl implements CreateCategoryService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryRepository	categoryRepo;

	@Override
	public ResponseWrapper createCategory(ResponseWrapper resp, Category category) {

		try {
			categoryRepo.createCategory(category);
		} catch (DuplicateKeyException dk) {
			log.info("Duplicate key on id: {}", category.getId());
			resp.getStatus().updateStatus(1, "The category id '" + category.getId() + "' is already in use.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", category, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Category details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", category, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Category details to our database. Please try again.");
		}

		return resp;
	}

}
