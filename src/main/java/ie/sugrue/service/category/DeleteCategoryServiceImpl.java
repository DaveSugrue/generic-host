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

@Service("deleteCategoryService")
@Scope("prototype")
public class DeleteCategoryServiceImpl implements DeleteCategoryService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryRepository	categoryRepo;

	@Override
	public ResponseWrapper deleteCategory(ResponseWrapper resp, Category category) {

		try {
			if (null != category.getId()) {
				categoryRepo.deleteCategory(category.getId());
			} else {
				log.error("Cannot identify Category to be deleted when trying to delete {} from Database", category);
				resp.getStatus().updateStatus(1, "We encountered a problem deleting Category details from our database. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting category with id of {} from DB - ", category.getId(), erdae);
			resp.getStatus().updateStatus(1, "Category cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to delete {} from Database", category, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Category details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to delete {} to Database", category, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Category details from our database. Please try again.");
		}

		return resp;
	}
}
