package ie.sugrue.service.category;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.repository.CategoryRepository;

@Service("getCategoryService")
@Scope("prototype")
public class GetCategoryServiceImpl implements GetCategoryService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryRepository	categoryRepo;

	@Override
	public Category getCategory(String id) {

		Category category = new Category();

		try {
			category = categoryRepo.getCategory(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting category with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting category with id of {} from DB - ", id, e);
		}

		return category;
	}

	@Override
	public ResponseWrapper getCategory(ResponseWrapper resp, String id) {

		try {
			Category category = categoryRepo.getCategory(id);
			resp.addObject(category);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting category with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "Category Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting category with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting Category data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public ArrayList<Category> getCategories() {

		ArrayList<Category> categories = new ArrayList<Category>();

		try {
			categories = categoryRepo.getCategories();
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Categories", erdae);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Categories", e);
		}

		return categories;
	}

	@Override
	public ResponseWrapper getCategories(ResponseWrapper resp) {

		try {
			ArrayList<Category> categories = categoryRepo.getCategories();
			resp.setObjects(categories);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Categories", erdae);
			Status status = new Status(1, "No Categories found");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Categories", e);
			Status status = new Status(2, "Problem getting Category data from DB");
			resp.setStatus(status);
		}

		return resp;
	}
}
