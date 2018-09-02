package ie.sugrue.service.category;

import java.util.ArrayList;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;

public interface GetCategoryService {

	public Category getCategory(String id);

	public ResponseWrapper getCategory(ResponseWrapper resp, String id);

	public ArrayList<Category> getCategories();

	public ResponseWrapper getCategories(ResponseWrapper resp);
}
