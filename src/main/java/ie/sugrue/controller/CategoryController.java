package ie.sugrue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.service.category.CreateCategoryService;
import ie.sugrue.service.category.DeleteCategoryService;
import ie.sugrue.service.category.GetCategoryService;
import ie.sugrue.service.category.UpdateCategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/category")
@Scope("request")
public class CategoryController extends PrimaryController {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResponseWrapper			resp;
	@Autowired
	GetCategoryService		getCategoryService;
	@Autowired
	CreateCategoryService	createCategoryService;
	@Autowired
	UpdateCategoryService	updateCategoryService;
	@Autowired
	DeleteCategoryService	deleteCategoryService;

	@RequestMapping("")
	public ResponseWrapper getCategory(@RequestParam(value = "id", defaultValue = "") String id) {
		// If no id specified, bring them all back (default)
		if (id.equals("")) {
			return getCategoryService.getCategories(resp);
		} else {
			return getCategoryService.getCategory(resp, id);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public ResponseWrapper createCategory(@RequestBody Category category) {
		log.info("CREATING -> {}", category);

		return createCategoryService.createCategory(resp, category);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseWrapper updateCategory(@RequestBody Category category) {
		log.info("UPDATING -> {}", category);

		return updateCategoryService.updateCategory(resp, category);
	}

	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseWrapper deleteCategory(@RequestBody Category category) {
		log.info("DELETING -> {}", category);
		return deleteCategoryService.deleteCategory(resp, category);
	}
}