package ie.sugrue.service.category;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;

public interface DeleteCategoryService {

	ResponseWrapper deleteCategory(ResponseWrapper resp, Category category);

}
